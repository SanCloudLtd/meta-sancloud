FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

BRANCH_bbe = "ti-img-sgx/${PV}"
SRC_URI_append_bbe = " file://0001-Set-DefaultPixelFormat.patch"
SRCREV_bbe = "bbbd5cbb55e4c54d3b02456ae553bea86fd61506"
