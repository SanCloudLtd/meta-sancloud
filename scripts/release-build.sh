#! /bin/bash

set -euo pipefail

if [[ $# != 1 ]]; then
    echo "ERROR: A single argument must be given to this script to specify the version"
    exit 1
fi

VERSION=$1

mkdir -p release release/sources

# Create layer tarball
git archive --prefix=meta-sancloud-${VERSION}/ -o release/meta-sancloud-${VERSION}.tar.gz ${VERSION}

# Create pre-built images
./scripts/ci-build.sh -Rs
mv images release/poky-images
mv build/tmp/deploy/sources/mirror/* release/sources/

./scripts/ci-build.sh -ARs
mv images release/arago-images
mv build/tmp/deploy/sources/mirror/* release/sources/

# Automatically generate release notes from tag message
cat > release/ReleaseNotes.txt << EOF
Release Notes
=============
EOF
git tag -l --format='%(contents)' ${VERSION} | sed '1d; /-----BEGIN PGP SIGNATURE-----/,$d' >> release/ReleaseNotes.txt
