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
    const val HILT_KAPT = "com.google.dagger:hilt-android-compiler:${Version.HILT}"
    const val JUNIT = "junit:junit:${Version.JUNIT}"
    const val MOCKK = "io.mockk:mockk:${Version.MOCKK}"
    const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.COROUTINES}"

    private object Version {
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
        const val JUNIT = "4.13.2"
        const val MOCKK = "1.12.3"
        const val COROUTINES = "1.6.0"
    }
}
