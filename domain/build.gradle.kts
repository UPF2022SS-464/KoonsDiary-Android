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

    implementation(Dependencies.HILT_CORE)
    kapt(Dependencies.HILT_KAPT)

    testImplementation(Dependencies.JUNIT)
    testImplementation(Dependencies.MOCKK)

    implementation(Dependencies.COROUTINES_CORE)

    implementation(Dependencies.COMMON)
}
