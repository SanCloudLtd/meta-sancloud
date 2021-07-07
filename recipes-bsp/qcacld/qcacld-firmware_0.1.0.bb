SUMMARY = "Firmware for BBE Extended+WiFi boards"
LICENSE = "CLOSED"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://github.com/SanCloudLtd/firmware.git;protocol=git;branch=main"
SRCREV = "72283874d550a14bb1a063a0f2172960998e0d90"
S = "${WORKDIR}/git"

inherit bin_package

do_install() {
    cd "${S}"
    ./install.sh -v -d "${D}${nonarch_base_libdir}/firmware"
}
