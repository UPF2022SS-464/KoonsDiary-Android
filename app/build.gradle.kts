plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    compileSdk = Apps.compileSdkVersion

    defaultConfig {
        applicationId = "com.upf464.koonsdiary"
        minSdk = Apps.minSdkVersion
        targetSdk = Apps.targetSdkVersion
        versionCode = Apps.versionCode
        versionName = Apps.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.APPCOMPAT)
    implementation(Dependencies.MATERIAL)

    androidTestImplementation(Dependencies.JUNIT_EXT)
    androidTestImplementation(Dependencies.ESPRESSO)

    implementation(Dependencies.HILT_ANDROID)
    kapt(Dependencies.HILT_KAPT)

    implementation(Dependencies.KAKAO_SDK_USER)

    implementation(platform(Dependencies.FIREBASE_BOM))
    implementation(Dependencies.FIREBASE_MESSAGING)
    implementation(Dependencies.FIREBASE_ANALYTICS)
    implementation(Dependencies.FIREBASE_CRASHLYTICS)

    implementation(Dependencies.PRESENTATION)
    implementation(Dependencies.LOCAL)
    implementation(Dependencies.KAKAO)
    implementation(Dependencies.FIREBASE)
    implementation(Dependencies.DOMAIN)
    implementation(Dependencies.REMOTE)
    implementation(Dependencies.DATA)
    implementation(Dependencies.COMMON)
}
