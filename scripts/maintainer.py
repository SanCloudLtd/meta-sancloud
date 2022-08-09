#! /usr/bin/env python3
# Copyright (c) 2021-2022 SanCloud Ltd
# SPDX-License-Identifier: MIT

import argparse
import glob
import os
import re
import shutil
import subprocess
import tarfile
from git_acquire.acquire import Acquisition


def run(cmd, **kwargs):
    return subprocess.run(cmd, shell=True, check=True, **kwargs)


def capture(cmd, **kwargs):
    return run(cmd, capture_output=True, **kwargs).stdout.decode("utf-8")


def do_acquire_layers(args):
    layers = (
        Acquisition("https://git.yoctoproject.org/poky", "yocto-3.1.17", "layers/poky"),
        Acquisition("https://git.openembedded.org/meta-openembedded", "0722ff6f021df91542b5efa1ff5b5f6269f66add", "layers/meta-openembedded"),
        Acquisition("https://git.yoctoproject.org/git/meta-arm", "c4f04f3fb66f8f4365b08b553af8206372e90a63", "layers/meta-arm"),
        Acquisition("https://git.yoctoproject.org/git/meta-ti", "08.02.00.006", "layers/meta-ti"),
        Acquisition("https://github.com/EmbeddedAndroid/meta-rtlwifi.git", "98b2b2c34f186050e6092bc4f17ecb69aef6148a", "layers/meta-rtlwifi")
    )
    if args.distro == "arago":
        layers += (
            Acquisition("https://git.yoctoproject.org/git/meta-arago", "08.02.00.006", "layers/meta-arago"),
            Acquisition("https://github.com/meta-qt5/meta-qt5.git", "b4d24d70aca75791902df5cd59a4f4a54aa4a125", "layers/meta-qt5"),
            Acquisition("https://git.yoctoproject.org/git/meta-virtualization", "c4f156fa93b37b2428e09ae22dbd7f5875606f4d", "layers/meta-virtualization")
        )
    for layer in layers:
        print(f"Acquiring {layer.local_path}")
        layer.acquire()


def do_setup_build_dir(args):
    project_root = os.path.realpath(os.getcwd())
    do_acquire_layers(args)
    print("Setting up bblayers.conf")
    cmd = (
        f'source layers/poky/oe-init-build-env "{args.build_path}" && '
        'bitbake-layers add-layer '
        f'"{project_root}/layers/meta-openembedded/meta-oe" '
        f'"{project_root}/layers/meta-openembedded/meta-python" '
        f'"{project_root}/layers/meta-openembedded/meta-networking" '
        f'"{project_root}/layers/meta-arm/meta-arm-toolchain" '
        f'"{project_root}/layers/meta-arm/meta-arm" '
        f'"{project_root}/layers/meta-ti" '
        f'"{project_root}/layers/meta-rtlwifi" '
    )
    if args.distro == "arago":
        cmd += (
            f'"{project_root}/layers/meta-openembedded/meta-filesystems" '
            f'"{project_root}/layers/meta-qt5" '
            f'"{project_root}/layers/meta-virtualization" '
            f'"{project_root}/layers/meta-arago/meta-arago-extras" '
            f'"{project_root}/layers/meta-arago/meta-arago-distro" '
        )
    cmd += f'"{project_root}"'
    run(cmd)

    if args.site_conf:
        path = os.path.realpath(args.site_conf)
        print(f"Setting up site.conf -> {path}")
        run(f"ln -sfn {path} {args.build_path}/conf/site.conf")

    print("Setting up auto.conf")
    with open(f"{args.build_path}/conf/auto.conf", "w") as f:
        f.write(f'DISTRO = "{args.distro}"\n')
        f.write(f'MACHINE = "bbe"\n')
        if args.kernel_provider:
            f.write(f'BBE_KERNEL_PROVIDER = "{args.kernel_provider}"\n')
        if args.distro == "arago":
            f.write('PACKAGE_CLASSES = "package_ipk"\n')
        f.write("\n")
        f.write('BB_NUMBER_THREADS = "8"\n')
        f.write('PARALLEL_MAKE = "-j8"\n')
        f.write("\n")
        f.write("require conf/include/sancloud-enable-archiver.inc\n")
        f.write("require conf/include/sancloud-mirrors.inc\n")


def do_build(args):
    if not args.skip_setup:
        do_setup_build_dir(args)
    print(f"Building {args.target}")
    if args.command:
        maybe_cmd = f"-c {args.command}"
    else:
        maybe_cmd = ""
    run(f'source layers/poky/oe-init-build-env "{args.build_path}" && bitbake {args.target} {maybe_cmd}')


def do_clean(args):
    for d in glob.glob("build*"):
        shutil.rmtree(d)


def do_set_version(args):
    with open("conf/layer.conf", "r+") as f:
        text = re.sub(r"(SANCLOUD_BSP_VERSION =).*\n", rf'\1 "{args.version}"\n', f.read())
        f.seek(0)
        f.write(text)
        f.truncate()
    run(f'git commit -asm "Release {args.version}"')


def do_release_build(args):
    run('docker run -it --rm -v "$(pwd):/workdir" '
        'gitlab-registry.sancloud.co.uk/bsp/build-containers/poky-build:latest '
        '--workdir=/workdir ./scripts/maintainer.py build -p build-poky')
    with tarfile.open(f"release/bbe-base-image-v{args.version}.tar", mode="w", dereference=True) as tf:
        tf.add("build-poky/tmp/deploy/images/bbe/core-image-base-bbe.wic.xz", "bbe-base-image.wic.xz")
        tf.add("build-poky/tmp/deploy/images/bbe/core-image-base-bbe.wic.bmap", "bbe-base-image.wic.bmap")

    run('docker run -it --rm -v "$(pwd):/workdir" '
        'gitlab-registry.sancloud.co.uk/bsp/build-containers/arago-build:latest '
        '--workdir=/workdir ./scripts/maintainer.py build -p build-arago -d arago -t tisdk-default-image')
    with tarfile.open(f"release/bbe-tisdk-image-v{args.version}.tar", mode="w", dereference=True) as tf:
        tf.add("build-arago/tmp-external-arm-glibc/deploy/images/bbe/tisdk-default-image-bbe.wic.xz", "bbe-tisdk-image.wic.xz")
        tf.add("build-arago/tmp-external-arm-glibc/deploy/images/bbe/tisdk-default-image-bbe.wic.bmap", "bbe-tisdk-image.wic.bmap")


def do_release(args):
    do_clean(args)

    os.makedirs("release", exist_ok=True)
    with open("release/RELEASE_NOTES.txt", "w") as f:
        f.write(f"SanCloud BSP {args.version}\n")
        text = capture(f"markdown-extract -n ^{args.version} ChangeLog.md")
        f.write(text)

    with open("conf/layer.conf", "r+") as f:
        text = re.sub(r"(SANCLOUD_BSP_VERSION =).*\n", rf'\1 "{args.version}"\n', f.read())
        f.seek(0)
        f.write(text)
        f.truncate()
    run(f'git commit -asm "Release {args.version}"')
    release_commit = capture("git rev-parse HEAD").strip()
    do_release_build(args)

    run(f"git tag -a -F release/RELEASE_NOTES.txt v{args.version} {release_commit}")
    with open("release/SHA256SUMS", "w") as f:
        text = capture(
            "sha256sum RELEASE_NOTES.txt *.tar",
            cwd="release",
        )
        f.write(text)
    if args.sign:
        run("gpg --detach-sign -a release/SHA256SUMS")

    if not args.no_gitlab:
        run(f"git push origin v{args.version}")
        run(f"git push origin {release_commit}:refs/heads/release")
        run(f"glab release create v{args.version} -n v{args.version} -F release/RELEASE_NOTES.txt release/*")
    if not args.no_github:
        run(f"git push gh v{args.version}")
        run(f"git push gh {release_commit}:refs/heads/release")
        run(f"gh release create v{args.version} -t v{args.version} -F release/RELEASE_NOTES.txt release/*")


def do_no_command(args):
    print("Missing command! Try `./scripts/maintainer.py --help`")


def parse_args():
    parser = argparse.ArgumentParser()
    parser.set_defaults(cmd_fn=do_no_command)
    subparsers = parser.add_subparsers(
        dest="cmd", title="Maintainer commands", metavar="command"
    )

    build_cmd = subparsers.add_parser(name="build", help="Perform build")
    build_cmd.set_defaults(cmd_fn=do_build)
    build_cmd.add_argument(
        "-d", "--distro", default="poky", help="Distribution to build (poky or arago)"
    )
    build_cmd.add_argument(
        "-i", "--site-conf", help="Site-specific configuration file"
    )
    build_cmd.add_argument(
        "-p", "--build-path", default="build", help="Directory in which to perform build"
    )
    build_cmd.add_argument(
        "-k", "--kernel-provider", help="Alternative kernel provider for build (e.g. mainline, stable, rt)"
    )
    build_cmd.add_argument(
        "-t", "--target", default="core-image-base", help="Recipe to build"
    )
    build_cmd.add_argument(
        "-S", "--skip-setup", action="store_true", help="Skip build directory setup"
    )
    build_cmd.add_argument(
        "-c", "--command", help="Recipe command to run (e.g. fetch, populate_sdk)"
    )

    setup_build_dir_cmd = subparsers.add_parser(name="setup-build-dir", help="Setup a build directory")
    setup_build_dir_cmd.set_defaults(cmd_fn=do_setup_build_dir)
    setup_build_dir_cmd.add_argument(
        "-d", "--distro", default="poky", help="Distribution to build (poky or arago)"
    )
    setup_build_dir_cmd.add_argument(
        "-i", "--site-conf", help="Site-specific configuration file"
    )
    setup_build_dir_cmd.add_argument(
        "-p", "--build-path", default="build", help="Directory in which to perform build"
    )
    setup_build_dir_cmd.add_argument(
        "-k", "--kernel-provider", help="Alternative kernel provider for build (e.g. mainline, stable, rt)"
    )

    acquire_layers_cmd = subparsers.add_parser(name="acquire-layers", help="Fetch and checkout Yocto layers")
    acquire_layers_cmd.set_defaults(cmd_fn=do_acquire_layers)
    acquire_layers_cmd.add_argument(
        "-d", "--distro", default="poky", help="Distribution to build (poky or arago)"
    )

    release_build_cmd = subparsers.add_parser(name="release-build", help="Perform release build")
    release_build_cmd.set_defaults(cmd_fn=do_release_build)
    release_build_cmd.add_argument("version", help="Version string for the new release")

    clean_cmd = subparsers.add_parser(
        name="clean", help="Remove build output from the source tree"
    )
    clean_cmd.set_defaults(cmd_fn=do_clean)

    release_cmd = subparsers.add_parser(
        name="release", help="Release a new version of this project"
    )
    release_cmd.set_defaults(cmd_fn=do_release)
    release_cmd.add_argument("version", help="Version string for the new release")
    release_cmd.add_argument(
        "-s", "--sign", action="store_true", help="Sign release with gpg"
    )
    release_cmd.add_argument(
        "--no-gitlab",
        action="store_true",
        help="Disable push to SanCloud gitlab instance",
    )
    release_cmd.add_argument(
        "--no-github",
        action="store_true",
        help="Disable push to public github repositories",
    )

    set_version_cmd = subparsers.add_parser(
        name="set-version", help="Set version string & commit"
    )
    set_version_cmd.set_defaults(cmd_fn=do_set_version)
    set_version_cmd.add_argument("version", help="New version string")

    return parser.parse_args()


def main():
    args = parse_args()
    args.cmd_fn(args)


if __name__ == "__main__":
    main()
