plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "ru.point.data.transactions.impl"
    compileSdk = 35

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
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(projects.core.models.serializable)
    implementation(projects.core.models.dto)
    implementation(projects.core.utils)
    implementation(projects.data.transactions.api)
    implementation(projects.data.database)
    implementation(projects.data.account.api)

    implementation(libs.androidx.work.runtime.ktx)

    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    implementation(libs.bundles.dagger)
    ksp(libs.dagger.compiler)
    ksp(libs.dagger.android.processor)
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}