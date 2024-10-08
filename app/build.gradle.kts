plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services") version "4.4.0"
    id("com.google.dagger.hilt.android") version "2.48" apply false
}


android {
    namespace = "com.example.bodytech" // Substitua pelo seu namespace
    compileSdk = Versions.compileSdkVersion

    defaultConfig {
        applicationId = "com.example.bodytech"
        minSdk = Versions.minSdkVersion
        targetSdk = Versions.targetSdkVersion
        versionCode = Versions.versionCode
        versionName = Versions.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        buildConfig = false
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeOptionKotlinCompilerVersion
    }
}


dependencies {
// Firebase - Declaração do BOM e do Firestore
    implementation(platform(Dep.firebaseBom))
    implementation(Dep.firebaseFirestore)
    implementation(Dep.firebaseAuth)

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
    implementation(Dep.gson)

    implementation(Dep.hiltAndroid)
    implementation(Dep.retrofit)
    implementation(Dep.retrofitGsonConverter)
    implementation(Dep.coroutinesAndroid)
    implementation(Dep.lifecycleViewModelCompose)
    implementation(Dep.navigationCompose)
    implementation(Dep.hiltNavigationCompose)


//    kapt(Dep.hiltAndroidCompiler)


    testImplementation(TestDep.junit)
    androidTestImplementation(TestDep.extJunit)
    androidTestImplementation(TestDep.espressoCore)
    androidTestImplementation(platform(Dep.composeBom))
    androidTestImplementation(TestDep.composeUiTestJunit4)
    debugImplementation(TestDep.uiTooling)
    debugImplementation(TestDep.uiTestManifest)
    implementation(project(":bioimpedance"))
}