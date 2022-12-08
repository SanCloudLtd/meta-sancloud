# Copyright (C) 2022 SanCloud Ltd
# SPDX-License-Identifier: MIT

SUMMARY = "SPI flash image for SanCloud BBE Lite"
LICENSE = "MIT"
INHIBIT_DEFAULT_DEPS = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

do_fetch[noexec] = "1"
do_unpack[noexec] = "1"
do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_install[noexec] = "1"
do_package[noexec] = "1"
do_packagedata[noexec] = "1"
do_package_qa[noexec] = "1"
do_package_write_ipk[noexec] = "1"
do_package_write_rpm[noexec] = "1"
do_package_write_deb[noexec] = "1"
do_populate_sysroot[noexec] = "1"

DEPENDS = "xz-native"
do_compile[depends] = "virtual/bootloader:do_deploy virtual/kernel:do_deploy"

do_compile() {
    dd if=/dev/zero bs=1k count=8192 | tr "\000" "\377" > "${WORKDIR}/spiflash.img"
    dd if="${DEPLOY_DIR_IMAGE}/MLO.byteswap-${MACHINE}" of="${WORKDIR}/spiflash.img" conv=notrunc bs=1k
    dd if="${DEPLOY_DIR_IMAGE}/u-boot-${MACHINE}.img" of="${WORKDIR}/spiflash.img" conv=notrunc bs=1k seek=128
    dd if="${DEPLOY_DIR_IMAGE}/fitImage-complete-${MACHINE}.bin" of="${WORKDIR}/spiflash.img" conv=notrunc bs=1k seek=2048
    xz "${WORKDIR}/spiflash.img"
}

inherit deploy

do_deploy() {
    install -d "${DEPLOYDIR}"
    install -m 0644 "${WORKDIR}/spiflash.img.xz" "${DEPLOYDIR}/spiflash-${MACHINE}${IMAGE_VERSION_SUFFIX}.img.xz"
    ln -snf spiflash-${MACHINE}${IMAGE_VERSION_SUFFIX}.img.xz "${DEPLOYDIR}/spiflash-${MACHINE}.img.xz"
    ln -snf spiflash-${MACHINE}${IMAGE_VERSION_SUFFIX}.img.xz "${DEPLOYDIR}/spiflash.img.xz"
}

addtask do_deploy after do_compile before do_build
