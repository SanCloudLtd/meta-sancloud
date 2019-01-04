SECTION = "kernel"
DESCRIPTION = "Linux kernel for SanCloud BeagleBone devices"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel

require recipes-kernel/linux/cmem.inc
require recipes-kernel/linux/ti-uio.inc

# Look in the generic major.minor directory for files
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-4.9:"

# Pull in the devicetree files into the rootfs
RDEPENDS_kernel-base += "kernel-devicetree"

# Add run-time dependency for PM firmware to the rootfs
RDEPENDS_kernel-base_append_ti33x = " amx3-cm3"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"

S = "${WORKDIR}/git"

BRANCH = "linux-sancloud-4.9.y"
SRCREV = "9002752baa10e9f2609b6dc3db4e71ecf3e77b39"
PV = "4.9.69+git${SRCPV}"

KERNEL_GIT_URI = "git://bitbucket.sancloud.co.uk/scm/lin/linux.git"
KERNEL_GIT_PROTOCOL = "https"
SRC_URI = "${KERNEL_GIT_URI};protocol=${KERNEL_GIT_PROTOCOL};branch=${BRANCH}"

do_configure_append() {
    oe_runmake -C ${S} O=${B} sancloud_bbe_defconfig
}
