plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
//    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")

}

android {
    namespace = "com.example.bioimpedance" // Substitua pelo seu namespace
    compileSdk = Versions.compileSdkVersion

    defaultConfig {
        minSdk = Versions.minSdkVersion
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
        sourceCompatibility = JavaVersion. VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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

}

dependencies {

    implementation(platform(Dep.firebaseBom))
    implementation(Dep.firebaseFirestore)
    implementation(Dep.firebaseAuth)
    /*    kapt {
            correctErrorTypes = true
        }*/
    implementation(platform(Dep.composeBom))
    implementation(Dep.coreKtx)
    implementation(Dep.lifecycleRuntimeKtx)
//    implementation(Dep.appcompat)
//    implementation(Dep.material)
//    implementation(Dep.constraintLayout)
    implementation(Dep.activityCompose)
    implementation(Dep.composeUi)
    implementation(Dep.composeUiGraphics)
    implementation(Dep.composeUiToolingPreview)
    implementation(Dep.composeMaterial3)

    //DI
    ksp(Dep.hiltAndroidCompiler)
    implementation(Dep.hiltAndroid)

    //Room
    ksp(Dep.roomCompiler)
    implementation(Dep.roomktx)
    implementation(Dep.roomRuntime)
    annotationProcessor(Dep.roomRuntime)
//    ksp(Dep.roomCompiler)


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