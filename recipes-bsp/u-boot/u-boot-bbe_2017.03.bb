require u-boot-bbe.inc

LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"

TAG = "v2017.03"

# We use the revision in order to avoid having to fetch it from the
# repo during parse
SRCREV = "8537ddd769f460d7fb7a62a3dcc9669049702e51"

SRC_URI += "file://0001-sancloud-bbe.patch"
