# Copyright (C) 2023 SanCloud Ltd
# Based on the recipe in meta-ti
# SPDX-License-Identifier: MIT

SECTION = "kernel"
SUMMARY = "BeagleBoard.org Linux kernel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

COMPATIBLE_MACHINE = "bbe"

inherit kernel
inherit kernel-yocto

DEPENDS += "gmp-native libmpc-native"

S = "${WORKDIR}/git"

# 5.10.162 version for 32-bit
SRCREV = "982fde4eb381f98ec8be946e8d33dd0c9f9416ab"
PV = "5.10.162+git${SRCPV}"
BRANCH = "v5.10.162-ti-r59"

SRC_URI = " \
	git://github.com/beagleboard/linux.git;protocol=https;branch=${BRANCH} \
	file://0001-defconfig-switch-default-kernel-compression-to-LZMA.patch \
	file://0002-Enable-SanCloud-DTBs.patch \
	"

# Pull in the devicetree files into the rootfs & add run-time dependency for PM
# and prueth firmware to the rootfs
RDEPENDS:${KERNEL_PACKAGE_NAME}-base += " \
    ${KERNEL_PACKAGE_NAME}-devicetree \
    amx3-cm3 \
    prueth-fw \
    "

KBUILD_DEFCONFIG ?= "bb.org_defconfig"
KCONFIG_MODE ?= "alldefconfig"

do_kernel_metadata:prepend() {
    # Always use the defconfig file specified in KBUILD_DEFCONFIG.
    # This line prevents warnings such as:
    #     defconfig detected in WORKDIR. ${KBUILD_DEFCONFIG} skipped
    rm -f "${WORKDIR}/defconfig"
}
