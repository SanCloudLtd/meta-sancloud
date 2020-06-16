# Select MMC partition containing this script
setenv devtype mmc
setenv bootpart ${devnum}:${distro_bootpart}

# Load kernel
run loadimage

# Load devicetree
run findfdt
run loadfdt

# Set kernel arguments
part uuid ${devtype} ${bootpart} uuid
setenv bootargs console=${console} root=PARTUUID=${uuid} rw rootwait ${optargs}

# Boot Linux
bootz ${loadaddr} - ${fdtaddr}
