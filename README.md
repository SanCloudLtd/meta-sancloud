<!--
Copyright (c) 2018-2022 SanCloud Ltd
SPDX-License-Identifier: CC-BY-4.0
-->

# Yocto BSP layer for the SanCloud boards

[<img align=right src="https://www.sancloud.co.uk/wp-content/uploads/2016/09/sancloud_and_address_web.png">](https://www.sancloud.com/)

[![pre-commit.ci status](https://results.pre-commit.ci/badge/github/SanCloudLtd/meta-sancloud/dunfell.svg)](https://results.pre-commit.ci/latest/github/SanCloudLtd/meta-sancloud/dunfell)

This is the [Yocto Project](https://www.yoctoproject.org/)
Board Support Package (BSP) layer for [SanCloud](https://sancloud.co.uk/)
devices.

For support enquiries relating to this BSP please contact us via email to
<opensource@sancloud.com>.

## Compatibility

Currently supported hardware with corresponding Yocto Project MACHINE names:

* `bbe`: [Sancloud BeagleBone Enhanced (BBE)](https://sancloud.co.uk/beaglebone-enhanced-bbe/)

This BSP layer supports building the following Yocto Project distributions:

* [Poky Reference Distribution](https://www.yoctoproject.org/software-item/poky/)
* [Arago Distribution](http://arago-project.org/wiki/index.php/Main_Page)
* [Automotive Grade Linux (AGL)](https://www.automotivelinux.org/)

This layer primarily supports the Yocto Project 3.1 "dunfell" release series.
It also provisionally supports the Yocto Project 4.0 "kirkstone" release series,
although not all features are tested yet with kirkstone.

## Usage

### Building with kas & Docker/Podman

The simplest way to build this BSP is to use a container image and the kas build
tool. This method avoids the requirement to manually install the required build
dependencies and checkout appropriate versions of each Yocto Project layer.

This BSP layer includes build configuration files for use with the
[kas build tool](https://github.com/siemens/kas). This tool can fetch all
layer dependencies (including bitbake) and set up a build directory with
appropriate configuration for the BBE and the chosen distro. This tool is
included in our container images already.

The instructions below assume that you are using Docker, but Podman can be
substituted if that is your preferred container engine.

#### Poky distribution

To build the Poky distribution using Docker & kas, first clone this
meta-sancloud BSP layer. Then use the
[poky-build](https://quay.io/repository/sancloudltd/poky-build) image to start a
new Docker container with the meta-sancloud directory mounted as `/workdir`:

```
git clone -b dunfell https://github.com/sancloudltd/meta-sancloud.git
docker run -it --rm -v "$(pwd)/meta-sancloud:/workdir" --workdir /workdir quay.io/sancloudltd/poky-build:latest
```

Inside the Docker container, start the build using the `kas` tool:

```
kas build kas/poky-dunfell-bbe.yml
```

Following a successful build, the resulting SD card image can be found at
`build/tmp/deploy/images/bbe/core-image-base-bbe.wic.xz`.

#### Arago distribution

To build the Arago distribution using Docker & kas, first clone this
meta-sancloud BSP layer. Then use the
[arago-build](https://quay.io/repository/sancloudltd/arago-build) image to start
a new Docker container with the meta-sancloud directory mounted as `/workdir`:

```
git clone -b dunfell https://github.com/sancloudltd/meta-sancloud.git
docker run -it --rm -v "$(pwd)/meta-sancloud:/workdir" --workdir /workdir quay.io/sancloudltd/arago-build:latest
```

Inside the Docker container, start the build using the `kas` tool:

```
kas build kas/arago-dunfell-bbe.yml
```

Following a successful build, the resulting SD card image can be found at
`build/tmp/deploy/images/bbe/tisdk-default-image-bbe.wic.xz`.

#### Configuration fragments

Additional kas configuration fragments are provided in the `kas/inc` directory
and can be used to simplify the process of making minor configuration changes.
These configuration fragments are also used to support CI builds and internal
testing of this layer. Multiple configuration fragments can be combined in one
build, but beware that some combinations may not make sense and will give
unpredictable results (such as selecting multiple kernel providers).

To use one or more configuration fragments, append the relevant paths to the
base configuration path with `:` separators on the kas command line.  For
example, to build the Poky distribution with both systemd and the Real Time
kernel enabled, run the following command:

```
kas build kas/poky-dunfell-bbe.yml:kas/inc/systemd.yml:kas/inc/kernel-rt.yml
```

The following configuration fragments are available and are suitable for general
use:

* `kas/inc/spiboot.yml`: enables support for booting from SPI flash.

* `kas/inc/systemd.yml`: switches the init system to systemd.

* `kas/inc/connman.yml`: enables the connman network connection manager.

* `kas/inc/kernel-bb.org.yml`: switches the Linux kernel source to the
  [BeagleBoard.org tree](https://github.com/beagleboard/linux).

* `kas/inc/kernel-rt.yml`: enables Real Time support in the Linux kernel
  (via the RT patch series and PREEMPT_RT configuration).

* `kas/inc/kernel-mainline.yml`: switches the Linux kernel source to Linus'
  mainline branch using the [meta-linux-mainline Yocto Project layer][1], with
  no downstream patches applied.

* `kas/inc/kernel-stable.yml`: switches the Linux kernel source to the latest
  upstream stable branch using the [meta-linux-mainline Yocto Project layer][1],
  with no downstream patches applied.

* `kas/inc/kernel-lts.yml`: switches the Linux kernel source to the latest
  upstream LTS branch using the [meta-linux-mainline Yocto Project layer][1],
  with no downstream patches applied.

[1]: https://github.com/unnecessary-abstraction/meta-linux-mainline

The following configuration fragments are used internally by SanCloud and should
be used with caution:

* `kas/inc/ci.yml`: used for Continuous Integration (CI) builds.

* `kas/inc/debug.yml`: used to enable inclusion of debug tools and to provision
  extra free space in the rootfs.

* `kas/inc/release.yml`: used for release builds.

* `kas/inc/kernel-next.yml`: used to test pending kernel changes.

* `kas/inc/kernel-rt-next.yml`: used to test pending kernel changes with Real
  Time support.

#### Customisation

The build configuration files in the kas directory can be used as the basis of
further customisation and integration work. It's recommended to copy the build
configuration files into your own repository (adding a `url:` entry for the
meta-sancloud layer) and work there so that your changes can be tracked
separately from future BSP updates in this repository.

### Building without kas

This BSP layer is listed in the
[OpenEmbedded Layer Index](http://layers.openembedded.org/)
which makes getting started very easy. Once your OpenEmbedded/Yocto Project
build environment is set up you can use the `bitbake-layers layerindex-fetch`
command to download this layer with all its dependencies and add these layers
to your bblayers.conf file automatically.

```
git clone -b dunfell git://git.yoctoproject.org/poky
source poky/oe-init-build-env
bitbake-layers layerindex-fetch meta-sancloud
echo 'MACHINE = "bbe"' >> conf/local.conf
bitbake core-image-base
```

Following a successful build, the resulting SD card image can be found at
`tmp/deploy/images/bbe/core-image-base-bbe.wic.xz`.

### Building Automotive Grade Linux (AGL)

This BSP layer is included in recent releases of AGL. AGL sources can be
downloaded by following the
[upstream instructions](https://wiki.automotivelinux.org/agl-distro/source-code).
Once the AGL sources have been downloaded and you're in the top-level AGL
directory, run the following commands to build the AGL Telematics Demo image for
the Sancloud BBE:

```
source meta-agl/scripts/aglsetup.sh -m bbe agl-demo
bitbake agl-telematics-demo-platform
```

Following a successful build, the resulting SD card image can be found at
`tmp/deploy/images/bbe/agl-telematics-demo-image-bbe.wic.xz`.

### Building without Docker/Podman

We recommend building this BSP under
[Ubuntu 20.04](https://releases.ubuntu.com/20.04/) or
[Ubuntu 22.04](https://releases.ubuntu.com/22.04/).
Building under other distributions supported by the relevant Yocto Project
release is expected to work but has not been tested.

To configure your system to build this BSP, please see
[Required Packages for the Build Host](https://docs.yoctoproject.org/3.1.24/ref-manual/ref-system-requirements.html#required-packages-for-the-build-host)
in the Yocto Project Reference Manual for details.

#### Arago distribution dependencies

Two additional setup steps are needed if you intend to build the Arago
distribution:

1) In addition to the packages listed in the Yocto Project documentation, 32-bit
   (aka multilib) C++ libraries also need to be installed. On Ubuntu these
   packages can be installed with the following command:

   ```
   sudo apt install gcc-multilib g++-multilib
   ```

2) The Arago distribution also depends on a pre-built ARM toolchain which must
   be downloaded and installed under `/opt`. This toolchain can be installed
   with the following commands:

   ```
   wget 'https://bit.ly/arm-none-linux-gnueabihf-2019-12' -O gcc-arm-9.2-2019.12-x86_64-arm-none-linux-gnueabihf.tar.xz
   sudo tar xf gcc-arm-9.2-2019.12-x86_64-arm-none-linux-gnueabihf.tar.xz -C /opt
   ```

## Contributing

Issues and Pull Requests for this BSP layer may be opened on our primary
GitHub repository at <https://github.com/SanCloudLtd/meta-sancloud>.

## License

* The metadata and scripts in this repository
  are distributed under the
  [MIT License](https://tldrlegal.com/license/mit-license)
  to provide license compatibility with other Yocto Project layers.

* Patches for third-party software
  are distributed under the license conditions
  of the software being patched.

* Documentation files are distributed under the
  [CC BY 4.0 License](https://tldrlegal.com/license/creative-commons-attribution-4.0-international-(cc-by-4)).

* Trivial data files are distributed under the
  [CC0 1.0 License](https://tldrlegal.com/license/creative-commons-cc0-1.0-universal).
