// bioimpedance/build.gradle.kts

plugins {
    alias(libs.plugins.androidLibrary)       // Certifique-se que é androidLibrary aqui!
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinComposeCompiler) // Se este módulo usa Compose
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.bodytech.bioimpedance" // Ajuste o namespace para o módulo
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        // SEM applicationId, versionCode, versionName para módulos de biblioteca

        // --- AQUI É O LUGAR CORRETO PARA consumerProguardFiles ---
        consumerProguardFiles("consumer-rules.pro") // Esta linha deve estar aqui!
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Habilite 'true' para builds de produção
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro" // Estas regras são para o JAR da biblioteca
            )
        }
        // Se tiver outras buildTypes, você pode adicionar consumerProguardFiles nelas também, se necessário
    }

    compileOptions {
        val javaVersion = JavaVersion.VERSION_11
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }

    buildFeatures {
        compose = true // Se este módulo usa Compose
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinComposeCompiler.get()
    }
}

dependencies {
    // Dependência do módulo core, se precisar
//    implementation(project(":core"))

    // Firebase - BOM gerencia as versões
    implementation(platform(libs.google.firebase.bom))
    // implementation(libs.google.firebase.firestore.ktx) // Exemplo usando alias individual
    // implementation(libs.google.firebase.auth.ktx)      // Exemplo usando alias individual
    implementation(libs.bundles.firebase) // Usando o bundle para Firestore e Auth

    // Dependências específicas do módulo bioimpedance
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)

    // Gson (usado pelo Retrofit bundle, mas pode declarar aqui se usar diretamente)
    implementation(libs.google.code.gson)

    // Networking - Retrofit
    implementation(libs.bundles.retrofit) // Bundle para Retrofit e conversor Gson


    // Se o módulo usa Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)

    // Se o módulo usa Hilt
    implementation(libs.google.hilt.android)
    ksp(libs.google.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose) // Se este módulo tiver navegação via Compose

    // Testes
    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
    androidTestImplementation(platform(libs.test.androidx.compose.bom))
    androidTestImplementation(libs.test.androidx.compose.ui.test.junit4)
}