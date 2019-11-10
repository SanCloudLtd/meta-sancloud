HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"

DEPENDS += "flex-native bison-native"

require recipes-bsp/u-boot/u-boot.inc

SRC_URI = "git://github.com/SanCloudLtd/u-boot.git;protocol=https;branch=uboot-bbe-2019.10"
SRCREV = "81a1432e5c4514cc3ca1fec78023afbee638c4f0"
PV = "2019.10+git${SRCPV}"

S = "${WORKDIR}/git"

PROVIDES += "u-boot"
