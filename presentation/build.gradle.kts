plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

version = "0.1.0"
apply(from = "../android_publish.gradle")

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
        kotlinCompilerExtensionVersion = Dependencies.Version.COMPOSE
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

    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.APPCOMPAT)
    implementation(Dependencies.MATERIAL)
    implementation(Dependencies.CONSTRAINT_LAYOUT)
    implementation(Dependencies.BIOMETRIC)

    implementation(Dependencies.COMPOSE_MATERIAL)
    implementation(Dependencies.COMPOSE_UI)
    implementation(Dependencies.COMPOSE_PREVIEW)
    androidTestImplementation(Dependencies.COMPOSE_TEST_JUNIT4)
    debugImplementation(Dependencies.COMPOSE_TOOLING)
    implementation(Dependencies.LIFECYCLE_RUNTIME)
    implementation(Dependencies.LIFECYCLE_VIEWMODEL)
    implementation(Dependencies.ACTIVITY_COMPOSE)
    implementation(Dependencies.COIL)
    implementation(Dependencies.ACCOMPANIST_PAGER)
    debugImplementation(Dependencies.COMPOSE_TEST_MANIFEST)

    testImplementation(Dependencies.JUNIT)
    testImplementation(Dependencies.MOCKK)
    androidTestImplementation(Dependencies.JUNIT_EXT)
    androidTestImplementation(Dependencies.ESPRESSO)
    testImplementation(Dependencies.COROUTINES_TEST)

    implementation(Dependencies.HILT_ANDROID)
    kapt(Dependencies.HILT_KAPT)
    implementation(Dependencies.HILT_NAVIGATION_COMPOSE)

    implementation(Dependencies.COROUTINES_CORE)

    implementation(Dependencies.DOMAIN)
    implementation(Dependencies.COMMON)
}
