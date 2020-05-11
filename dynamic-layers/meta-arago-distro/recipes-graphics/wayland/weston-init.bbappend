do_configure_append_bbe() {
    sed -i '/\[core\]/a gbm-format=rgb565' ${WORKDIR}/weston.ini
}
