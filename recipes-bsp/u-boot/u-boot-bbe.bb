HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"

DEPENDS += "flex-native bison-native bc-native dtc-native"

require recipes-bsp/u-boot/u-boot.inc

SRC_URI = "git://github.com/SanCloudLtd/u-boot.git;protocol=https;branch=uboot-bbe-2020.04"
SRCREV = "59f3a318bd424ad88cb0a457382ea7ab19414356"
PV = "2020.04+git${SRCPV}"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"
do_configure[cleandirs] = "${B}"

PROVIDES += "u-boot"
