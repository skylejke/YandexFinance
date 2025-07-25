plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "ru.point.feature.transactions"
    compileSdk = 36

    defaultConfig {
        minSdk = 30

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
}

dependencies {
    implementation(projects.core.models.dto)
    implementation(projects.core.models.vo)
    implementation(projects.core.navigation)
    implementation(projects.core.resources)
    implementation(projects.core.utils)
    implementation(projects.core.ui)
    implementation(projects.core.ui.charts)
    implementation(projects.data.transactions.api)
    implementation(projects.data.categories.api)
    implementation(projects.data.account.api)

    implementation(libs.compose.runtime)
    implementation(libs.bundles.dagger)
    ksp(libs.dagger.compiler)
    ksp(libs.dagger.android.processor)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.viewmodel.compose.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    debugImplementation(libs.androidx.ui.tooling)
}