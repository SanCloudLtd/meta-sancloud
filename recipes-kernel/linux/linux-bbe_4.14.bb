SECTION = "kernel"
DESCRIPTION = "Linux kernel for SanCloud BeagleBone devices"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel
require recipes-kernel/linux/linux-dtb.inc

COMPATIBLE_MACHINE = "beaglebone"

FILESPATH =. "${FILE_DIRNAME}/linux-bbe_4.14:${FILE_DIRNAME}/linux-bbe_4.14/${MACHINE}:"

LINUX_VERSION ?= "4.14.62"

KERNEL_IMAGETYPE ?= "zImage"
KERNEL_DEVICETREE ?= "am335x-sancloud-bbe.dtb"

SRC_URI[md5sum] = "1df23335700046cc1a0d643e4fb2b0ad"
SRC_URI = " \
    https://cdn.kernel.org/pub/linux/kernel/v4.x/linux-4.14.62.tar.gz \
    file://0001-add-am335x-sancloud-bbe.patch \
    file://defconfig \
	file://firmware/am335x-pm-firmware.elf \
	file://firmware/am335x-bone-scale-data.bin \
	file://firmware/am335x-pm-firmware.bin \
    "
S = "${WORKDIR}/linux-4.14.62"


do_patch_append () {
    import shutil

    elfsrc = "%s/firmware/am335x-pm-firmware.elf" % d.getVar('WORKDIR')
    elfdestfile = "%s/firmware/am335x-pm-firmware.elf" % d.getVar('S')
    binsrc = "%s/firmware/am335x-bone-scale-data.bin" % d.getVar('WORKDIR')
    bindestfile = "%s/firmware/am335x-bone-scale-data.bin" % d.getVar('S')
    shutil.copyfile(elfsrc, elfdestfile)
    shutil.copyfile(binsrc, bindestfile)
}