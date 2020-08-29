#! /bin/bash

set -e

CONF=$1

kas build --update --force-checkout kas/dev/${CONF}.yml:kas/inc/ci.yml

mv build/tmp/deploy/images .
