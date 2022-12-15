# Copyright (C) 2018-2022 SanCloud Ltd
# SPDX-License-Identifier: MIT

HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
DESCRIPTION = "U-Boot, a boot loader for Embedded boards based on PowerPC, \
ARM, MIPS and several other processors, which can be installed in a boot \
ROM and used to initialize and test the hardware or to download and run \
application code."
SECTION = "bootloaders"
DEPENDS += "flex-native bison-native"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"
PE = "1"

DEPENDS += "flex-native bison-native bc-native dtc-native python3-setuptools-native"

require recipes-bsp/u-boot/u-boot.inc

SRC_URI = "git://github.com/SanCloudLtd/u-boot.git;protocol=https;branch=${BRANCH}"
BRANCH = "uboot-bbe-2022.10"
SRCREV = "83eb0968cb2f008adebf853584c4ed274956c6f0"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"
do_configure[cleandirs] = "${B}"

PROVIDES += "u-boot"

# Prevent '-bbe' being inserted into to the u-boot-initial-env filename
UBOOT_INITIAL_ENV = "u-boot-initial-env"
