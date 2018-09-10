HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"

require recipes-bsp/u-boot/u-boot.inc

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"
PE = "1"

TAG = "v2018.07-rc3"

# We use the revision in order to avoid having to fetch it from the
# repo during parse
SRCREV = "89c5c976195342344f4caffce016c2de7abb8802"

SRC_URI = " \
           git://git.denx.de/u-boot.git \
           file://0001-am335x_evm-uEnv.txt-bootz-n-fixes.patch"

S = "${WORKDIR}/git"


