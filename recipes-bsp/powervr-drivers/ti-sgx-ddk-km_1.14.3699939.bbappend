FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:" 

BRANCH_bbe = "ti-img-sgx/${PV}/k4.9"
SRC_URI_append_bbe = " file://0001-srvkm-common-devicemem.c-suppress-implicit-fallthrou.patch"
SRCREV_bbe = "0086977380d3320d70a3abc78b95fa0641427073"
