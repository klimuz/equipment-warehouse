plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.klimuz.hardwarewarehouse"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.klimuz.hardwarewarehouse"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        packagingOptions {
            resources {
                excludes += "META-INF/DEPENDENCIES"
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
//        packaging {
//            resources {
//                // Исключить или объединить META-INF/DEPENDENCIES, чтобы избежать конфликтов
//                excludes.add("META-INF/DEPENDENCIES")
//            }
//        }
    }

    packagingOptions {
        resources {
            excludes += "META-INF/INDEX.LIST"
            excludes += "META-INF/*.kotlin_module"
        }
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.poi.v540)
    implementation(libs.poi.ooxml.v523)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("androidx.sqlite:sqlite:2.3.1")
    implementation("androidx.sqlite:sqlite-ktx:2.3.1")
    // Apache POI для работы с Excel файлами
    implementation("org.apache.poi:poi:5.2.3")
    implementation("org.apache.poi:poi-ooxml:5.2.3")
    implementation("org.apache.commons:commons-collections4:4.4")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    implementation("com.google.apis:google-api-services-drive:v3-rev136-1.25.0")
    implementation("com.google.auth:google-auth-library-oauth2-http:1.22.0")


    implementation("com.google.apis:google-api-services-drive:v3-rev305-1.25.0")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.36.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.google.api-client:google-api-client-android:1.34.1")
    implementation("com.google.http-client:google-http-client-jackson2:1.41.5")
    implementation("com.google.api-client:google-api-client-android:1.34.1")
    implementation("com.google.api-client:google-api-client-gson:1.34.1")
    implementation("com.google.apis:google-api-services-drive:v3-rev305-1.25.0")
    implementation("com.google.android.gms:play-services-auth:21.3.0")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.google.api-client:google-api-client:1.34.1")
    implementation("com.google.http-client:google-http-client-android:1.41.5")
}


