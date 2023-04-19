#! /usr/bin/env python3
# Copyright (c) 2021-2023 SanCloud Ltd
# SPDX-License-Identifier: MIT

import argparse
import os
import re
import subprocess
import tarfile


def run(cmd, **kwargs):
    return subprocess.run(cmd, shell=True, check=True, **kwargs)


def capture(cmd, **kwargs):
    return run(cmd, capture_output=True, **kwargs).stdout.decode("utf-8")


def do_build(args):
    machine = "bbe"         # This is all we support for now
    kas_configs = f"kas/{args.distro}-{args.series}-{machine}.yml"
    kas_args = "--update --force-checkout"
    kas_env = os.environ.copy()
    kas_env["KAS_BUILD_DIR"] = args.build_path

    if args.site_conf:
        conf_path = os.path.join(args.build_path, "conf")
        site_dest = os.path.join(conf_path, "site.conf")
        if os.path.exists(site_dest):
            os.remove(site_dest)
        os.makedirs(conf_path, exist_ok=True)
        os.symlink(os.path.realpath(args.site_conf), site_dest)
    if args.target:
        kas_args += f" --target {args.target}"
    if args.command:
        kas_args += f" --cmd {args.command}"
    for cfg in args.extra_cfg:
        kas_configs += f":kas/inc/{cfg}.yml"
    run(f"kas build {kas_args} {kas_configs}", env=kas_env)


def do_clean(args):
    run("rm -rf build* release")


def do_set_version(args):
    with open("conf/layer.conf", "r+") as f:
        text = re.sub(r"(SANCLOUD_BSP_VERSION =).*\n", rf'\1 "{args.version}"\n', f.read())
        f.seek(0)
        f.write(text)
        f.truncate()
    msg = "Release" if args.release else "Bump version to"
    run(f'git commit -asm "{msg} {args.version}"')


def do_release_build(args):
    os.makedirs("release", exist_ok=True)
    with open("release/RELEASE_NOTES.txt", "w") as f:
        f.write(f"SanCloud BSP {args.version}\n")
        text = capture(f"markdown-extract -n ^{args.version} ChangeLog.md")
        f.write(text)

    run('git archive --format=tar.gz '
        f'--prefix=meta-sancloud-v{args.version}/ '
        f'--output=release/meta-sancloud-v{args.version}.tar.gz '
        'HEAD')

    run('docker run -it --rm -v "$(pwd):/workdir" '
        'gitlab-registry.sancloud.co.uk/bsp/build-containers/poky-build:latest '
        '--workdir=/workdir ./scripts/maintainer.py build -p build-poky')
    with tarfile.open(f"release/bbe-base-image-v{args.version}.tar", mode="w", dereference=True) as tf:
        tf.add("build-poky/tmp/deploy/images/bbe/core-image-base-bbe.wic.xz", "bbe-base-image.wic.xz")
        tf.add("build-poky/tmp/deploy/images/bbe/core-image-base-bbe.wic.bmap", "bbe-base-image.wic.bmap")
    run("rsync -a build-poky/tmp/deploy/sources/mirror/ release/sources/")

    run('docker run -it --rm -v "$(pwd):/workdir" '
        'gitlab-registry.sancloud.co.uk/bsp/build-containers/arago-build:latest '
        '--workdir=/workdir ./scripts/maintainer.py build -p build-arago -d arago -t tisdk-default-image')
    with tarfile.open(f"release/bbe-tisdk-image-v{args.version}.tar", mode="w", dereference=True) as tf:
        tf.add("build-arago/tmp-external-arm-glibc/deploy/images/bbe/tisdk-default-image-bbe.wic.xz", "bbe-tisdk-image.wic.xz")
        tf.add("build-arago/tmp-external-arm-glibc/deploy/images/bbe/tisdk-default-image-bbe.wic.bmap", "bbe-tisdk-image.wic.bmap")
    run("rsync -a build-arago/tmp-external-arm-glibc/deploy/sources/mirror/ release/sources/")

    file_list = (
        f"RELEASE_NOTES.txt meta-sancloud-v{args.version}.tar.gz "
        f"bbe-base-image-v{args.version}.tar bbe-tisdk-image-v{args.version}.tar"
        )
    with open("release/SHA256SUMS", "w") as f:
        text = capture(
            f"sha256sum {file_list}",
            cwd="release",
        )
        f.write(text)
    with open("release/B3SUMS", "w") as f:
        text = capture(
            f"b3sum {file_list}",
            cwd="release",
        )
        f.write(text)
    if args.sign:
        run("gpg --detach-sign -a release/SHA256SUMS")
        run("gpg --detach-sign -a release/B3SUMS")


def do_release_tag(args):
    run(f"git tag -a -F release/RELEASE_NOTES.txt v{args.version} {args.commit}")


def do_release_push(args):
    commit = capture(f"git rev-parse v{args.version}~0")
    file_list = (
        f"RELEASE_NOTES.txt meta-sancloud-v{args.version}.tar.gz "
        f"bbe-base-image-v{args.version}.tar bbe-tisdk-image-v{args.version}.tar "
        "SHA256SUMS B3SUMS"
        )
    if not args.no_gitlab:
        run("git push origin")
        run(f"git push origin v{args.version}")
        run(f"git push origin {commit}:refs/heads/release")
        run(f"glab release create v{args.version} -n v{args.version} "
            f"-F RELEASE_NOTES.txt {file_list}",
            cwd="release")
    if not args.no_github:
        run("git push gh")
        run(f"git push gh v{args.version}")
        run(f"git push gh {commit}:refs/heads/release")
        run(f"gh release create v{args.version} -n v{args.version} "
            f"-F RELEASE_NOTES.txt {file_list}",
            cwd="release")
    if not args.no_source_mirror:
        run("rclone copy release/sources yocto-source-mirror:")


def do_release_signatures(args):
    file_list = "SHA256SUMS.asc B3SUMS.asc"
    if not args.no_gitlab:
        run(f"glab release upload v{args.version} {file_list}",
            cwd="release")
    if not args.no_github:
        run(f"gh release upload v{args.version} {file_list}",
            cwd="release")


def do_release(args):
    args.release = True
    do_clean(args)
    do_set_version(args)
    args.commit = capture("git rev-parse HEAD").strip()
    do_release_build(args)
    do_release_tag(args)
    do_release_push(args)


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
        "-s", "--series", default="dunfell", help="Yocto release series to build for (dunfell or kirkstone)"
    )
    build_cmd.add_argument(
        "-i", "--site-conf", help="Site-specific configuration file"
    )
    build_cmd.add_argument(
        "-p", "--build-path", default="build", help="Directory in which to perform build"
    )
    build_cmd.add_argument(
        "-t", "--target", default="core-image-base", help="Recipe to build"
    )
    build_cmd.add_argument(
        "-c", "--command", help="Recipe command to run (e.g. fetch, populate_sdk)"
    )
    build_cmd.add_argument(
        "-x", "--extra-cfg", default=[], action="append", help="Additional kas config fragments (may be passed more than once)"
    )

    clean_cmd = subparsers.add_parser(
        name="clean", help="Remove build output from the source tree"
    )
    clean_cmd.set_defaults(cmd_fn=do_clean)

    set_version_cmd = subparsers.add_parser(
        name="set-version", help="Set version string & commit"
    )
    set_version_cmd.set_defaults(cmd_fn=do_set_version)
    set_version_cmd.add_argument("version", help="New version string")
    set_version_cmd.add_argument("-r", "--release", action="store_true", help="This version bump is for a release")

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
    release_cmd.add_argument(
        "--no-source-mirror",
        action="store_true",
        help="Disable pushing sources to mirror",
    )

    release_build_cmd = subparsers.add_parser(name="release-build", help="Perform release build")
    release_build_cmd.set_defaults(cmd_fn=do_release_build)
    release_build_cmd.add_argument("version", help="Release to build (must already be checked out)")
    release_build_cmd.add_argument(
        "-s", "--sign", action="store_true", help="Sign release with gpg"
    )

    release_tag_cmd = subparsers.add_parser(
        name="release-tag", help="Tag a new release of this project"
    )
    release_tag_cmd.set_defaults(cmd_fn=do_release_tag)
    release_tag_cmd.add_argument("version", help="Version string for the new release (must already be built)")
    release_tag_cmd.add_argument("commit", help="Commit to tag")

    release_push_cmd = subparsers.add_parser(
        name="release-push", help="Push a release to GitHub and/or GitLab"
    )
    release_push_cmd.set_defaults(cmd_fn=do_release_push)
    release_push_cmd.add_argument("version", help="Release to push (must already be tagged and built)")
    release_push_cmd.add_argument(
        "--no-gitlab",
        action="store_true",
        help="Disable push to SanCloud gitlab instance",
    )
    release_push_cmd.add_argument(
        "--no-github",
        action="store_true",
        help="Disable push to public github repositories",
    )
    release_push_cmd.add_argument(
        "--no-source-mirror",
        action="store_true",
        help="Disable pushing sources to mirror",
    )

    release_signatures_cmd = subparsers.add_parser(
        name="release-signatures", help="Push release signatures to GitHub and/or GitLab"
    )
    release_signatures_cmd.set_defaults(cmd_fn=do_release_signatures)
    release_signatures_cmd.add_argument("version", help="Release to push signatures for (must already be released)")
    release_signatures_cmd.add_argument(
        "--no-gitlab",
        action="store_true",
        help="Disable pushing signatures to SanCloud gitlab instance",
    )
    release_signatures_cmd.add_argument(
        "--no-github",
        action="store_true",
        help="Disable pushing signatures to public github repositories",
    )

    return parser.parse_args()


def main():
    args = parse_args()
    args.cmd_fn(args)


if __name__ == "__main__":
    main()
