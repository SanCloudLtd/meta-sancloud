SECTION = "kernel"
DESCRIPTION = "Linux kernel for SanCloud BeagleBone devices"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel
require recipes-kernel/linux/linux-dtb.inc

COMPATIBLE_MACHINE = "bbe"

LINUX_VERSION ?= "4.14.71"
SRCREV = "bd5de28772967a3f8a8ec29c0a636cd5d5cd057d"

SRC_URI = " \
    git://git.ti.com/ti-linux-kernel/ti-linux-kernel.git;branch=ti-lsk-linux-4.14.y \
    file://0001-add-am335x-sancloud-bbe.patch \
    file://ARM-Fix-Thumb-2-syscall-return-for-binutils-2.29.patch \
    file://defconfig \
    "
S = "${WORKDIR}/git"
