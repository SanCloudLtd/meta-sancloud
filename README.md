# meta-sancloud

Yocto BSP layer for the SanCloud boards - <http://www.sancloud.co.uk/>.

## Quick links

* Git repository web frontend:
  <https://bitbucket.sancloud.co.uk/projects/YB/repos/meta-sancloud>

## Description

This is the general hardware specific BSP overlay for the SanCloud BeagleBoneEnhanced device.

More information can be found at: <http://www.sancloud.co.uk/> (Official Site)

The core BSP part of meta-sancloud should work with different
OpenEmbedded/Yocto distributions and layer stacks, such as:

* Automotive Grade Linux (AGL).
* Arago.
* Distro-less (only with OE-Core).
* Yocto/Poky.

## Dependencies

This layer depends on:

* URI: https://git.yoctoproject.org/git/poky
  * branch: master
  * revision: HEAD

* URI: https://git.yoctoproject.org/git/meta-ti
  * branch: master
  * revision: HEAD

* URI: https://github.com/EmbeddedAndroid/meta-rtlwifi.git
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
