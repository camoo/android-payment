plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}


android {
    namespace = "cm.camoo.payment"
    compileSdk = 36

    defaultConfig {
        minSdk = 21
        consumerProguardFiles("proguard-rules.pro")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    testOptions {
        unitTests.isIncludeAndroidResources = false
    }
}

/**
 * NEW Kotlin Compiler Options DSL
 */
kotlin {
    compilerOptions {
        jvmTarget.set(
            org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
        )
    }
}

dependencies {
    // Retrofit + Moshi
    api("com.squareup.retrofit2:retrofit:3.0.0")
    api("com.squareup.retrofit2:converter-moshi:3.0.0")
    api("com.squareup.moshi:moshi-kotlin:1.15.2")
    // OkHttp
    api("com.squareup.okhttp3:okhttp:5.3.2")
    api("com.squareup.okhttp3:logging-interceptor:5.3.2")

    // Coroutines
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

    // Tests
    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test:runner:1.7.0")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
}



afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "cm.camoo"
                artifactId = "camoo-payment-android"
                version = "0.1.0"

                pom {
                    name.set("Camoo Payment Android SDK")
                    description.set("Android SDK for Camoo Payment API (cashout, verify, account)")
                    url.set("https://github.com/camoo/android-payment")

                    licenses {
                        license {
                            name.set("Apache-2.0")
                            url.set("https://www.apache.org/licenses/LICENSE-2.0")
                        }
                    }

                    developers {
                        developer {
                            id.set("camoo")
                            name.set("Camoo")
                            email.set("dev@camoo.cm")
                        }
                    }

                    scm {
                        url.set("https://github.com/camoo/android-payment")
                    }
                }
            }
        }

        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/camoo/android-payment")

                credentials {
                    val actor = System.getenv("GITHUB_ACTOR")
                    val token = System.getenv("GITHUB_TOKEN")

                    if (actor != null && token != null) {
                        username = actor
                        password = token
                    }
                }
            }
        }

    }
}
