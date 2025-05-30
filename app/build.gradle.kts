plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.inovadigitalapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.inovadigitalapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // Dependências para acessar API
    // Para JSON
    implementation("com.google.code.gson:gson:2.10.1")  // Dependência correta do Gson
    implementation("com.squareup.okhttp3:okhttp:4.10.0") // Ou a versão mais recente
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("com.jakewharton.threetenabp:threetenabp:1.3.1")

}