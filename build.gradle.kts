buildscript {
    dependencies {
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.google.ksp) apply false
    alias(libs.plugins.androidx.navigation.safeargs.kotlin) apply false
    alias(libs.plugins.jetbrains.kotlin.parcelize) apply false
    alias(libs.plugins.google.dagger.hilt.android) apply false
    alias(libs.plugins.jetbrains.kotlin.kapt) apply false
}