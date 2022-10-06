# Copyright (C) 2018-2022 SanCloud Ltd
# SPDX-License-Identifier: MIT

SECTION = "kernel"
DESCRIPTION = "Linux kernel for SanCloud BeagleBone devices"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

inherit kernel
inherit kernel-yocto

# Look in the generic major.minor directory for files
FILESEXTRAPATHS:prepend = "${THISDIR}/linux-bbe-5.10:"

BRANCH ?= "linux-sancloud-5.10.y"
SRC_URI = "git://github.com/SanCloudLtd/linux.git;protocol=https;branch=${BRANCH}"

S = "${WORKDIR}/git"

KBUILD_DEFCONFIG ?= "sancloud_defconfig"
KCONFIG_MODE ?= "alldefconfig"

# Pull in the devicetree files into the rootfs & add run-time dependency for PM
# and prueth firmware to the rootfs
RDEPENDS:${KERNEL_PACKAGE_NAME}-base += " \
    ${KERNEL_PACKAGE_NAME}-devicetree \
    amx3-cm3 \
    prueth-fw \
    "

# Special configuration for remoteproc/rpmsg IPC modules
module_conf_rpmsg_client_sample = "blacklist rpmsg_client_sample"
module_conf_ti_k3_r5_remoteproc = "softdep ti_k3_r5_remoteproc pre: virtio_rpmsg_bus"
module_conf_ti_k3_dsp_remoteproc = "softdep ti_k3_dsp_remoteproc pre: virtio_rpmsg_bus"
KERNEL_MODULE_PROBECONF += "rpmsg_client_sample ti_k3_r5_remoteproc ti_k3_dsp_remoteproc"

KERNEL_EXTRA_ARGS += "DTC_FLAGS=-@"
