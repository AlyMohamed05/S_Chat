plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.silverbullet.schat"
    compileSdk = 33

    defaultConfig {
        applicationId ="com.silverbullet.schat"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {

        getByName("debug"){
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }

        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }

    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeCompilerVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation (AndroidX.coreKtx)
    implementation(AndroidX.lifecycleRuntimeKtx)
    implementation(AndroidX.splashscreen)

    implementation(Compose.activity)
    implementation(Compose.ui)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.navigation)
    implementation(Compose.material)
    implementation(Compose.hiltNavigationCompose)
    implementation(Compose.iconsExtension)
    debugImplementation(Compose.debugTooling)

    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.hiltCompiler)

    implementation(Timber.timber)

    implementation(project(Modules.coreData))
    implementation(project(Modules.coreModel))

    testImplementation(Testing.junit4)
    testImplementation(Testing.composeUiTest)

    androidTestImplementation(Testing.junitAndroidExt)
    androidTestImplementation(Testing.composeUiTest)
}