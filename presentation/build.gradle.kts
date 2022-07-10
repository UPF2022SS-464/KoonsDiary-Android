plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

version = "0.1.1"
apply(from = "../publish_android.gradle")

android {
    compileSdk = Apps.compileSdkVersion

    defaultConfig {
        minSdk = Apps.minSdkVersion
        targetSdk = Apps.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE
    }

    buildFeatures {
        compose = true
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

    implementation(Dependencies.Kotlin.COROUTINES_CORE)

    implementation(Dependencies.AndroidX.CORE_KTX)
    implementation(Dependencies.AndroidX.APPCOMPAT)
    implementation(Dependencies.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Dependencies.AndroidX.BIOMETRIC)
    implementation(Dependencies.AndroidX.ACTIVITY_COMPOSE)
    implementation(Dependencies.Google.ACCOMPANIST_PAGER)
    implementation(Dependencies.Google.MATERIAL)

    implementation(Dependencies.Compose.MATERIAL)
    implementation(Dependencies.Compose.UI)
    implementation(Dependencies.Compose.PREVIEW)
    implementation(Dependencies.Compose.COIL)
    debugImplementation(Dependencies.Compose.TOOLING)

    implementation(Dependencies.LifeCycle.RUNTIME)
    implementation(Dependencies.LifeCycle.VIEWMODEL)

    kapt(Dependencies.Hilt.KAPT)
    implementation(Dependencies.Hilt.ANDROID)
    implementation(Dependencies.Hilt.NAVIGATION_COMPOSE)

    testImplementation(Dependencies.UnitTest.JUNIT)
    testImplementation(Dependencies.UnitTest.MOCKK)
    testImplementation(Dependencies.UnitTest.COROUTINES_TEST)
    debugImplementation(Dependencies.AndroidTest.COMPOSE_MANIFEST)
    androidTestImplementation(Dependencies.AndroidTest.JUNIT_EXT)
    androidTestImplementation(Dependencies.AndroidTest.ESPRESSO)
    androidTestImplementation(Dependencies.AndroidTest.COMPOSE_JUNIT4)

    implementation(Dependencies.Module.DOMAIN)
    implementation(Dependencies.Module.COMMON)
}
