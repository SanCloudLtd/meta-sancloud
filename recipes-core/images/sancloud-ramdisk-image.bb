# Copyright (C) 2018-2022 SanCloud Ltd
# SPDX-License-Identifier: MIT

SUMMARY = "Minimal ramdisk image for tftp boot"
LICENSE = "MIT"

SANCLOUD_RAMDISK_PACKAGES ?= ""
IMAGE_INSTALL = "packagegroup-core-boot ${SANCLOUD_RAMDISK_PACKAGES}"
IMAGE_LINGUAS = " "

inherit core-image

IMAGE_FSTYPES = "cpio.xz"
