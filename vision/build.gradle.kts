plugins {
    id("com.android.library")
}

android {
    namespace = "com.bruno.lumen.vision"
    buildToolsVersion = "36.1.0"
}

dependencies {
    implementation(project(":domain"))
    implementation("com.google.mediapipe:tasks-vision:0.10.35")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    
    // CameraX
    val cameraVersion = "1.4.0"
    implementation("androidx.camera:camera-core:$cameraVersion")
    implementation("androidx.camera:camera-camera2:$cameraVersion")
    implementation("androidx.camera:camera-lifecycle:$cameraVersion")
    implementation("androidx.camera:camera-view:$cameraVersion")
}
