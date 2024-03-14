// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    repositories {
        google()
        maven {  url = uri("https://jitpack.io") }
    }
    dependencies {
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.42")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
    }

}

plugins {
    id("com.android.application") version "8.1.4" apply false
    id ("com.android.library") version "7.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}


