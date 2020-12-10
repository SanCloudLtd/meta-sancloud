#! /bin/bash

set -euo pipefail

if [[ ! -e /etc/os-release ]]; then
    echo "ERROR: No /etc/os-release file found!" >&2
    exit 1
fi

DISTRO=`grep '^ID=' /etc/os-release | cut -d= -f2`

if [[ "$DISTRO" != "ubuntu" ]]; then
    echo "ERROR: This script only supports Ubuntu for now." >&2
    exit 1
fi

sudo apt update
sudo apt install gawk wget diffstat unzip texinfo gcc-multilib g++-multilib \
    build-essential chrpath socat cpio python3 python3-pip python3-pexpect \
    xz-utils debianutils iputils-ping python3-git python3-jinja2 libegl1-mesa \
    libsdl1.2-dev pylint xterm curl locales bison flex bc libssl-dev git

sudo pip3 install kas

if [[ `readlink /bin/sh` == "dash" ]]; then
    echo 'dash dash/sh boolean false' | sudo debconf-set-selections
    sudo DEBIAN_FRONTEND=noninteractive dpkg-reconfigure dash
fi

if [[ ! -e /opt/gcc-arm-9.2-2019.12-x86_64-arm-none-linux-gnueabihf ]]; then
    wget 'https://bit.ly/arm-none-linux-gnueabihf-2019-12' -O \
        gcc-arm-9.2-2019.12-x86_64-arm-none-linux-gnueabihf.tar.xz
    sudo tar xf gcc-arm-9.2-2019.12-x86_64-arm-none-linux-gnueabihf.tar.xz -C /opt
    rm gcc-arm-9.2-2019.12-x86_64-arm-none-linux-gnueabihf.tar.xz
fi

echo "Your host OS is now prepared for running Yocto Project builds with"
echo "the SanCloud BSP."
