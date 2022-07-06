interface Dependencies {

    object Kotlin {

        const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
    }

    object AndroidX {

        const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
        const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"
        const val PREFERENCE = "androidx.preference:preference-ktx:${Versions.PREFERENCE}"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
        const val BIOMETRIC = "androidx.biometric:biometric:${Versions.BIOMETRIC}"

        const val ROOM_COMMON = "androidx.room:room-common:${Versions.ROOM}"
        const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
        const val ROOM_KAPT = "androidx.room:room-compiler:${Versions.ROOM}"

        const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:${Versions.ACTIVITY_COMPOSE}"
    }

    object Google {

        const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
        const val ACCOMPANIST_PAGER = "com.google.accompanist:accompanist-pager:${Versions.ACCOMPANIST_PAGER}"
    }

    object Compose {

        const val MATERIAL = "androidx.compose.material:material:${Versions.COMPOSE}"
        const val UI = "androidx.compose.ui:ui:${Versions.COMPOSE}"
        const val PREVIEW = "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE}"
        const val TOOLING = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE}"
        const val COIL = "io.coil-kt:coil-compose:${Versions.COIL}"
    }

    object LifeCycle {

        const val RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}"
        const val VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.LIFECYCLE}"
    }

    object Hilt {

        const val CORE = "com.google.dagger:hilt-core:${Versions.HILT}"
        const val ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
        const val KAPT = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
        const val NAVIGATION_COMPOSE = "androidx.hilt:hilt-navigation-compose:${Versions.HILT_NAVIGATION_COMPOSE}"
    }

    object UnitTest {

        const val JUNIT = "junit:junit:${Versions.JUNIT}"
        const val MOCKK = "io.mockk:mockk:${Versions.MOCKK}"
        const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES}"
    }

    object AndroidTest {

        const val JUNIT_EXT = "androidx.test.ext:junit:${Versions.JUNIT_EXT}"
        const val ESPRESSO = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}"
        const val COMPOSE_JUNIT4 = "androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE}"
        const val COMPOSE_MANIFEST = "androidx.compose.ui:ui-test-manifest:${Versions.COMPOSE}"
    }

    object Firebase {

        const val BOM = "com.google.firebase:firebase-bom:${Versions.FIREBASE_BOM}"
        const val MESSAGING = "com.google.firebase:firebase-messaging"
        const val ANALYTICS = "com.google.firebase:firebase-analytics"
        const val CRASHLYTICS = "com.google.firebase:firebase-crashlytics"
    }

    object Kakao {

        const val USER = "com.kakao.sdk:v2-user:${Versions.KAKAO_SDK}"
    }

    object Module {

        const val COMMON = "com.upf464.koonsdiary:common:${Versions.COMMON}"
        const val DOMAIN = "com.upf464.koonsdiary:domain:${Versions.DOMAIN}"
        const val DATA = "com.upf464.koonsdiary:data:${Versions.DATA}"
        const val LOCAL = "com.upf464.koonsdiary:local:${Versions.LOCAL}"
        const val REMOTE = "com.upf464.koonsdiary:remote:${Versions.REMOTE}"
        const val PRESENTATION = "com.upf464.koonsdiary:presentation:${Versions.PRESENTATION}"
        const val KAKAO = "com.upf464.koonsdiary:kakao:${Versions.KAKAO}"
        const val FIREBASE = "com.upf464.koonsdiary:firebase:${Versions.FIREBASE}"
    }
}
