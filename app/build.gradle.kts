plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.alternator.foroom"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.alternator.foroom"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            if (providers.gradleProperty("foroomTraining").orElse("true").get().toBoolean()) {
                applicationIdSuffix = ".training"
                resValue("string", "app_name", "Foroom Training")
            }
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.squareup.okhttp3)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.glide)
    implementation(libs.facebook.shimmer)
    implementation(libs.insert.koin)
    implementation(libs.lottie)
    implementation(projects.shared)
    implementation(projects.designSystem)
    implementation(projects.navigation)
    implementation(projects.network)
    implementation(libs.retrofit)
    implementation(libs.datastore)
    implementation(libs.splashscreen)
    androidTestImplementation(libs.androidx.espresso.contrib)
    implementation(libs.firebase.crashlytics.buildtools)
    coreLibraryDesugaring(libs.android.desugaring)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}