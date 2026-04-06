buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.commonmark:commonmark:0.24.0")
    }
}

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij") version "1.17.4"
}

group = "com.github.bearalliance"
version = System.getenv("RELEASE_VERSION") ?: "1.0.0"

repositories {
    mavenCentral()
}

intellij {
    version.set("2024.1.7")
    type.set("IC")
    updateSinceUntilBuild.set(false)
}

kotlin {
    jvmToolchain(17)
}

tasks {
    patchPluginXml {
        sinceBuild.set("241")
        val markdownNotes = System.getenv("RELEASE_NOTES") ?: ""
        val parser = org.commonmark.parser.Parser.builder().build()
        val renderer = org.commonmark.renderer.html.HtmlRenderer.builder().build()
        changeNotes.set(renderer.render(parser.parse(markdownNotes)))
    }

    buildSearchableOptions {
        enabled = false
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
