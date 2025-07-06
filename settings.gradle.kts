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
    ":core:data",
    ":core:ui",
    ":core:utils",
    ":core:data:api",
    ":core:data:impl",
    ":core:res",
    ":core:res:common",
    ":core:res:account",
    ":core:res:categories",
    ":core:res:transactions",
    ":core:res:settings"
)

