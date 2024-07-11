pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven{ setUrl("https://jitpack.io")}
    }
}

rootProject.name = "StoreOnline"
include(":app")
include(":authorization:api")
include(":core:database-local-user")
include(":authorization:data")
include(":features:feature-authorization")
include(":features:feature-products")
include(":main:api")
include(":main:database")
include(":main:data")

include(":core:components-ui")
