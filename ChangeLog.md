# ChangeLog for meta-sancloud

## dunfell-r2 (2020-12-10)

This is a maintenance release for the Yocto Project 3.1 "dunfell" release
series.

Changes from dunfell-r1:

* Added SDKs for both Poky and Arago distros
* Dropped public sstate mirrors due to changes in infrastructure
* Updated kas build config files (requires kas v2.2 or later)
* Linux v5.4.74-sc1 & v5.4.74-rt42-sc1
  * Including patches from TI 07.01.00.006 & 07.01.00.006-rt releases
* poky 3.1.4
* meta-ti & meta-arago 07.01.00.006
* Updates to meta-openembedded, meta-arm, meta-rtlwifi and meta-qt5 layers
* Optional support for building with the mainline Linux kernel using the
  meta-linux-mainline layer

## dunfell-r1 (2020-07-07)

Initial release for the Yocto Project 3.1 "dunfell" release series.

Component versions:

* Linux v5.4.40-sc1 & v5.4.43-rt25-sc1
  * Including patches from TI 07.00.00.005 & 07.00.00.005-rt releases
* poky 3.1.1
* meta-ti & meta-arago 07.00.00
* Corresponding versions of meta-openembedded, meta-arm, meta-rtlwifi and
  meta-qt5
