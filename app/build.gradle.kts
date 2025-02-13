plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.remziakgoz.todolistwithcompose"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.remziakgoz.todolistwithcompose"
        minSdk = 24
        targetSdk = 34
        versionCode = 7
        versionName = "1.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.core.ktx)
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.androidx.runner)
    ksp(libs.androidx.room.compiler)
    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.androidx.room.ktx)
    implementation(libs.io.insert.koin)
    implementation(libs.io.workmanager.koin)
    implementation(libs.io.navigation.koin)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.compose.material3)
    implementation(libs.android.arch.core.testing)
    implementation(libs.kotlinx.coroutine.test)
    implementation(libs.test.truth)
    testImplementation(libs.junit)
    testImplementation(libs.mockito)
    testImplementation(libs.mockitoKotlin)
    testImplementation(libs.junitEngine)
    testImplementation(libs.mockk)
    testImplementation(libs.androidx.junit.ktx)
    testImplementation(libs.kotlinx.coroutine.test)
    testImplementation(libs.test.truth)
    testImplementation(libs.androidx.ui.test.junit4)
    testImplementation(libs.lifecycle.livedata.ktx)
    testImplementation(libs.lifecycle.viewmodel.ktx)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
