SUMMARY = "Qualcomm Atheros drivers"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://CORE/HDD/src/wlan_hdd_main.c;beginline=1;endline=26;md5=f4f17debc2f9aba53097b3fc521d8953"

SRC_URI = " \
    https://source.codeaurora.org/external/wlan/qcacld-2.0/snapshot/${PV}.tar.gz \
    file://0001-Fix-build-with-Linux-5.4-GCC-11.patch \
    file://am335x_debian.patch \
    "
SRC_URI[sha256sum] = "4cf555a1513bd90b0dcaa3f0e7266cfc480dbd5a526855ed2aef7093f5372fee"
S = "${WORKDIR}/${PV}"

inherit module

EXTRA_OEMAKE += " \
    CONFIG_CLD_HL_SDIO_CORE=y \
    CONFIG_PER_VDEV_TX_DESC_POOL=1 \
    SAP_AUTH_OFFLOAD=1 \
    CONFIG_QCA_LL_TX_FLOW_CT=1 \
    CONFIG_WLAN_FEATURE_FILS=y \
    CONFIG_FEATURE_COEX_PTA_CONFIG_ENABLE=y \
    CONFIG_QCA_SUPPORT_TXRX_DRIVER_TCP_DEL_ACK=y \
    CONFIG_WLAN_WAPI_MODE_11AC_DISABLE=y \
    CONFIG_WLAN_WOW_PULSE=y \
    CONFIG_TXRX_PERF=y \
    "

do_install() {
    # Ignore the Makefile in qcacld, it only supports installing directly into
    # the host system. It's easier to just invoke the kernel build system
    # directly than to try patching up that Makefile.
    oe_runmake -C "${STAGING_KERNEL_DIR}" M="${S}" \
               DEPMOD=echo INSTALL_MOD_PATH="${D}" \
               modules_install
}

RDEPENDS_${PN} += "${PN}-firmware"
