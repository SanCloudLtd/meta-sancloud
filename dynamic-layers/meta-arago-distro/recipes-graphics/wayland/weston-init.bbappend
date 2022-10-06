# Copyright (C) 2020-2022 SanCloud Ltd
# SPDX-License-Identifier: MIT

do_configure:append:bbe() {
    sed -i '/\[core\]/a gbm-format=rgb565' ${WORKDIR}/weston.ini
}
