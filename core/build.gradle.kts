plugins {
    id("java-library")
    alias(libs.plugins.kotlinJvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }

    dependencies {

        // Para operações assíncronas (corrotinas) no módulo JVM puro
        implementation(libs.jetbrains.kotlinx.coroutines.core) // <--- Use esta referência

        // Para testes
        testImplementation(libs.test.junit) // Você já tem 'test-junit' no seu TOML
        // testImplementation(libs.test.mockk) // Se você adicionar mockk ao TOML

        // Para testes
        testImplementation(libs.test.junit)
        // Adicione outras libs de teste que você usa, como mockk ou truth
        // testImplementation(libs.test.mockk)
    }
}
