// build.gradle.kts (project level)

plugins {
    // Android Gradle Plugins
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false

    // Kotlin Plugins
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinJvm) apply false             // NOVO: Adicionado para módulos JVM puros
    alias(libs.plugins.kotlinComposeCompiler) apply false

    // Ferramentas de Processamento de Anotações
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false

    // Plugins de Serviços
    alias(libs.plugins.googleServices) apply false         // Já estava no toml, agora com alias correto
    // Se você ainda usa Kapt (em vez de KSP para Hilt/Room), inclua-o também:
    // alias(libs.plugins.kotlinKapt) apply false
    // Se você usa Parcelize, inclua-o:
    // alias(libs.plugins.kotlinParcelize) apply false
}