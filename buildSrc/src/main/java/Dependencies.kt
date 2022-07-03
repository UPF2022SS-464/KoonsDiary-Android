object Dependencies {
    const val JGIT = "org.eclipse.jgit:org.eclipse.jgit:${Version.JGIT}"
    const val HILT_CORE = "com.google.dagger:hilt-core:${Version.HILT}"
    const val HILT_KAPT = "com.google.dagger:hilt-android-compiler:${Version.HILT}"
    const val JUNIT = "junit:junit:${Version.JUNIT}"
    const val MOCKK = "io.mockk:mockk:${Version.MOCKK}"
    const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.COROUTINES}"

    private object Version {
        const val JGIT = "6.2.0.202206071550-r"
        const val HILT = "2.41"
        const val JUNIT = "4.13.2"
        const val MOCKK = "1.12.3"
        const val COROUTINES = "1.6.0"
    }
}
