HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"

require recipes-bsp/u-boot/u-boot.inc

SRC_URI = "git://bitbucket.sancloud.co.uk/scm/yb/u-boot.git;protocol=https;branch=uboot-bbe-2017.03"
SRCREV = "19dfaca3ab903eae2b5d15ffd88aa645ef1c43c4"
PV = "2017.03+git${SRCPV}"

S = "${WORKDIR}/git"

PROVIDES += "u-boot"
