SECTION = "kernel"
DESCRIPTION = "Linux kernel for SanCloud BeagleBone devices"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

inherit kernel

require recipes-kernel/linux/cmem.inc
require recipes-kernel/linux/ti-uio.inc

# Look in the generic major.minor directory for files
FILESEXTRAPATHS_prepend = "${THISDIR}/linux-bbe-5.4:"

SRC_URI = "git://github.com/SanCloudLtd/linux.git;protocol=https;branch=${BRANCH}"

S = "${WORKDIR}/git"

KERNEL_CONFIG_COMMAND = "oe_runmake -C ${S} CC="${KERNEL_CC}" LD="${KERNEL_LD}" O=${B} sancloud_bbe_defconfig"

# Pull in the devicetree files into the rootfs & add run-time dependency for PM
# and prueth firmware to the rootfs
RDEPENDS_${KERNEL_PACKAGE_NAME}-base += " \
    ${KERNEL_PACKAGE_NAME}-devicetree \
    amx3-cm3 \
    prueth-fw \
    "

# Special configuration for remoteproc/rpmsg IPC modules
module_conf_rpmsg_client_sample = "blacklist rpmsg_client_sample"
module_conf_ti_k3_r5_remoteproc = "softdep ti_k3_r5_remoteproc pre: virtio_rpmsg_bus"
module_conf_ti_k3_dsp_remoteproc = "softdep ti_k3_dsp_remoteproc pre: virtio_rpmsg_bus"
KERNEL_MODULE_PROBECONF += "rpmsg_client_sample ti_k3_r5_remoteproc ti_k3_dsp_remoteproc"
