// app/build.gradle.kts

plugins {
    // Aplica os plugins usando os aliases do catálogo de versões (libs)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose.compiler) // Necessário para Jetpack Compose
    alias(libs.plugins.google.services)         // Plugin do Google Services (Firebase, etc.)
    alias(libs.plugins.hilt)                    // Plugin do Hilt para injeção de dependência
    alias(libs.plugins.ksp)                     // KSP para processadores de anotação (Hilt, Room)
    // alias(libs.plugins.kotlin.kapt) // Descomente se ainda usar Kapt para algum processador
}

android {
    // Namespace do seu aplicativo
    namespace = "com.example.bodytech" // Substitua pelo seu namespace real
    // Referencia as versões do SDK do catálogo
    compileSdk = libs.versions.compileSdk.get().toInt()
    // compileSdk = 34 // Alternativa: definir diretamente

    defaultConfig {
        applicationId = "com.example.bodytech" // Substitua pelo seu applicationId real
        // Referencia as versões do SDK e do App do catálogo
        minSdk = libs.versions.minSdk.get().toInt() // Assume minSdk está no [versions] do TOML
        targetSdk = libs.versions.targetSdk.get().toInt() // Assume targetSdk está no [versions] do TOML
        versionCode = libs.versions.versionCode.get().toInt() // Assume versionCode está no [versions] do TOML
        versionName = libs.versions.versionName.get() // Assume versionName está no [versions] do TOML

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // Vector Drawables (recomendado)
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Habilite 'true' para builds de produção reais
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        // Define a compatibilidade Java (referencia a versão do catálogo se definida, senão define diretamente)
        val javaVersion = JavaVersion.VERSION_11 // Ou use libs.versions.jvmTarget.get() se mapeado para Java version
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    kotlinOptions {
        // Define o target da JVM para o Kotlin (referencia a versão do catálogo)
        jvmTarget = libs.versions.jvmTarget.get() // Assume jvmTarget está no [versions] do TOML
    }

    buildFeatures {
        compose = true // Habilita o Jetpack Compose
        // buildConfig = false // Desabilitado no seu original, mantido
    }

    composeOptions {
        // Define a versão do compilador de extensão do Compose (referencia a versão do catálogo)
        kotlinCompilerExtensionVersion = libs.versions.kotlinComposeCompiler.get() // Usa a mesma versão definida para o plugin
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
    // implementation(libs.androidx.activity.compose) // Exemplo individual
    // ... outras dependências individuais do compose ...
    implementation(libs.bundles.compose) // Usando o bundle para as libs comuns do Compose

    // AndroidX Core & UI (alguns podem não estar no bundle compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat) // Necessário para temas AppCompat
    implementation(libs.google.material)     // Material Design Components
    // implementation(libs.androidx.constraintlayout) // Se ainda usar ConstraintLayout XML

    // Gson (usado pelo Retrofit bundle, mas pode declarar aqui se usar diretamente)
    // implementation(libs.google.code.gson)

    // Hilt - Injeção de Dependência
    implementation(libs.bundles.hilt.runtime)
    implementation(libs.androidx.lifecycle.runtime.compose.android) // Bundle para Hilt runtime e navegação
    ksp(libs.google.hilt.android.compiler)   // Processador KSP do Hilt

    // Databinding Adapters (dependência específica que você tinha)
    implementation(libs.databinding.adapters)

    // Networking - Retrofit
    implementation(libs.bundles.retrofit) // Bundle para Retrofit e conversor Gson

    // Coroutines
    implementation(libs.jetbrains.kotlinx.coroutines.android)
    implementation(libs.jetbrains.kotlinx.coroutines.play.services) // Kotlin Coroutines com Play Services para await()

    // Room - Persistência
/*    implementation(libs.bundles.room.runtime) // Bundle para Room runtime e KTX
    ksp(libs.androidx.room.compiler)*/       // Processador KSP do Room
    // kapt(libs.androidx.room.compiler)    // Use se preferir Kapt

    // Dependências de Módulos Locais
    implementation(project(":bioimpedance"))
    implementation(project(":login"))

    // Testes Unitários (local)
    // testImplementation(libs.test.junit) // Exemplo individual
    testImplementation(libs.bundles.test.core) // Bundle para JUnit e Espresso (core não usado aqui, mas no bundle)

    // Testes Instrumentados (Android)
    androidTestImplementation(platform(libs.test.androidx.compose.bom)) // BOM para testes de UI do Compose
    androidTestImplementation(libs.test.androidx.compose.ui.test.junit4) // Testes de UI do Compose
    androidTestImplementation(libs.test.androidx.junit) // AndroidX Test JUnit runner
    androidTestImplementation(libs.test.androidx.espresso.core) // Espresso para testes de UI

    // Debug - Ferramentas de UI do Compose (geralmente gerenciadas pelo compose.bom principal)
    debugImplementation(libs.debug.androidx.compose.ui.tooling)
    debugImplementation(libs.debug.androidx.compose.ui.test.manifest)
}

// Configuração do KSP (se necessário, para passar argumentos)
// ksp {
//    arg("room.schemaLocation", "$projectDir/schemas")
// }

// Configuração do Hilt (se necessário, embora geralmente automático com o plugin)
// hilt {
//    enableAggregatingTask = true
// }