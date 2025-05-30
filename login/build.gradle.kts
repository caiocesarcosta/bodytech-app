// :login/build.gradle.kts

plugins {
    alias(libs.plugins.androidLibrary)       // Certifique-se que é androidLibrary aqui!
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinComposeCompiler) // Se este módulo usa Compose
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    // Namespace do seu módulo de biblioteca
    namespace = "com.example.login" // Substitua pelo seu namespace real
    // Referencia as versões do SDK do catálogo
    compileSdk = libs.versions.compileSdk.get().toInt() // Assume compileSdk está no [versions] do TOML

    defaultConfig {
        // Referencia a versão mínima do SDK do catálogo
        minSdk = libs.versions.minSdk.get().toInt() // Assume minSdk está no [versions] do TOML

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro") // Regras do Proguard para quem consumir esta lib
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Habilite 'true' para builds de produção se necessário
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        // Define a compatibilidade Java
        val javaVersion = JavaVersion.VERSION_11
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    kotlinOptions {
        // Define o target da JVM para o Kotlin (referencia a versão do catálogo)
        jvmTarget = libs.versions.jvmTarget.get() // Assume jvmTarget está no [versions] do TOML
    }

    buildFeatures {
        compose = true // Habilita o Jetpack Compose neste módulo
    }

    composeOptions {
        // Define a versão do compilador de extensão do Compose (referencia a versão do catálogo)
        kotlinCompilerExtensionVersion = libs.versions.kotlinComposeCompiler.get()
    }

    packaging {
        // Resolve conflitos comuns com dependências do Compose/Kotlin
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Firebase - BOM gerencia as versões
    implementation(platform(libs.google.firebase.bom))
    // implementation(libs.google.firebase.firestore.ktx) // Exemplo usando alias individual
    // implementation(libs.google.firebase.auth.ktx)      // Exemplo usando alias individual
    implementation(libs.bundles.firebase) // Usando o bundle para Firestore e Auth
    // Jetpack Compose - BOM gerencia as versões
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose) // Usando o bundle para as libs comuns do Compose

    // AndroidX Core (necessário para extensões Kotlin e Lifecycle)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx) // Necessário para escopos de Coroutine/Lifecycle

    // Hilt - Injeção de Dependência
    implementation(libs.bundles.hilt.runtime) // Bundle para Hilt runtime e navegação
    ksp(libs.google.hilt.android.compiler)   // Processador KSP do Hilt

    // Room - Persistência
    /*implementation(libs.bundles.room.runtime) // Bundle para Room runtime e KTX
    ksp(libs.androidx.room.compiler)     */  // Processador KSP do Room

    // Gson (usado pelo Retrofit bundle, mas pode declarar aqui se usar diretamente)
    implementation(libs.google.code.gson)

    // Networking - Retrofit
    implementation(libs.bundles.retrofit) // Bundle para Retrofit e conversor Gson



    // Coroutines
    implementation(libs.jetbrains.kotlinx.coroutines.android)
    implementation(libs.jetbrains.kotlinx.coroutines.play.services) // Kotlin Coroutines com Play Services para await()

    // Testes Unitários (local)
    testImplementation(libs.test.junit)

    // Testes Instrumentados (Android)
    androidTestImplementation(platform(libs.test.androidx.compose.bom)) // BOM para testes de UI do Compose
    androidTestImplementation(libs.test.androidx.compose.ui.test.junit4) // Testes de UI do Compose
    androidTestImplementation(libs.test.androidx.junit) // AndroidX Test JUnit runner
    androidTestImplementation(libs.test.androidx.espresso.core) // Espresso para testes de UI

    // Debug - Ferramentas de UI do Compose (geralmente gerenciadas pelo compose.bom principal)
    debugImplementation(libs.debug.androidx.compose.ui.tooling)
    debugImplementation(libs.debug.androidx.compose.ui.test.manifest)
}

// Configuração do KSP (se necessário, para passar argumentos para Room, etc.)
// ksp {
//    arg("room.schemaLocation", "$projectDir/schemas")
// }

// Configuração do Hilt (geralmente não necessária aqui se o plugin for aplicado)
// hilt {
//    enableAggregatingTask = true
// }