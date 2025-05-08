pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "alexAndroidLab"
include(":app")
include(":module-injector")
include(":core-network")
include(":core-db")
include(":core-utils")
include(":core-db-api")
include(":core-network-api")
include(":core-model")
include(":feature-products")
include(":feature-products-api")
include(":feature-pdp-api")
include(":feature-pdp")
include(":feature-shoppingcart-api")
include(":feature-shoppingcart")
include(":core-navigation")
include(":core-navigation-api")
