Yocto BSP layer for the SanCloud boards
=======================================

Quick links
-----------

[<img align=right src="https://www.sancloud.co.uk/wp-content/uploads/2016/09/sancloud_and_address_web.png">](https://www.sancloud.co.uk/)

* [SanCloud website](https://www.sancloud.co.uk/)

* [BeagleBone Enhanced Description](https://www.sancloud.co.uk/beaglebone-enhanced-bbe)

* [Sancloud repositories on GitHub](https://github.com/SanCloudLtd)

Description
-----------

This is the Yocto Project Board Support Package (BSP) layer for SanCloud devices.

Currently supported hardware with corresponding Yocto Project MACHINE names:

* `bbe`: Sancloud BeagleBone Enhanced (BBE)

This BSP is layer supports the following configurations:

* Automotive Grade Linux (AGL)
* Arago Distribution
* Poky Reference Distribution

Getting Started with AGL
------------------------

This BSP layer is included in recent releases of AGL. AGL sources can be
downloaded by following the
[upstream instructions](https://wiki.automotivelinux.org/agl-distro/source-code).
Once the AGL sources have been downloaded and you're in the top-level AGL
directory, run the following commands to build the AGL Demo image for the
Sancloud BBE:

    source meta-agl/scripts/aglsetup.sh -m bbe agl-demo agl-devel
    bitbake agl-demo-platform

Getting started with Arago/Poky
-------------------------------

This BSP layer includes build configuration files for use with the kas build
tool (https://github.com/siemens/kas). The kas tool can be installed by running
`pip install kas` as long as you have a recent Python version.

To use kas to build the Poky distro for the BBE, run the following commands in
the top directory of this repository:

    kas build kas/bbe-poky.yml

Similarly, to build the Arago distro for the BBE:

    kas build kas/bbe-arago.yml

The build configuration files in the kas directory can be used as the basis of
further customisation and integration work. It's recommended to copy the build
configuration files into your own repository (adding a `url:` entry for the
meta-sancloud layer) and work there so that your changes can be tracked
separately from future BSP updates in this repository.

Support
-------

Issues and Pull Requests for this BSP layer may be opened on our primary
GitHub repository at https://github.com/SanCloudLtd/meta-sancloud.

For further support enquiries please contact us via email to
yocto@sancloud.co.uk.
