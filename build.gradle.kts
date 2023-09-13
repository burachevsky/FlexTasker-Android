buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://www.jitpack.io")
    }

    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle)
        classpath(libs.navigation.safe.args.gradle)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://www.jitpack.io")
    }
}