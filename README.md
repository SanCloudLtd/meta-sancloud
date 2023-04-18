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

This BSP is layer supports the following configurations:

* [Automotive Grade Linux (AGL)](https://www.automotivelinux.org/)
* [Arago Distribution](http://arago-project.org/wiki/index.php/Main_Page)
* [Poky Reference Distribution](https://www.yoctoproject.org/software-item/poky/)

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

The instructions below assume that you are using `docker`, but `podman` can be
substituted.

#### Poky distribution

```
docker run -it --rm -v "$(pwd):/workdir" --workdir /workdir quay.io/sancloudltd/poky-build:latest
```

```
kas build kas/poky-dunfell-bbe.yml
```

#### Arago distribution

```
docker run -it --rm -v "$(pwd):/workdir" --workdir /workdir quay.io/sancloudltd/arago-build:latest
```

```
kas build kas/arago-dunfell-bbe.yml
```

#### Customisation

The build configuration files in the kas directory can be used as the basis of
further customisation and integration work. It's recommended to copy the build
configuration files into your own repository (adding a `url:` entry for the
meta-sancloud layer) and work there so that your changes can be tracked
separately from future BSP updates in this repository.

### Building without Docker/Podman

We recommend building this BSP under
[Ubuntu 20.04](https://releases.ubuntu.com/20.04/) or
[Ubuntu 22.04](https://releases.ubuntu.com/22.04/).
Building under other distributions supported by the relevant Yocto Project
release is expected to work but has not been tested.

To configure your system to build this BSP, please see
[Required Packages for the Build Host](https://docs.yoctoproject.org/3.1.14/ref-manual/ref-system-requirements.html#required-packages-for-the-build-host)
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

### Building without kas

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

### Building Automotive Grade Linux (AGL)

This BSP layer is included in recent releases of AGL. AGL sources can be
downloaded by following the
[upstream instructions](https://wiki.automotivelinux.org/agl-distro/source-code).
Once the AGL sources have been downloaded and you're in the top-level AGL
directory, run the following commands to build the AGL Telematics Demo image for
the Sancloud BBE:

    source meta-agl/scripts/aglsetup.sh -m bbe agl-demo
    bitbake agl-telematics/demo-platform

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
