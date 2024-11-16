pluginManagement {
    repositories {
        maven {
            url = uri("https://maven.myket.ir")
        }
    }
}

dependencyResolutionManagement {
    repositories {
        maven {
            url = uri("https://maven.myket.ir")
        }
    }
}

rootProject.name = "network_connection"
include(":app")
 