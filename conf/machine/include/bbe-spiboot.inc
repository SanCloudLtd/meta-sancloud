# Copyright (C) 2022 SanCloud Ltd
# SPDX-License-Identifier: MIT

SPL_BINARY = "MLO.byteswap"
UBOOT_MACHINE = "sancloud_spiboot_defconfig"

BBE_WIFI_DRIVERS = ""
KBUILD_DEFCONFIG = "sancloud_tiny_defconfig"
KERNEL_CLASSES = "kernel-fitimage"
KERNEL_IMAGETYPES:append = " fitImage"
KERNEL_DEVICETREE = "am335x-sancloud-bbe-lite.dtb"
INITRAMFS_IMAGE = "sancloud-ramdisk-image"

kernel_do_deploy:append() {
    # Deploy a consistently named symlink for the complete fitImage
    # (which includes the initramfs)
    ln -snf fitImage-${INITRAMFS_IMAGE_NAME}-${KERNEL_FIT_NAME}.bin "$deployDir/fitImage-complete-${MACHINE}.bin"
    ln -snf fitImage-${INITRAMFS_IMAGE_NAME}-${KERNEL_FIT_NAME}.bin "$deployDir/fitImage-complete.bin"
}
