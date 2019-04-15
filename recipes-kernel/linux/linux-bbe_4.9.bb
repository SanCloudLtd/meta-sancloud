SECTION = "kernel"
DESCRIPTION = "Linux kernel for SanCloud BeagleBone devices"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel

require recipes-kernel/linux/cmem.inc
require recipes-kernel/linux/ti-uio.inc

# Look in the generic major.minor directory for files
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-4.9:"

SRC_URI = "git://bitbucket.sancloud.co.uk/scm/yb/linux.git;protocol=https;branch=linux-sancloud-4.9.y"
SRCREV = "62e6e012ab1b70164b7d12cb966af0113f527fb8"
PV = "4.9.147+git${SRCPV}"

S = "${WORKDIR}/git"

do_configure_append() {
    oe_runmake -C ${S} O=${B} sancloud_bbe_defconfig
}

# Pull in the devicetree files into the rootfs & add run-time dependency for PM
# firmware to the rootfs
RDEPENDS_${KERNEL_PACKAGE_NAME}-base += "${KERNEL_PACKAGE_NAME}-devicetree amx3-cm3"
