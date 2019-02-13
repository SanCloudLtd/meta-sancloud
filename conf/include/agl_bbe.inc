# This config is for the Sancloud BBE, adapted from the config provider for the
# standard Beaglebone

AGL_DEFAULT_IMAGE_FSTYPES = "tar.xz wic.xz wic.bmap"

CORE_IMAGE_EXTRA_INSTALL += "packagegroup-machine-base"

#-------------------------------------------------
## Graphics section ##
#-------------------------------------------------
PACKAGES_GFX = "omapdrm-pvr"
IMAGE_INSTALL_append = " ${PACKAGES_GFX}"


# Enable Gfx Pkgs
MACHINE_FEATURES_append = " sgx"
MULTI_PROVIDER_WHITELIST += "virtual/libgl virtual/egl virtual/libgles1 virtual/libgles2"

DEPENDS_remove = "virtual/libgl"

# Preferred providers
PREFERRED_PROVIDER_virtual/libgles1 = ""
PREFERRED_PROVIDER_virtual/libgles2 = "ti-sgx-ddk-um"
PREFERRED_PROVIDER_virtual/egl      = "ti-sgx-ddk-um"
PREFERRED_PROVIDER_virtual/mesa     = "mesa-gl"
PREFERRED_PROVIDER_libgbm           = "libgbm"

#Preferred versions
PREFERRED_VERSION_libgbm            = "10.0.0"

#-------------------------------------------------
## Multimedia section ##
#-------------------------------------------------

# DEFAULTTUNE for cortex-a8
DISTRO_FEATURES_append = " agl-medium-arm-compiler"

SECURITY_CFLAGS_pn-mozjs = ""
SECURITY_LDFLAGS_pn-mozjs = ""
SECURITY_CFLAGS_pn-gpm = ""
SECURITY_LDFLAGS_pn-gpm = ""
SECURITY_CFLAGS_pn-libatasmart = ""
SECURITY_LDFLAGS_pn-libatasmart = ""
