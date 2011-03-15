JNLP Applet Loader provides a way to create a LWJGL Applet Loader based on a JNLP file.

It is a maven project and it is based on the next modules: 

	jnlpappletloader-jnlp 

		This module contains all JNLP core classes and utilities in order to interact with JNLP files in an
		easy way.

	jnlpappletloader-applet

		This module is where the main Applet is, which loads the JNLP file using jnlp module classes and then
		launch the LWJGL Applet Loader with built parameters from JNLP file.

	jnlpappletloader-deployable

		This module provides a deployable project to deploy on a server in order to check the first module
		works right.

	jnlpappletloader-full

		This module provides a generated jar with all dependencies (the LWJGL Applet loader and the JNLP Applet
		Loader) for fast loading when starting an applet.

In jnlpappletloader-status.html you can see how the applet should be configured.

It is mainly a Wrapper for the LWJGL Applet Loader, so it couldn't be used for any Applet.
