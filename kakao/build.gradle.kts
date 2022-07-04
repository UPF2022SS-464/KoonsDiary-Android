plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
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
    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.APPCOMPAT)

    testImplementation(Dependencies.JUNIT)
    testImplementation(Dependencies.MOCKK)
    androidTestImplementation(Dependencies.JUNIT_EXT)
    androidTestImplementation(Dependencies.ESPRESSO)

    implementation(Dependencies.HILT_ANDROID)
    kapt(Dependencies.HILT_KAPT)

    implementation(Dependencies.KAKAO_SDK_USER)

    implementation(Dependencies.COROUTINES_CORE)

    implementation(Dependencies.DATA)
    implementation(Dependencies.COMMON)
}
