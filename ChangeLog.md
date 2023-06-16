<!--
Copyright (c) 2020-2022 SanCloud Ltd
SPDX-License-Identifier: CC-BY-4.0
-->

# ChangeLog for meta-sancloud

## 6.3.0

This release supports the Yocto Project 3.1 "dunfell" series and
includes provisional support for the Yocto Project 4.0 "kirkstone"
series.

Changes from v6.2.0:

* Update to TI BSP 08.06.00.007 (meta-ti & meta-arago layers)

* linux-bbe v5.10.168-sc2 & linux-bbe-rt v5.10.168-rt83-sc2

  * Merge TI kernel releases 08.06.00.007 & 08.06.00.007-rt respectively

  * Backport patches required to build with GCC 13.

* Update to u-boot v2023.04-sc1

* Add support for building with the BeagleBoard.org 5.10 series kernel. This
  can be used to align support between Yocto Project and Debian images for the
  BBE.

* Document kas configuration fragments.

* Improved support for the Yocto Project kirkstone branch. This support is still
  provisional.

  * Based on TI BSP pre-release 09.00.00.001 while still using 5.10 series
    kernel.

  * Support building both Poky and Arago distros.

## 6.2.0

This release supports the Yocto Project 3.1 "dunfell" series and
includes provisional support for the Yocto Project 4.0 "kirkstone"
series.

Changes from v6.1.0:

* Updated readme & build instructions. kas configurations have been renamed to
  the form `{distro}-{version}-{machine}.yml` (e.g. `poky-dunfell-bbe.yml`).

* Poky 3.1.23

* meta-ti & meta-arago 08.03.00.005 release

* linux-bbe v5.10.109-sc2 & linux-bbe-rt v5.10.109-rt65-sc2
  * Merge TI kernel releases 08.03.00.005 & 08.03.00.005-rt respectively
  * Includes new configuration sancloud_tiny_defconfig for deployments where
    space is limited.

* u-boot v2022.10-sc2
  * Includes additional patches to support SPI boot on am335x.

* Initial, provisional support for the Yocto Project 4.0 "kirkstone"
  release series. Currently, kirkstone builds only support headless
  or non-GUI images using the Poky distribution. It is expected that
  more comprehensive kirkstone support will be included in a future
  release of this BSP.

  * A Software Bill of Materials (SBOM) in SPDX format is generated for
    kirkstone builds.

* Fix installation paths for qcacld module.

## 6.1.0

This release supports the Yocto Project 3.1 "dunfell" series.

Changes from dunfell-r6:

* Poky 3.1.17

* meta-ti & meta-arago 08.02.00.006 release

* linux-bbe v5.10.100-sc1 & linux-bbe-rt v5.10.100-rt62-sc1
  * Merge TI kernel releases 08.02.00.006 & 08.02.00.006-rt respectively
  * Reduce kernel footprint and tidy up defconfig

* u-boot-bbe v2022.04-sc1

* qcacld v4.5.25.57

* Other minor changes:
  * Switch to CODEOWNERS file instead of MAINTAINERS
  * Fix linux-bbe-next & linux-bbe-rt-next recipes
  * Fix REUSE spec compliance
  * Add a consolidated maintainer script

## dunfell-r6

This is a maintenance release for the Yocto Project 3.1 "dunfell" release
series.

Changes from dunfell-r5:

* Poky 3.1.13 and updates to the meta-openembedded layer.

* meta-ti & meta-arago 08.01.00.006 release.
  * Included post-release changes to meta-arago to add docker support and fix
    building dma-heap-tests with Poky 3.1.13.

* linux-bbe v5.10.65-sc1 & linux-bbe-rt v5.10.65-rt53-sc1.
  * Merged TI kernel releases 08.01.00.006 & 08.01.00.006-rt respectively.
  * Drop unused cmem & ti-uio features.

* qcacld v4.5.25.55 with additional build fixes.

* Follow the [REUSE spec](https://reuse.software/) by clarifying license
  metadata for all files.

* Follow best practices so we pass the `yocto-check-layer` tests.

* Add automated checks using the [pre-commit framework](https://pre-commit.com/)
  and [GitHub Actions](https://github.com/SanCloudLtd/meta-sancloud/actions).

## dunfell-r5

This is a maintenance release for the Yocto Project 3.1 "dunfell" release
series.

Changes from dunfell-r4.1:

* Poky 3.1.11 and updates to meta-openembedded & meta-rtlwifi layers.

* meta-ti & meta-arago 08.00.00.004 release.
  * Patch PowerVR drivers to build with 5.10 kernel.

* linux-bbe v5.10.41-sc1 & linux-bbe-rt v5.10.41-rt39-sc1.
  * Use kernel-yocto bbclass for easier customization.
  * Includes patches to better support qcacld drivers.
  * Includes spidev patch for Micron Authenta SPI flash.
    (backported from Linux v5.13-rc1)
  * Includes device trees & defconfig based on previous v5.4.xx kernels.

* u-boot-bbe v2021.10-sc1.
  * Use new sancloud_defconfig.
  * Switch back to u-boot script instead of extlinux.conf for flexibility.

* qcacld v4.5.25.53 with patches to support 5.10 kernel series.

* Add SANCLOUD_BSP_VERSION variable so BSP version can be easily captured.
  * Automatically included in IMAGE_BUILDINFO_VARS.

* Add SANCLOUD_RAMDISK_PACKAGES variable so that packages installed in
  sancloud-ramdisk-image can be easily customized.

* Minor improvements to kas build configs.

## dunfell-r4.1

This is a hotfix release for the Yocto Project 3.1 "dunfell" release series.

Changes from dunfell-r4:

* Fixed Arago image booting.

## dunfell-r4

This is a maintenance release for the Yocto Project 3.1 "dunfell" release
series.

Changes from dunfell-r3:

* Poky 3.1.10 and updates to meta-openembedded & meta-rtlwifi layers.

* Update linux-bbe to v5.4.106-sc3 and update linux-bb-rt to v5.4.106-rt54-sc3.

  * Improve support for BBE Extended WiFi variant and rename the device tree to
    `am335x-sancloud-bbe-extended-wifi.dts`.

  * Backport DTB improvements submitted upstream.

* Update u-boot-bbe to v2021.07-sc1.

  * Select appropriate device tree for the current board (BBE, BBE Lite or BBE
    Extended Wifi) at boot time.

  * Replace custom boot script with an `extlinux.conf` file generated by Yocto.

  * Drop u-boot files from boot partition as they're not needed.

* Add recipes for the qcacld driver and firmware required by the BBE Extended
  WiFi variant.

* Add sancloud-ramdisk-image recipe to provide a very lightweight image
  suitable for TFTP boot.

* Build compressed ext4 images to simplify on-device rootfs updates.

* Switch to master branch of meta-linux-mainline to provide updated mainline
  kernel support. Also dropped patches against linux-stable, mainline/stable
  kernels are now used unmodified if enabled.

* Add new kas inc files which can be used to easily add debug tools, systemd and
  connman to images.

* Improve bluetooth support by setting `MACHINE_FEATURES` appropriately.

* Small dependency tidy up to remove duplication.

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
