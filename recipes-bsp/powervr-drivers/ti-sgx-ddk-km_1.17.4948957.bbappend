# Copyright (C) 2018-2022 SanCloud Ltd
# SPDX-License-Identifier: MIT

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-Fix-driver-config-for-5.10-kernel.patch"
