do_configure:append:bbe() {
    sed -i '/\[core\]/a gbm-format=rgb565' ${WORKDIR}/weston.ini
}
