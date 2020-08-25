#! /bin/bash

set -e

CONF=$1

kas build --update --force-checkout kas/dev/${CONF}.yml | sed -e '/^NOTE: .*Started$/d' -e '/^NOTE: Running /d'

mv build/tmp/deploy/images .
