HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
DESCRIPTION = "U-Boot, a boot loader for Embedded boards based on PowerPC, \
ARM, MIPS and several other processors, which can be installed in a boot \
ROM and used to initialize and test the hardware or to download and run \
application code."
SECTION = "bootloaders"
DEPENDS += "flex-native bison-native"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"
PE = "1"

DEPENDS += "flex-native bison-native bc-native dtc-native python3-setuptools-native"

require recipes-bsp/u-boot/u-boot.inc

SRC_URI = "git://github.com/SanCloudLtd/u-boot.git;protocol=https;branch=${BRANCH}"
BRANCH = "uboot-bbe-2021.10"
SRCREV = "b99800e547a98b828d391f860c389948ea0279b0"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"
do_configure[cleandirs] = "${B}"

PROVIDES += "u-boot"

# Prevent '-bbe' being inserted into to the u-boot-initial-env filename
UBOOT_INITIAL_ENV = "u-boot-initial-env"
UBOOT_EXTLINUX = "1"
UBOOT_EXTLINUX_ROOT = "root=/dev/mmcblk0p1"
UBOOT_EXTLINUX_CONSOLE = "console=ttyS0,115200n8"

PACKAGES =+ "${PN}-extlinux"
FILES_${PN}-extlinux = "${UBOOT_EXTLINUX_INSTALL_DIR}/${UBOOT_EXTLINUX_CONF_NAME}"
RDEPENDS_${PN} += "${PN}-extlinux"