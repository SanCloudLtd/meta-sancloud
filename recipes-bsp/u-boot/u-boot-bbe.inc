HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"

require recipes-bsp/u-boot/u-boot.inc

COMPATIBLE_MACHINE = "bbe"

LICENSE = "GPLv2+"

SRC_URI = "git://git.denx.de/u-boot.git"
S = "${WORKDIR}/git"
