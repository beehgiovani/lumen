plugins {
    id("com.android.library")
}

android {
    namespace = "com.bruno.lumen.data"
}

dependencies {
    implementation(project(":domain"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
}
