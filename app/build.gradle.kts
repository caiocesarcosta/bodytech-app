plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.bodytech"
    compileSdk = Versions.compileSdkVersion

    defaultConfig {
        applicationId = "com.example.bodytech"
        targetSdk = Versions.targetSdkVersion
        minSdk = Versions.minSdkVersion
        versionCode = Versions.versionCode
        versionName = Versions.versionName


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        buildConfig = false
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeOptionKotlinCompilerVersion
    }


}

dependencies {
    implementation(platform(Dep.composeBom))
    implementation(platform(Dep.firebaseFirestore))
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
    implementation(Dep.firebaseBom)

    testImplementation(TestDep.junit)
    androidTestImplementation(TestDep.extJunit)
    androidTestImplementation(TestDep.espressoCore)
    androidTestImplementation(platform(Dep.composeBom))
    androidTestImplementation(TestDep.composeUiTestJunit4)
    debugImplementation(TestDep.uiTooling)
    debugImplementation(TestDep.uiTestManifest)
    implementation(project(":bioimpedance"))
}