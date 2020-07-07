#! /bin/bash

set -e

CONF=$1
shift 1
IMAGES=$@

( kas shell kas/dev/${CONF}.yml -c "bitbake --setscene-only ${IMAGES}" || true ) | sed -e '/^NOTE: .*Started$/d' -e '/^NOTE: Running /d'
kas shell kas/dev/${CONF}.yml -c "bitbake --skip-setscene ${IMAGES}" | sed -e '/^NOTE: .*Started$/d' -e '/^NOTE: Running /d'

mv build/tmp/deploy/images .
