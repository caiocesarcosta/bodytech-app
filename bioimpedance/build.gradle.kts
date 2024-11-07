plugins {
    id("kotlin-kapt")
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
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

    // Permitir referências ao código gerado
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

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

    //DI
    implementation(Dep.hiltAndroid)
    kapt(Dep.hiltAndroidCompiler)

    //Room
    kapt(Dep.roomCompiler)
    implementation(Dep.roomktx)
    implementation(Dep.roomRuntime)


    // Test
    implementation(Dep.retrofit)
    implementation(Dep.retrofitGsonConverter)
    implementation(Dep.coroutinesAndroid)
    implementation(Dep.lifecycleViewModelCompose)
    implementation(Dep.navigationCompose)
    implementation(Dep.hiltNavigationCompose)
    implementation(Dep.composeRuntimeLivedata)
    implementation(Dep.hiltNavigationFragment)

    testImplementation(TestDep.junit)
    androidTestImplementation(TestDep.extJunit)
    androidTestImplementation(TestDep.espressoCore)
    androidTestImplementation(platform(Dep.composeBom))
    androidTestImplementation(TestDep.composeUiTestJunit4)
    debugImplementation(TestDep.uiTooling)
    debugImplementation(TestDep.uiTestManifest)
}