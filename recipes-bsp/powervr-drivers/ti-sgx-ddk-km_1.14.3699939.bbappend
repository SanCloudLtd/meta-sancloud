FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:" 

BRANCH_bbe = "ti-img-sgx/${PV}/k4.9"
SRCREV_bbe = "0086977380d3320d70a3abc78b95fa0641427073"

SRC_URI_bbe = " \
    git://git.ti.com/graphics/omap5-sgx-ddk-linux.git;protocol=git;branch=${BRANCH} \
    file://0001-srvkm-common-devicemem.c-suppress-implicit-fallthrou.patch \
    "
