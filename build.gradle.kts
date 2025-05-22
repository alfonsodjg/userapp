// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    //Plugin hilt
    alias (libs.plugins.hilt) apply false
    //Plugin room
    alias(libs.plugins.ksp) apply false
}