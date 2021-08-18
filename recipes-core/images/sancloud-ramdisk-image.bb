SUMMARY = "Minimal ramdisk image for tftp boot"
LICENSE = "MIT"

IMAGE_INSTALL = "packagegroup-core-boot bmap-tools xz"
IMAGE_LINGUAS = " "

inherit core-image

IMAGE_ROOTFS_SIZE = "24576"
IMAGE_ROOTFS_EXTRA_SPACE = "0"
IMAGE_OVERHEAD_FACTOR = "1.0"
IMAGE_FSTYPES = "squashfs-lz4 ext2.xz"
