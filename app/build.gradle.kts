plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "alex.android.lab"
    compileSdk = 35

    defaultConfig {
        applicationId = "alex.android.lab"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {
    implementation(project(":module-injector"))

    implementation(project(":core-navigation"))
    implementation(project(":core-navigation-api"))

    implementation(project(":core-db"))
    implementation(project(":core-db-api"))

    implementation(project(":core-network"))
    implementation(project(":core-network-api"))

    implementation(project(":feature-products"))
    implementation(project(":feature-products-api"))
    implementation(project(":feature-pdp"))
    implementation(project(":feature-pdp-api"))
    implementation(project(":feature-shoppingcart"))
    implementation(project(":feature-shoppingcart-api"))


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.viewmodel.runtime.ktx)

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.cicerone)

    // Coroutines
    implementation(libs.coroutines.core)

    // Room
    implementation(libs.room.core)
    implementation(project(":core-model"))
    ksp(libs.room.compiler)

    // Dagger
    implementation(libs.dagger.core)
    ksp(libs.dagger.compiler)

    // Retrofit & OkHttp
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gsonConverter)
    implementation(libs.okhttp.core)

    // Glide
    implementation(libs.glide.core)

    // UI extras
    implementation(libs.swipe)

    // Tests
    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.core)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
