# Copyright (C) 2020-2022 SanCloud Ltd
# SPDX-License-Identifier: MIT

require conf/linux-mainline/mainline.inc

KBUILD_DEFCONFIG = "multi_v7_defconfig"
KCONFIG_MODE = "alldefconfig"
BBE_WIFI_DRIVERS = ""
RDEPENDS_${KERNEL_PACKAGE_NAME}-base += "${KERNEL_PACKAGE_NAME}-devicetree"
