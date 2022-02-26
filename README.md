<!--
Copyright (c) 2018-2022 SanCloud Ltd
SPDX-License-Identifier: CC-BY-4.0
-->

# Yocto BSP layer for the SanCloud boards

[<img align=right src="https://www.sancloud.co.uk/wp-content/uploads/2016/09/sancloud_and_address_web.png">](https://www.sancloud.com/)

[![CI](https://github.com/SanCloudLtd/meta-sancloud/actions/workflows/ci.yml/badge.svg)](https://github.com/SanCloudLtd/meta-sancloud/actions/workflows/ci.yml)
[![pre-commit.ci status](https://results.pre-commit.ci/badge/github/SanCloudLtd/meta-sancloud/dunfell.svg)](https://results.pre-commit.ci/latest/github/SanCloudLtd/meta-sancloud/dunfell)

This is the [Yocto Project](https://www.yoctoproject.org/)
Board Support Package (BSP) layer for [SanCloud](https://sancloud.co.uk/)
devices.

For further support enquiries please contact us via email to
<yocto@sancloud.com>.

## Compatibility

Currently supported hardware with corresponding Yocto Project MACHINE names:

* `bbe`: [Sancloud BeagleBone Enhanced (BBE)](https://sancloud.co.uk/beaglebone-enhanced-bbe/)

This BSP is layer supports the following configurations:

* [Automotive Grade Linux (AGL)](https://www.automotivelinux.org/)
* [Arago Distribution](http://arago-project.org/wiki/index.php/Main_Page)
* [Poky Reference Distribution](https://www.yoctoproject.org/software-item/poky/)

## Host OS preparation

If you are running [Ubuntu 20.04](https://releases.ubuntu.com/20.04/),
we've provided a script to automate the
process of setting up your host system to build images with Yocto Project and
this SanCloud BSP layer. To use this script, run the following command:

    curl https://raw.githubusercontent.com/SanCloudLtd/meta-sancloud/dunfell-r6/scripts/prepare-host.sh | bash

If you are running any other Linux distribution please see
[Required Packages for the Build Host](https://docs.yoctoproject.org/3.1.14/ref-manual/ref-system-requirements.html#required-packages-for-the-build-host)
in the Yocto Project Reference Manual.

In addition to the packages listed in the documentation, 32-bit (aka
multilib) C++ libraries may also need to be installed when building the Arago
distribution. This is done automatically if you're using the
`prepare-host.sh` script.

## Getting started with Poky

This BSP layer is listed in the
[OpenEmbedded Layer Index](http://layers.openembedded.org/)
which makes getting started very easy. Once your OpenEmbedded/Yocto Project
build environment is set up you can use the `bitbake-layers layerindex-fetch`
command to download this layer with all its dependencies and add these layers
to your bblayers.conf file automatically.

If you do not have a Yocto Project build environment set up please first
follow the
[Yocto Project Quick Build Guide](https://docs.yoctoproject.org/3.1.14/brief-yoctoprojectqs/brief-yoctoprojectqs.html)
to ensure that your Linux system has the correct packages installed and that
a simple build succeeds. Once you know that your Linux system is set up
correctly you can download the appropriate Yocto Project version and build an
image for the BBE using the following commands:

    git clone -b dunfell git://git.yoctoproject.org/poky
    cd poky
    source oe-init-build-env
    bitbake-layers layerindex-fetch meta-sancloud
    echo 'MACHINE = "bbe"' >> conf/local.conf
    bitbake core-image-base

## Getting started with Automotive Grade Linux (AGL)

This BSP layer is included in recent releases of AGL. AGL sources can be
downloaded by following the
[upstream instructions](https://wiki.automotivelinux.org/agl-distro/source-code).
Once the AGL sources have been downloaded and you're in the top-level AGL
directory, run the following commands to build the AGL Demo image for the
Sancloud BBE:

    source meta-agl/scripts/aglsetup.sh -m bbe agl-demo agl-devel
    bitbake agl-demo-platform

## Getting started with Arago/Poky using kas

This BSP layer includes build configuration files for use with the
[kas build tool](https://github.com/siemens/kas). This tool can fetch all
layer dependencies (including bitbake) and set up a build directory with
appropriate configuration for the BBE and the chosen distro. It can be
installed by running `pip install kas` as long as you have a recent Python
version.

### Poky

To use kas to build the Poky distro for the BBE, run the following command in
the top directory of this repository:

    kas build kas/bbe-poky.yml

This BSP also supports building a Software Development Kit (SDK) for the Poky
distribution. To use kas to build the SDK, run the following command in the
top directory of this repository:

    kas build kas/bbe-sdk-poky.yml

### Arago

To build the Arago distro for the BBE the appropriate ARM toolchain first
needs to be installed. This is handled automatically if you're using the
`prepare-host.sh` script [described above](#host-os-preparation).

To install the ARM toolchain manually, download
[gcc-arm-9.2-2019.12-x86_64-arm-none-linux-gnueabihf.tar.xz](https://bit.ly/arm-none-linux-gnueabihf-2019-12)
and unpack into /opt. This can be done at the command line using the
following commands:

    wget 'https://bit.ly/arm-none-linux-gnueabihf-2019-12' -O gcc-arm-9.2-2019.12-x86_64-arm-none-linux-gnueabihf.tar.xz
    sudo tar xf gcc-arm-9.2-2019.12-x86_64-arm-none-linux-gnueabihf.tar.xz -C /opt

Once the toolchain is installed in the correct location, run the following
command in the top level of this repository:

    kas build kas/bbe-arago.yml

This BSP also supports building a Software Development Kit (SDK) for the Arago
distribution. To use kas to build the SDK, run the following command in the
top directory of this repository:

    kas build kas/bbe-sdk-arago.yml

### Customisation

The build configuration files in the kas directory can be used as the basis of
further customisation and integration work. It's recommended to copy the build
configuration files into your own repository (adding a `url:` entry for the
meta-sancloud layer) and work there so that your changes can be tracked
separately from future BSP updates in this repository.

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
