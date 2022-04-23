# Copyright (C) 2021-2022 SanCloud Ltd
# SPDX-License-Identifier: MIT

SUMMARY = "Firmware for BBE Extended+WiFi boards"
LICENSE = "CLOSED"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://github.com/SanCloudLtd/firmware.git;protocol=git;branch=main"
SRCREV = "c30917b92138942a4d06c1efb4d01b1c892b6829"
S = "${WORKDIR}/git"

inherit bin_package

do_install() {
    cd "${S}"
    ./install.sh -v -d "${D}${nonarch_base_libdir}/firmware"
}
