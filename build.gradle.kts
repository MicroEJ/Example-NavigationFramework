/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software

Build: 7E4D1F7C
*/
plugins {
    id("com.microej.gradle.application") version "1.6.0"
}

group="com.microej.example"
version="1.0.0"

microej {
    applicationEntryPoint = "com.microej.example.navigationframework.Main"
    // Uncomment to use "prod" architecture when using a VEE Port (defaults to "eval")
    // architectureUsage = "prod"
}

// Fetch the VEE Port as a module dependency (default)
// Update the following variables to set your VEE Port information
val defaultVeePortGroup: String = "com.nxp.vee.mimxrt1170_mapps"
val defaultVeePortModule: String = "vee-port"
val defaultVeePortVersion: String = "3.1.0"

// Allows to override the VEE Port information with command line arguments
val veePortGroup: String = providers.systemProperty("veeport.group").getOrElse(defaultVeePortGroup)
val veePortModule: String = providers.systemProperty("veeport.module").getOrElse(defaultVeePortModule)
val veePortVersion: String = providers.systemProperty("veeport.version").getOrElse(defaultVeePortVersion)

// Fetch the VEE Port by specifying the local sources
// Update the defaultLocalVEEPortPath to your VEE Port local source folder
// Setting a value to defaultLocalVEEPortPath will force Gradle to use the local VEEPort path for building.
// If this variable is not an empty String, Gradle will use it to fetch the VEE Port.
val defaultLocalVEEPortPath: String = ""
val localVEEPortPath: String = providers.systemProperty("local.veeport.path").getOrElse(defaultLocalVEEPortPath)

dependencies {
    implementation("ej.api:edc:1.3.7")
    implementation("ej.api:bon:1.4.4")
    implementation("ej.api:microui:3.6.1")
    implementation("ej.api:microvg:1.5.2")
    implementation("ej.api:drawing:1.0.6")

    implementation("ej.library.ui:mwt:3.7.1")
    implementation("ej.library.ui:widget:5.6.1")

    implementation("ej.library.ui:navigation-framework:1.0.1")

    // VEE Port dependency
    // If any local VEE Port path is provided, look for the local source, otherwise fetch the VEE Port as module dependency
    if (localVEEPortPath.isEmpty()) {
        microejVee("$veePortGroup:$veePortModule:$veePortVersion")
    } else {
        microejVee(files(localVEEPortPath))
    }
}
testing {
   suites {
      val test by getting(JvmTestSuite::class) {
         microej.useMicroejTestEngine(this)

         dependencies {
             implementation(project())
             implementation("ej.api:edc:1.3.7")
             implementation("ej.api:bon:1.4.4")
             implementation("ej.library.test:junit:1.12.0")
         }
      }
   }
}
