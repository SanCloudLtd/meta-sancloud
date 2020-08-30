#! /bin/bash

set -e

CONF=$1

mkdir images
IMAGES_DIR=`realpath images`

kas build --update --force-checkout kas/dev/${CONF}.yml:kas/inc/ci.yml

cd build/tmp/deploy/images/bbe

if [[ "$CONF" == "bbe-poky" ]]; then
    cp  MLO \
        am335x-sancloud-bbe.dtb \
        am335x-sancloud-bbe-icu4.dtb \
        am335x-sancloud-bbei-wifi.dtb  \
        modules-bbe.tgz \
        u-boot.img \
        u-boot-initial-env \
        zImage \
        core-image-base-bbe.wic.bmap \
        core-image-base-bbe.wic.xz \
        core-image-base.env \
        ${IMAGES_DIR}
elif [[ "$CONF" == "bbe-arago" ]]; then
    cp  MLO \
        am335x-sancloud-bbe.dtb \
        am335x-sancloud-bbe-icu4.dtb \
        am335x-sancloud-bbei-wifi.dtb  \
        modules-bbe.tgz \
        u-boot.img \
        u-boot-initial-env \
        zImage \
        tisdk-base-image-bbe.wic.bmap \
        tisdk-base-image-bbe.wic.xz \
        tisdk-base-image.env \
        tisdk-default-image-bbe.wic.bmap \
        tisdk-default-image-bbe.wic.xz \
        tisdk-default-image.env \
        ${IMAGES_DIR}
fi
