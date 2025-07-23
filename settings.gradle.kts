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
    ":feature:account",
    ":feature:categories",
    ":feature:settings",
    ":feature:transactions",

    ":core",
    ":core:models",
    ":core:models:dto",
    ":core:models:serializable",
    ":core:models:vo",
    ":core:network",
    ":core:resources",
    ":core:ui",
    "core:ui:charts",
    ":core:utils",

    ":data",
    ":data:account",
    ":data:account:api",
    ":data:account:impl",
    ":data:categories",
    ":data:categories:api",
    ":data:categories:impl",
    ":data:transactions",
    ":data:transactions:api",
    ":data:transactions:impl",
    ":data:database"
)
