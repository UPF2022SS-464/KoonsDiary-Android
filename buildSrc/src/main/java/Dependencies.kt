object Apps {
    const val minSdkVersion = 26
    const val targetSdkVersion = 31
    const val compileSdkVersion = 31
}

object Dependencies {
    const val COMMON = "com.upf464.koonsdiary:common:${Version.COMMON}"
    const val DOMAIN = "com.upf464.koonsdiary:domain:${Version.DOMAIN}"
    const val DATA = "com.upf464.koonsdiary:data:${Version.DATA}"
    const val LOCAL = "com.upf464.koonsdiary:local:${Version.LOCAL}"
    const val REMOTE = "com.upf464.koonsdiary:remote:${Version.REMOTE}"
    const val PRESENTATION = "com.upf464.koonsdiary:presentation:${Version.PRESENTATION}"
    const val KAKAO = "com.upf464.koonsdiary:kakao:${Version.KAKAO}"
    const val FIREBASE = "com.upf464.koonsdiary:firebase:${Version.FIREBASE}"

    const val JGIT = "org.eclipse.jgit:org.eclipse.jgit:${Version.JGIT}"

    const val HILT_CORE = "com.google.dagger:hilt-core:${Version.HILT}"
    const val HILT_ANDROID = "com.google.dagger:hilt-android:${Version.HILT}"
    const val HILT_KAPT = "com.google.dagger:hilt-android-compiler:${Version.HILT}"
    const val HILT_NAVIGATION_COMPOSE = "androidx.hilt:hilt-navigation-compose:${Version.HILT_NAVIGATION_COMPOSE}"

    const val JUNIT = "junit:junit:${Version.JUNIT}"
    const val JUNIT_EXT = "androidx.test.ext:junit:${Version.JUNIT_EXT}"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:${Version.ESPRESSO}"
    const val MOCKK = "io.mockk:mockk:${Version.MOCKK}"
    const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.COROUTINES}"
    const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.COROUTINES}"

    const val FIREBASE_BOM = "com.google.firebase:firebase-bom:${Version.FIREBASE_BOM}"
    const val FIREBASE_MESSAGING = "com.google.firebase:firebase-messaging"
    const val FIREBASE_ANALYTICS = "com.google.firebase:firebase-analytics"
    const val KAKAO_SDK_USER = "com.kakao.sdk:v2-user:${Version.KAKAO_SDK}"

    const val CORE_KTX = "androidx.core:core-ktx:${Version.CORE_KTX}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:${Version.APPCOMPAT}"
    const val PREFERENCE = "androidx.preference:preference-ktx:${Version.PREFERENCE}"
    const val ROOM_COMMON = "androidx.room:room-common:${Version.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Version.ROOM}"
    const val ROOM_KAPT = "androidx.room:room-compiler:${Version.ROOM}"

    const val MATERIAL = "com.google.android.material:material:${Version.MATERIAL}"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Version.CONSTRAINT_LAYOUT}"
    const val BIOMETRIC = "androidx.biometric:biometric:${Version.BIOMETRIC}"

    const val COMPOSE_MATERIAL = "androidx.compose.material:material:${Version.COMPOSE}"
    const val COMPOSE_UI = "androidx.compose.ui:ui:${Version.COMPOSE}"
    const val COMPOSE_PREVIEW = "androidx.compose.ui:ui-tooling-preview:${Version.COMPOSE}"
    const val COMPOSE_TOOLING = "androidx.compose.ui:ui-tooling:${Version.COMPOSE}"
    const val COMPOSE_TEST_JUNIT4 = "androidx.compose.ui:ui-test-junit4:${Version.COMPOSE}"
    const val COMPOSE_TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest:${Version.COMPOSE}"

    const val ACCOMPANIST_PAGER = "com.google.accompanist:accompanist-pager:${Version.ACCOMPANIST_PAGER}"
    const val COIL = "io.coil-kt:coil-compose:${Version.COIL}"
    const val LIFECYCLE_RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.LIFECYCLE}"
    const val LIFECYCLE_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-compose:${Version.LIFECYCLE}"
    const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:${Version.ACTIVITY_COMPOSE}"

    object Version {
        const val COMMON = "0.1.0"
        const val DOMAIN = "0.1.0"
        const val DATA = "0.1.0"
        const val LOCAL = "0.1.0"
        const val REMOTE = "0.1.0"
        const val PRESENTATION = "0.1.0"
        const val KAKAO = "0.1.0"
        const val FIREBASE = "0.1.0"

        const val JGIT = "6.2.0.202206071550-r"

        const val HILT = "2.41"
        const val HILT_NAVIGATION_COMPOSE = "1.0.0"

        const val JUNIT = "4.13.2"
        const val JUNIT_EXT = "1.1.3"
        const val ESPRESSO = "3.4.0"
        const val MOCKK = "1.12.3"
        const val COROUTINES = "1.6.0"

        const val FIREBASE_BOM = "29.1.0"
        const val KAKAO_SDK = "2.8.6"

        const val CORE_KTX = "1.7.0"
        const val APPCOMPAT = "1.4.1"
        const val PREFERENCE = "1.2.0"
        const val ROOM = "2.4.2"

        const val COMPOSE = "1.1.1"

        const val MATERIAL = "1.5.0"
        const val CONSTRAINT_LAYOUT = "2.1.3"
        const val BIOMETRIC = "1.1.0"

        const val ACCOMPANIST_PAGER = "0.23.1"
        const val COIL = "2.0.0-rc01"
        const val LIFECYCLE = "2.5.0"
        const val ACTIVITY_COMPOSE = "1.4.0"
    }
}
