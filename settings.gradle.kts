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
include(":app")
include(":feature")
include(":feature:transactions")
include(":feature:settings")
include(":feature:account")
include(":feature:categories")
include(":core")
include(":core:data")
include(":core:ui")
include(":core:utils")
include(":core:data:api")
include(":core:data:impl")
include(":core:res")
include(":core:res:common")
include(":core:res:account")
include(":core:res:categories")
include(":core:res:transactions")
include(":core:res:settings")
