import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.serialization") version "1.4.10"
    id("io.qameta.allure") version "2.8.1"
    id("org.jetbrains.dokka") version "0.10.1"
    id("com.adarshr.test-logger") version "2.1.1"
    id("io.gitlab.arturbosch.detekt") version "1.14.2"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
    id("com.github.ben-manes.versions") version "0.36.0"
    id("se.patrikerdes.use-latest-versions") version "0.2.15"
}

allprojects {
    apply(plugin = "io.qameta.allure")
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "com.adarshr.test-logger")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "com.github.ben-manes.versions")
    apply(plugin = "se.patrikerdes.use-latest-versions")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    group = "ee.found"
    version = "1.0-SNAPSHOT"

    val fuelVersion = "2.3.0"
    val kotestVersion = "4.3.1"
    val allureVersion = "2.13.6"
    val aspectJVersion = "1.9.6"
    val exposedVersion = "0.28.1"
    val selenideVercion = "5.20.4"

    //region Settings
    val compileKotlin: KotlinCompile by tasks
    compileKotlin.kotlinOptions {
        jvmTarget = "11"
    }
    val compileTestKotlin: KotlinCompile by tasks
    compileTestKotlin.kotlinOptions {
        jvmTarget = "11"
    }

    //region Dokka
    // https://github.com/Kotlin/dokka
    // https://kotlinlang.org/docs/reference/kotlin-doc.html
    tasks.withType<DokkaTask> {
        outputFormat = "html"
        outputDirectory = "$buildDir/dokka"
        subProjects = subprojects.map { it.name }
        configuration {
            reportUndocumented = true
        }
    }
    //endregion

    //region Detekt
    detekt {
        input = files("src/main/kotlin", "src/test/kotlin")
    }
    //endregion

    //region Ktlint
    ktlint {
        verbose.set(true)
        outputToConsole.set(true)
        outputColorName.set("RED")
        reporters {
            reporter(ReporterType.PLAIN)
        }
    }
    //endregion

    //region Allure
    allure {
        version = allureVersion
        aspectjweaver = true
        aspectjVersion = aspectJVersion
        useJUnit5 {
            version = allureVersion
        }
    }
    //endregion

    //region Tests
    tasks.withType<Test> {
        testlogger {
            setTheme("mocha-parallel")
        }
        useJUnitPlatform()
        systemProperty("junit.jupiter.testinstance.lifecycle.default", "per_class")
        systemProperty("junit.jupiter.execution.parallel.enabled", "true")
        systemProperty("junit.jupiter.execution.parallel.mode.default", "same_thread")
        systemProperty("junit.jupiter.extensions.autodetection.enabled", "true")
        systemProperty("junit.jupiter.execution.parallel.config.strategy", "dynamic")
        systemProperty("junit.jupiter.execution.parallel.mode.classes.default", "same_thread")
    }
    //endregion
    //endregion

    repositories {
        jcenter()
        mavenCentral()
        maven(url = "https://kotlin.bintray.com/kotlinx/") // soon will be just jcenter()
    }

    dependencies {
        implementation(kotlin("stdlib"))

        implementation(group = "io.qameta.allure", name = "allure-junit5", version = "2.13.5")

        detektPlugins(group = "io.gitlab.arturbosch.detekt", name = "detekt-formatting", version = "1.14.2")

        implementation(group = "org.litote.kmongo", name = "kmongo", version = "4.2.0")

        implementation(group = "com.google.code.gson", name = "gson", version = "2.8.6")

        implementation(group = "com.charleskorn.kaml", name = "kaml", version = "0.26.0")

        implementation(group = "org.postgresql", name = "postgresql", version = "42.2.18")

        implementation(group = "com.ecwid.consul", name = "consul-api", version = "1.4.5")

        implementation(group = "org.awaitility", name = "awaitility-kotlin", version = "4.0.3")

        implementation(group = "io.github.microutils", name = "kotlin-logging", version = "2.0.3")

        implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-datetime", version = "0.1.0")

        implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-serialization-runtime", version = "+")

        implementation(group = "com.github.mifmif", name = "generex", version = "1.0.2")

        implementation(group = "com.fasterxml.jackson.module", name = "jackson-module-kotlin", version = "2.11.+")

        implementation(group = "com.github.vertical-blank", name = "sql-formatter", version = "1.0.3")

        // Kotest
        implementation(group = "io.kotest", name = "kotest-assertions-core", version = kotestVersion)

        // AspectJ
        implementation(group = "org.aspectj", name = "aspectjrt", version = aspectJVersion)
        implementation(group = "org.aspectj", name = "aspectjweaver", version = aspectJVersion)

        // JUnit
        implementation(platform("org.junit:junit-bom:5.7.0"))
        implementation(group = "org.junit.jupiter", name = "junit-jupiter")

        // Fuel
        implementation(group = "com.github.kittinunf.fuel", name = "fuel", version = fuelVersion)
        implementation(group = "com.github.kittinunf.fuel", name = "fuel-jackson", version = fuelVersion)
        implementation(group = "com.github.kittinunf.fuel", name = "fuel-kotlinx-serialization", version = fuelVersion)

        // Exposed
        implementation(group = "org.jetbrains.exposed", name = "exposed-dao", version = exposedVersion)
        implementation(group = "org.jetbrains.exposed", name = "exposed-core", version = exposedVersion)
        implementation(group = "org.jetbrains.exposed", name = "exposed-jdbc", version = exposedVersion)
        implementation(group = "org.jetbrains.exposed", name = "exposed-jodatime", version = exposedVersion)

        // Selenide
        implementation(group = "com.codeborne", name = "selenide", version = selenideVercion)
    }
}

tasks.test {
    onlyIf { false }
}

tasks.register("uitest", org.gradle.api.tasks.testing.Test::class) {
    description =
        "Runs browser tests. Pass -Dconfiguration=local to select target test stand. Possible values: local"
    include("ee/found/uitests/*Test.class")
    bypassSystemPropertiesToTest()
    outputs.upToDateWhen { false }
}

fun Test.bypassSystemPropertiesToTest() {
    systemProperties(System.getProperties().mapKeys { it.key as String })
}
