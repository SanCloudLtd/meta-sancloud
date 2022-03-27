# Copyright (C) 2018-2022 SanCloud Ltd
# SPDX-License-Identifier: MIT

python () {
    if d.getVar("PREFERRED_PROVIDER_virtual/kernel") != d.getVar("PN"):
        raise bb.parse.SkipRecipe("Set PREFERRED_PROVIDER_virtual/kernel to %s to enable it" % (d.getVar("PN")))
}

KERNEL_VERSION_SANITY_SKIP="1"
BRANCH = "linux-sancloud-rt-next"
SRCREV = "${AUTOREV}"
require linux-bbe-5.10.inc
