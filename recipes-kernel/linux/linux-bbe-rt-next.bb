python () {
    if d.getVar("PREFERRED_PROVIDER_virtual/kernel") != d.getVar("PN"):
        raise bb.parse.SkipRecipe("Set PREFERRED_PROVIDER_virtual/kernel to %s to enable it" % (d.getVar("PN")))
}

BRANCH = "linux-sancloud-rt-next"
SRCREV = "${AUTOREV}"
PV = "5.4"

require linux-bbe-5.4.inc
