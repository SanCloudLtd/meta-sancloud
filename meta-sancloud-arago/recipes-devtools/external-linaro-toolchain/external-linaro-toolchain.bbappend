# We've seen failures in the do_rootfs task due to packages depending on
# 'rtld(GNU_HASH)' which has no providers. This is caused by the
# external-linaro-toolchain recipe overriding the default RPROVIDES value set in
# glibc-package.inc. To fix this we just add back in the missing RPROVIDES
# element.
#
# We will push this change up to the meta-linaro layer itself if possible but
# for now we're just going to carry it here.

RPROVIDES_${PN} += "rtld(GNU_HASH)"
