import java.time.LocalDate
import java.time.format.DateTimeFormatter

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose)
}

android {
    namespace = "com.pjs.tvbox"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.pjs.tvbox"
        minSdk = 24
        targetSdk = 36
        versionCode = getDateNumber()
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ndk {
            abiFilters.addAll(listOf("arm64-v8a"))
        }
    }

    signingConfigs {
        create("release") {
            enableV1Signing = false
            enableV2Signing = true
            enableV3Signing = false
            enableV4Signing = false
        }
    }

    lint {
        abortOnError = true
        checkReleaseBuilds = true
        baseline = file("lint-baseline.xml")
        ignoreWarnings = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    applicationVariants.all {
        outputs.all {
            val output = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
            output.outputFileName = "${applicationId}-v${versionName}-${versionCode}.apk"
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.animation.core)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.cn.lunar)

    implementation(libs.com.squareup.okhttp3)
    implementation(libs.com.squareup.okio)

    implementation(libs.org.jetbrains.kotlinx.serialization.json)
    implementation(libs.org.jetbrains.kotlinx.coroutines.android)

    implementation(libs.io.coil3.coil)
    implementation(libs.io.coil3.compose)
    implementation(libs.io.coil3.network.okhttp)

    implementation(libs.org.commonmark)
    implementation(libs.org.commonmark.ext.autolink)
    implementation(libs.org.commonmark.ext.footnotes)
    implementation(libs.org.commonmark.ext.strikethrough)
    implementation(libs.org.commonmark.ext.tables)
    implementation(libs.org.commonmark.ext.image)
    implementation(libs.org.commonmark.ext.ins)
    implementation(libs.org.commonmark.ext.tasklist)

    implementation(libs.io.github.carguo.gsyvideoplayer.java)
    implementation(libs.io.github.carguo.gsyvideoplayer.exo2)
    implementation(libs.io.github.carguo.gsyvideoplayer.ijk)

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}

fun getDateNumber(): Int {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    return currentDate.format(formatter).toInt()
}