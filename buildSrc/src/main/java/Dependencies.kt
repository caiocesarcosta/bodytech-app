object Versions {
    const val kotlinVersion = "1.9.22"
    const val applicationVersion = "8.2.2"
    const val compileSdkVersion = 34
    const val minSdkVersion = 21
    const val targetSdkVersion = 34
    const val versionCode = 1
    const val versionName = "1.0"
    const val jvmTarget = "1.8"
    const val appcompatVersion = "1.6.1"
    const val constraintLayoutVersion = "2.1.4"
    const val materialVersion = "1.9.0"
    const val junitVersion = "4.13.2"
    const val extJunitVersion = "1.2.1"
    const val espressoVersion = "3.6.1"
    const val composeBomVersion = "2023.08.00"
    const val testComposeBomVersion = "2023.05.00"
    const val activityComposeVersion = "1.7.0"
    const val composeMaterial3Version = "1.1.2"
    const val uiTestJunit4Version = "1.5.1"
    const val uiToolingVersion = "1.5.1"
    const val lifecycleRuntimeKtxVersion = "2.6.1"
    const val coreKtxVersion = "1.13.1"
    const val composeOptionKotlinCompilerVersion = "1.5.9"
    const val firebaseBomVersion = "32.3.1" // Versão do Firebase BOM
    const val firebaseFirestoreKtxVersion = "24.9.1" // Versão do Firestore
}

object Dep {
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompatVersion}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val material = "com.google.android.material:material:${Versions.materialVersion}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtxVersion}"
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtxVersion}"
    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityComposeVersion}"
    const val composeBom = "androidx.compose:compose-bom:${Versions.composeBomVersion}"
    const val composeUi = "androidx.compose.ui:ui"
    const val composeUiGraphics = "androidx.compose.ui:ui-graphics"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val composeMaterial3 = "androidx.compose.material3:material3:${Versions.composeMaterial3Version}"
    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBomVersion}" // Firebase BOM
    const val firebaseFirestore = "com.google.firebase:firebase-firestore-ktx:${Versions.firebaseFirestoreKtxVersion}" // Firestore
}

object TestDep {
    const val junit = "junit:junit:${Versions.junitVersion}"
    const val extJunit = "androidx.test.ext:junit:${Versions.extJunitVersion}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
    const val composeUiTestJunit4 = "androidx.compose.ui:ui-test-junit4:${Versions.uiTestJunit4Version}"
    const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.uiToolingVersion}"
    const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${Versions.uiToolingVersion}"
    const val composeBom = "androidx.compose:compose-bom:${Versions.testComposeBomVersion}"
}