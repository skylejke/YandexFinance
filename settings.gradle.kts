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

rootProject.name = "YandexFinance"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    ":app",
    ":feature",
    ":feature:transactions",
    ":feature:settings",
    ":feature:account",
    ":feature:categories",
    ":core",
    ":core:ui",
    ":core:utils",
    ":core:res",
    ":core:res:common",
    ":core:res:account",
    ":core:res:categories",
    ":core:res:transactions",
    ":core:res:settings"
)

include(":data")
include(":data:account")
include(":data:account:api")
include(":data:account:impl")
include(":data:categories")
include(":data:categories:api")
include(":data:categories:impl")
include(":data:transactions")
include(":data:transactions:api")
include(":data:transactions:impl")
include(":core:network")
include(":core:models")
include(":core:models:serializable")
include(":core:models:dto")
include(":core:models:vo")
include(":data:database")
