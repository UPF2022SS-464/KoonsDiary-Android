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

    implementation(Dependencies.AndroidX.CORE_KTX)
    implementation(Dependencies.AndroidX.APPCOMPAT)
    implementation(Dependencies.Google.MATERIAL)

    kapt(Dependencies.Hilt.KAPT)
    implementation(Dependencies.Hilt.ANDROID)

    androidTestImplementation(Dependencies.AndroidTest.JUNIT_EXT)
    androidTestImplementation(Dependencies.AndroidTest.ESPRESSO)

    implementation(Dependencies.Kakao.USER)

    implementation(platform(Dependencies.Firebase.BOM))
    implementation(Dependencies.Firebase.MESSAGING)
    implementation(Dependencies.Firebase.ANALYTICS)
    implementation(Dependencies.Firebase.CRASHLYTICS)

    implementation(Dependencies.Module.PRESENTATION)
    implementation(Dependencies.Module.LOCAL)
    implementation(Dependencies.Module.KAKAO)
    implementation(Dependencies.Module.FIREBASE)
    implementation(Dependencies.Module.DOMAIN)
    implementation(Dependencies.Module.REMOTE)
    implementation(Dependencies.Module.DATA)
    implementation(Dependencies.Module.COMMON)
}
