# Copyright (C) 2021-2022 SanCloud Ltd
# SPDX-License-Identifier: MIT

SUMMARY = "Qualcomm Atheros drivers"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://CORE/HDD/src/wlan_hdd_main.c;beginline=1;endline=27;md5=9efdd522865bb2e5aeec57e2869ed396"

SRC_URI = "git://github.com/SanCloudLtd/qcacld-2.0.git;protocol=https;branch=dev"

SRCREV = "4605c580d052af98df7717ed6a7aede3481a5c1b"
S = "${WORKDIR}/git"

inherit module

do_install() {
    # Ignore the Makefile in qcacld, it only supports installing directly into
    # the host system. It's easier to just invoke the kernel build system
    # directly than to try patching up that Makefile.
    oe_runmake -C "${STAGING_KERNEL_DIR}" M="${S}" \
               DEPMOD=echo INSTALL_MOD_PATH="${D}${root_prefix}" \
               modules_install
}

RDEPENDS:${PN} += "${PN}-firmware"
