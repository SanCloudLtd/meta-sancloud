SUMMARY = "Qualcomm Atheros drivers"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://CORE/HDD/src/wlan_hdd_main.c;beginline=1;endline=26;md5=f4f17debc2f9aba53097b3fc521d8953"

SRC_URI = "git://github.com/SanCloudLtd/qcacld-2.0.git;protocol=https;branch=dev"

SRCREV = "224286c92175071a44af5d81899c69f53319122c"
S = "${WORKDIR}/git"

inherit module

do_install() {
    # Ignore the Makefile in qcacld, it only supports installing directly into
    # the host system. It's easier to just invoke the kernel build system
    # directly than to try patching up that Makefile.
    oe_runmake -C "${STAGING_KERNEL_DIR}" M="${S}" \
               DEPMOD=echo INSTALL_MOD_PATH="${D}" \
               modules_install
}

RDEPENDS_${PN} += "${PN}-firmware"
