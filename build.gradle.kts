// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    // Firebase's plugin. It is only switched on in app/build.gradle.kts when google-services.json exists.
    alias(libs.plugins.google.services) apply false
}
