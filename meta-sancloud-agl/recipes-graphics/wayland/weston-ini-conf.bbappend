do_configure_append_bbe() {
    echo 'gbm-format=rgb565' >> ${WORKDIR}/core.cfg

    sed -i 's/UNNAMED-1/HDMI-A-1/' ${WORKDIR}/unnamed.cfg
}
