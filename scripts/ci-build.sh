#! /bin/bash

set -euo pipefail

BUILD_DISTRO=poky
BUILD_SDK=no
KERNEL_PROVIDER=
SITE_CONF=
KAS_PREFIX=kas/dev
KAS_INCLUDES="kas/inc/ci.yml"

usage() {
    echo "meta-sancloud CI build script"
    echo "Usage:"
    echo "    $0 [-R] [-A] [-s] [-n] [-k KERNEL] [-i SITE_CONF] [-h]"
    echo ""
    echo "    -R: Build release config (default is development config)."
    echo ""
    echo "    -A: Build the Arago distro (default is the Poky distro)."
    echo ""
    echo "    -s: Build an SDK as well as images."
    echo ""
    echo "    -k KERNEL: Use an alternative kernel recipe"
    echo "        Valid values for KERNEL are 'mainline', 'stable', 'lts' and 'rt'."
    echo ""
    echo "    -i SITE_CONF: Use the given file to provide site-specific configuration."
    echo ""
    echo "    -h: Print this help message and exit".
}

while getopts ":RAsk:i:h" opt; do
    case $opt in
        R)
            KAS_PREFIX=kas
            ;;
        A)
            BUILD_DISTRO=arago
            ;;
        s)
            BUILD_SDK=yes
            ;;
        k)
            KERNEL_PROVIDER=$OPTARG
            ;;
        i)
            SITE_CONF=`realpath $OPTARG`
            ;;
        h)
            usage
            exit 0
            ;;
        \?)
            echo "Invalid option: -$OPTARG" >&2
            usage >&2
            exit 1
            ;;
        :)
            echo "Option missing argument: -$OPTARG" >&2
            usage >&2
            exit 1
            ;;
    esac
done

echo ">>> Preparing for build"

if [[ -n "$KERNEL_PROVIDER" ]]; then
    echo ">>> Enabling kernel provider '$KERNEL_PROVIDER'"
    KAS_INCLUDES="kas/inc/kernel-${KERNEL_PROVIDER}.yml:${KAS_INCLUDES}"
fi

rm -rf images build
mkdir images build

if [[ -n "$SITE_CONF" ]]; then
    echo ">>> Linking '$SITE_CONF' as site configuration"
    mkdir build/conf
    ln -s "$SITE_CONF" build/conf/site.conf
fi

echo ">>> Building images"
kas build --update --force-checkout ${KAS_PREFIX}/bbe-${BUILD_DISTRO}.yml:$KAS_INCLUDES
cp  build/tmp/deploy/images/bbe/MLO \
    build/tmp/deploy/images/bbe/am335x-sancloud-bbe.dtb \
    build/tmp/deploy/images/bbe/modules-bbe.tgz \
    build/tmp/deploy/images/bbe/u-boot.img \
    build/tmp/deploy/images/bbe/u-boot-initial-env \
    build/tmp/deploy/images/bbe/zImage \
    images

for dtb in am335x-sancloud-bbe-icu4.dtb am335x-sancloud-bbei-wifi.dtb am335x-sancloud-bbe-lite.dtb; do
    srcpath="build/tmp/deploy/images/bbe/${dtb}"
    if [[ -e "${srcpath}" ]]; then
        cp "${srcpath}" images
    fi
done

if [[ "$BUILD_DISTRO" == "poky" ]]; then
    cp  build/tmp/deploy/images/bbe/core-image-base-bbe.wic.bmap \
        build/tmp/deploy/images/bbe/core-image-base-bbe.wic.xz \
        build/tmp/deploy/images/bbe/core-image-base.env \
        images
elif [[ "$BUILD_DISTRO" == "arago" ]]; then
    cp  build/tmp/deploy/images/bbe/tisdk-base-image-bbe.wic.bmap \
        build/tmp/deploy/images/bbe/tisdk-base-image-bbe.wic.xz \
        build/tmp/deploy/images/bbe/tisdk-base-image.env \
        build/tmp/deploy/images/bbe/tisdk-default-image-bbe.wic.bmap \
        build/tmp/deploy/images/bbe/tisdk-default-image-bbe.wic.xz \
        build/tmp/deploy/images/bbe/tisdk-default-image.env \
        images
fi

if [[ "$BUILD_SDK" == "yes" ]]; then
    echo ">>> Building SDK"
    kas build ${KAS_PREFIX}/bbe-sdk-${BUILD_DISTRO}.yml:$KAS_INCLUDES
    cp build/tmp/deploy/sdk/${BUILD_DISTRO}-*.sh images
fi
