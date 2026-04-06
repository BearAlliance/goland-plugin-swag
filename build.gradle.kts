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
        changeNotes.set(System.getenv("RELEASE_NOTES") ?: "")
    }

    buildSearchableOptions {
        enabled = false
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
