plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    //Plugins for hilt
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
    //Plugins for room
    alias(libs.plugins.room.ksp)
}

android {
    namespace = "com.alfonso.usersapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.alfonso.usersapp"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding {
        enable = true
    }
    tasks.withType<Test> {
        jvmArgs("-Dnet.bytebuddy.experimental=true")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    testImplementation(libs.androidx.mockito)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //viewmodel and livedata
    implementation(libs.androidx.viewmodel)
    implementation(libs.androidx.livedata)
    //Retrofit
    implementation(libs.androidx.retrofit)
    implementation(libs.androidx.retrofit.gson)
    //coroutines
    implementation(libs.kotlinx.coroutines.android)
    // Hilt
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)
    //Room
    implementation(libs.androidx.room)
    ksp(libs.androidx.room.ksp)
    testImplementation(kotlin("test"))
}