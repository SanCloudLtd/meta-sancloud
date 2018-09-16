SECTION = "kernel"
DESCRIPTION = "Linux kernel for SanCloud BeagleBone devices"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel
require recipes-kernel/linux/linux-dtb.inc

COMPATIBLE_MACHINE = "bbe"

LINUX_VERSION ?= "4.14.62"

SRC_URI[md5sum] = "1df23335700046cc1a0d643e4fb2b0ad"
SRC_URI = " \
    https://cdn.kernel.org/pub/linux/kernel/v4.x/linux-4.14.62.tar.gz \
    file://0001-add-am335x-sancloud-bbe.patch \
    file://ARM-Fix-Thumb-2-syscall-return-for-binutils-2.29.patch \
    file://defconfig \
    "
S = "${WORKDIR}/linux-4.14.62"
