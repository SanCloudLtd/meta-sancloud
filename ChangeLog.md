# ChangeLog for meta-sancloud

## dunfell-r3 (2021-05-31)

This is a maintenance release for the Yocto Project 3.1 "dunfell" release
series.

Changes from dunfell-r2:

* Switch to u-boot-bbe v2021.04-sc1
  * Based on upstream u-boot v2021.04
  * Supports Ethernet access in the bootloader on the BBE
  * Handles DTB selection for the BBE Lite.
* Update kernel to v5.4.106-sc2 & v5.4.106-rt54-sc2
  * Includes patches from TI 07.03.00.005 & 07.03.00.005-rt releases
  * Includes new DTB for the BBE Lite
  * Includes support for Micron Authenta SPI flash via spidev driver
    (backported from Linux v5.13-rc1)
* poky 3.1.7
* meta-ti & meta-arago 07.03.00.005
* Updates to meta-openembedded, meta-rtlwifi & meta-qt5 layers
* Rename default `BBE_KERNEL_PROVIDER` to "default" (from "ti") and RT kernel
  to "rt" (from "ti-rt") to simplify naming
* Add `BBE_KERNEL_PROVIDER` options of "next" & "rt-next" simplify testing of
  WIP kernel patches

## dunfell-r2 (2020-12-10)

This is a maintenance release for the Yocto Project 3.1 "dunfell" release
series.

Changes from dunfell-r1:

* Added SDKs for both Poky and Arago distros
* Dropped public sstate mirrors due to changes in infrastructure
* Updated kas build config files (requires kas v2.2 or later)
* Linux v5.4.74-sc1 & v5.4.74-rt42-sc1
  * Including patches from TI 07.01.00.006 & 07.01.00.006-rt releases
* poky 3.1.4
* meta-ti & meta-arago 07.01.00.006
* Updates to meta-openembedded, meta-arm, meta-rtlwifi and meta-qt5 layers
* Optional support for building with the mainline Linux kernel using the
  meta-linux-mainline layer

## dunfell-r1 (2020-07-07)

Initial release for the Yocto Project 3.1 "dunfell" release series.

Component versions:

* Linux v5.4.40-sc1 & v5.4.43-rt25-sc1
  * Including patches from TI 07.00.00.005 & 07.00.00.005-rt releases
* poky 3.1.1
* meta-ti & meta-arago 07.00.00
* Corresponding versions of meta-openembedded, meta-arm, meta-rtlwifi and
  meta-qt5
