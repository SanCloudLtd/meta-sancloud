# Copyright (C) 2020-2022 SanCloud Ltd
# SPDX-License-Identifier: MIT

PREFERRED_PROVIDER_virtual/kernel:bbe = "linux-bb.org"
KERNEL_DEVICETREE += " \
    am335x-sancloud-bbe-extended-wifi.dtb \
    am335x-sancloud-bbe-lite.dtb \
    "
