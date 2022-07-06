plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

version = "0.1.0"
apply(from = "../publish_android.gradle")

android {
    compileSdk = Apps.compileSdkVersion

    defaultConfig {
        minSdk = Apps.minSdkVersion
        targetSdk = Apps.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(Dependencies.Kotlin.COROUTINES_CORE)

    implementation(Dependencies.AndroidX.CORE_KTX)
    implementation(Dependencies.AndroidX.APPCOMPAT)

    kapt(Dependencies.Hilt.KAPT)
    implementation(Dependencies.Hilt.ANDROID)

    testImplementation(Dependencies.UnitTest.JUNIT)
    testImplementation(Dependencies.UnitTest.MOCKK)
    androidTestImplementation(Dependencies.AndroidTest.JUNIT_EXT)
    androidTestImplementation(Dependencies.AndroidTest.ESPRESSO)

    implementation(platform(Dependencies.Firebase.BOM))
    implementation(Dependencies.Firebase.MESSAGING)
    implementation(Dependencies.Firebase.ANALYTICS)

    implementation(Dependencies.Module.DATA)
    implementation(Dependencies.Module.COMMON)
}
