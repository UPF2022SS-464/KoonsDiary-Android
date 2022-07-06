plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlin-kapt")
}

version = "0.1.0"
apply(from = "../publish_kotlin.gradle")

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {

    implementation(Dependencies.Kotlin.COROUTINES_CORE)

    kapt(Dependencies.Hilt.KAPT)
    implementation(Dependencies.Hilt.CORE)

    testImplementation(Dependencies.UnitTest.JUNIT)
    testImplementation(Dependencies.UnitTest.MOCKK)
}
