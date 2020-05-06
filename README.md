Yocto BSP layer for the SanCloud boards
=======================================

Quick links
-----------

[<img align=right src="https://www.sancloud.co.uk/wp-content/uploads/2016/09/sancloud_and_address_web.png">](https://www.sancloud.co.uk/)

* [SanCloud website](https://www.sancloud.co.uk/)

* [BeagleBone Enhanced Description](https://www.sancloud.co.uk/?page_id=254)

* [Sancloud repositories on GitHub](https://github.com/SanCloudLtd)

Description
-----------

This is the Yocto Project Board Support Package (BSP) layer for SanCloud devices.

Currently supported hardware with corresponding Yocto Project MACHINE names:

* `bbe`: Sancloud BeagleBone Enhanced (BBE)

This BSP is layer is tested in two primary configurations:

* Automotive Grade Linux (AGL)
* Arago Distribution

Additionally, this BSP layer should work with the distro-less configuration
included in openembedded-core as well as the Poky distribution.

Getting Started with AGL
------------------------

This BSP layer is included in the most recent stable branch of AGL, code-named
Grumpy Guppy. AGL sources can be downloaded by following the
[upstream instructions](https://wiki.automotivelinux.org/agl-distro/source-code).
Once the AGL sources have been downloaded and you're in the top-level AGL
directory, run the following commands to build the AGL Demo image for the
Sancloud BBE:

    source meta-agl/scripts/aglsetup.sh -m bbe agl-demo agl-devel
    bitbake agl-demo-platform

Getting started with Arago
--------------------------

A pre-integrated Arago Distribution repository is available at
https://github.com/SanCloudLtd/sancloud-arago. Please follow the instructions
in the README.md file in that repository to get started.

Support
-------

Issues and Pull Requests for this BSP layer may be opened on our primary
GitHub repository at https://github.com/SanCloudLtd/meta-sancloud.

For further support enquiries please contact us via email to
yocto@sancloud.co.uk.
