# Copyright (C) 2020-2022 SanCloud Ltd
# SPDX-License-Identifier: MIT

# Select MMC partition containing this script
setenv devtype mmc
setenv bootpart ${devnum}:${distro_bootpart}
setenv kernelpath /boot/zImage
setenv fdtpath /boot/${fdtfile}

# Load kernel & device tree
echo Loading kernel ${kernelpath}
load ${devtype} ${bootpart} ${kernel_addr_r} ${kernelpath}
echo Loading fdt ${fdtpath}
load ${devtype} ${bootpart} ${fdt_addr_r} ${fdtpath}

# Set kernel arguments
part uuid ${devtype} ${bootpart} uuid
setenv bootargs console=${console} root=PARTUUID=${uuid} rw rootwait ${optargs}

# Boot Linux
bootz ${kernel_addr_r} - ${fdt_addr_r}
