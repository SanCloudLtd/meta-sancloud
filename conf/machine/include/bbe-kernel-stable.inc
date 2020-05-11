PREFERRED_PROVIDER_virtual/kernel_bbe = "linux-stable"
KERNEL_CONFIG_COMMAND = "oe_runmake -C ${S} CC="${KERNEL_CC}" LD="${KERNEL_LD}" O=${B} multi_v7_defconfig"
MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "kernel-devicetree"
