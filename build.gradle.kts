plugins {
    id("com.android.application") version "9.0.0" apply false
    id("com.android.library") version "9.0.0" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.3.21" apply false
}

subprojects {
    // Configuração moderna para o compilador Kotlin (2026 Ready)
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }
    }

    // Configuração para módulos de APLICATIVO
    plugins.withType<com.android.build.gradle.AppPlugin>().configureEach {
        extensions.configure<com.android.build.api.dsl.ApplicationExtension> {
            compileSdk = 36
            defaultConfig {
                minSdk = 24
                targetSdk = 36
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }
            packaging {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
                jniLibs {
                    useLegacyPackaging = false
                }
            }
        }
    }

    // Configuração para módulos de BIBLIOTECA
    plugins.withType<com.android.build.gradle.LibraryPlugin>().configureEach {
        extensions.configure<com.android.build.api.dsl.LibraryExtension> {
            compileSdk = 36
            defaultConfig {
                minSdk = 24
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }
            packaging {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
                jniLibs {
                    useLegacyPackaging = false
                }
            }
        }
    }
}
