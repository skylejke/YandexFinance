import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "ru.point.yandexfinance"
    compileSdk = 36

    defaultConfig {
        applicationId = "ru.point.yandexfinance"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        buildConfigField("String", "API_KEY", "\"${properties.getProperty("API_KEY")}\"")
        buildConfigField("String", "BASE_URL", "\"https://shmr-finance.ru/\"")
        buildConfigField("String", "ACCOUNT_ID", "\"${properties.getProperty("ACCOUNT_ID")}\"")
        buildConfigField("String", "APP_VERSION_NAME", "\"$versionName\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
}

dependencies {
    implementation(libs.androidx.work.runtime.ktx)
    implementation(projects.core.network)
    implementation(projects.core.ui)
    implementation(projects.core.utils)
    implementation(projects.core.models.dto)
    implementation(projects.core.navigation)

    implementation(projects.data.database)
    implementation(projects.data.account.api)
    implementation(projects.data.categories.api)
    implementation(projects.data.transactions.api)
    implementation(projects.data.settings.api)
    implementation(projects.data.account.impl)
    implementation(projects.data.categories.impl)
    implementation(projects.data.transactions.impl)
    implementation(projects.data.settings.impl)

    implementation(projects.feature.account)
    implementation(projects.feature.categories)
    implementation(projects.feature.settings)
    implementation(projects.feature.transactions)

    implementation(libs.bundles.dagger)
    ksp(libs.dagger.compiler)
    ksp(libs.dagger.android.processor)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.converter.gson)
    implementation(libs.retrofit)
    implementation(libs.retrofit.adapters.result)
    implementation(libs.splashScreen)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}