package com.franjo.github

object Versions {
  const val compileSdk = 30
  const val targetSdk = 30
  const val minSdk = 23
  const val buildTools = "30.0.2"
  const val versionCode = 1
  const val versionName = "1.0"
  const val ktlint = "0.40.0"
}

object Libs {
  const val androidGradlePlugin = "com.android.tools.build:gradle:4.2.1"
  const val ktlintGradlePlugin = "org.jlleitschuh.gradle:ktlint-gradle:9.4.1"
  const val detektGradlePlugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.16.0"

  object Kotlin {
    private const val version = "1.5.20"
    const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
    const val androidExtensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    const val test = "org.jetbrains.kotlin:kotlin-test-junit:$version"
  }

  object AndroidX {
    const val coreKtx = "androidx.core:core-ktx:1.2.0"
    const val appCompat = "androidx.appcompat:appcompat:1.1.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
    const val viewModelSaveState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:2.3.0"
    const val recyclerView = "androidx.recyclerview:recyclerview:1.1.0"
    const val security = "androidx.security:security-crypto:1.1.0-alpha03"
    const val preferences = "androidx.preference:preference-ktx:1.1.1"
    const val paginate = "androidx.paging:paging-runtime-ktx:3.0.0-rc01"
    const val viewPager2 = "androidx.viewpager2:viewpager2:1.0.0"

    object Test {
      const val extJunit = "androidx.test.ext:junit:1.1.1"
      const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
      const val archCore = "androidx.arch.core:core-testing:2.1.0"
    }

  }

  object Navigation {
    private const val version = "1.0.0"
    const val navFragment = "android.arch.navigation:navigation-fragment-ktx:$version"
    const val navUi = "android.arch.navigation:navigation-ui-ktx:$version"
    const val navSafeArgs = "android.arch.navigation:navigation-safe-args-gradle-plugin:$version"
  }

  object Coroutines {
    private const val version = "1.3.9"
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
  }

  object Firebase {
    private const val version = "2.6.0"
    const val crashlytics = "com.google.firebase:firebase-crashlytics-gradle:$version"
  }

  object Glide {
    private const val version = "4.11.0"
    const val glide = "com.github.bumptech.glide:glide:$version"
    const val compiler = "com.github.bumptech.glide:compiler:$version"
  }

  object Test {
    const val junit = "junit:junit:4.12"
    const val mockK = "io.mockk:mockk:1.10.6"
  }

  object Dagger2 {
    private const val version = "2.34"
    const val dagger = "com.google.dagger:dagger:$version"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:$version"
    const val daggerAndroid = "com.google.dagger:dagger-android-support:$version"
    const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:$version"
  }

  object Retrofit {
    private const val version = "2.7.1"
    const val retrofit = "com.squareup.retrofit2:retrofit:$version"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:$version"
    const val moshiKotlinAdapter = "com.squareup.moshi:moshi-kotlin:1.12.0"
    const val coroutinesAdapter =
      "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
  }

  object OkHttp {
    private const val version = "4.9.0"
    const val okhttp = "com.squareup.okhttp3:okhttp:$version"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
  }

  object Image {
    const val circleImage = "de.hdodenhof:circleimageview:3.1.0"
  }

  object Material {
    const val material = "com.google.android.material:material:1.3.0"
  }

  object Google {
    const val googleServices = "com.google.gms:google-services:4.3.5"
  }

  object Utils {
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.6"
    const val timber = "com.jakewharton.timber:timber:4.7.1"
  }

}

