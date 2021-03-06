#! /bin/bash

set -euo pipefail

if [[ $# != 1 ]]; then
    echo "ERROR: A single argument must be given to this script to specify the version"
    exit 1
fi

VERSION=$1

echo "meta-sancloud ${VERSION}" > tag.txt
markdown-extract -fri "^${VERSION}" ChangeLog.md | sed -e :a -e '/^\n*$/{$d;N;};/\n$/ba' >> tag.txt
git tag -a -F tag.txt "${VERSION}" HEAD
rm -f tag.txt
