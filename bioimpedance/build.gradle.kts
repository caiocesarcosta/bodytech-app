plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.bioimpedance" // Substitua pelo seu namespace
    compileSdk = Versions.compileSdkVersion

    defaultConfig {
        minSdk = Versions.minSdkVersion
        targetSdk = Versions.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = Versions.jvmTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeOptionKotlinCompilerVersion
    }
}

dependencies {
    implementation(platform(Dep.composeBom))
    implementation(Dep.coreKtx)
    implementation(Dep.lifecycleRuntimeKtx)
    implementation(Dep.appcompat)
    implementation(Dep.material)
    implementation(Dep.constraintLayout)
    implementation(Dep.activityCompose)
    implementation(Dep.composeUi)
    implementation(Dep.composeUiGraphics)
    implementation(Dep.composeUiToolingPreview)
    implementation(Dep.composeMaterial3)

    testImplementation(TestDep.junit)
    androidTestImplementation(TestDep.extJunit)
    androidTestImplementation(TestDep.espressoCore)
    androidTestImplementation(platform(Dep.composeBom))
    androidTestImplementation(TestDep.composeUiTestJunit4)
    debugImplementation(TestDep.uiTooling)
    debugImplementation(TestDep.uiTestManifest)
}