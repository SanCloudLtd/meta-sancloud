# meta-sancloud

Yocto BSP layer for the SanCloud boards - <http://www.sancloud.co.uk/>.

## Quick links

* Git repository web frontend:
  <https://github.com/sancloud/meta-sancloud>
* Mailing list (yocto mailing list): <yocto@sancloud.co.uk>
* Issues management (Github Issues):
  <https://github.com/sancloud/meta-sancloud/issues>
* Documentation: <http://meta-sancloud.readthedocs.io/en/latest/>

## Description

This is the general hardware specific BSP overlay for the SanCloud BeagleBoneEnhanced device.

More information can be found at: <http://www.sancloud.co.uk/> (Official Site)

The core BSP part of meta-sancloud should work with different
OpenEmbedded/Yocto distributions and layer stacks, such as:

* Distro-less (only with OE-Core).
* Yocto/Poky (main focus of testing).

## Dependencies

This layer depends on:

* URI: git://git.yoctoproject.org/poky
  * branch: master
  * revision: HEAD

* URI: git://git.openembedded.org/meta-openembedded
  * layers: meta-oe, meta-multimedia, meta-networking, meta-python
  * branch: master
  * revision: HEAD

## Quick Start

1. source poky/oe-init-build-env
2. Add this layer to bblayers.conf and the dependencies above
3. Set MACHINE in local.conf to one of the supported boards
4. bitbake core-image-base
5. dd to a SD card the generated wic file
6. Boot your BeagleBoneEnhanced.

## Maintainers

* Marc Murphy `<yocto at sancloud.co.uk>`