import org.gradle.api.JavaVersion

object Config {
    const val minSdk = 21
    const val compileSdk = 31
    const val targetSdk = 30
    val javaVersion = JavaVersion.VERSION_1_8
    const val buildTools = "29.0.2"
}

internal object DependencyVersions {
    const val androidx_arch_testing_core = "2.1.0"
    const val androidx_core = "1.8.0-alpha02"
    const val androidx_recyclerview = "1.2.1"
    const val androidx_constraintLayout = "2.1.2"
    const val material = "1.6.0-alpha01"
    const val ktx_lifecycle = "2.4.0"
    const val androidx_appCompat  = "1.4.1"

    const val pin_view = "1.4.3"

    const val view_pager_two = "1.1.0-beta01"

    const val ktx_core: String = "1.7.0"
    const val ktx_preference: String = "1.1.1"
    const val kotlin_version = "1.5.31"

    const val kotlin_coroutines_core = "1.3.8"
    const val kotlin_coroutines = "1.4.1"
    const val kotlinx_coroutines_test = "1.6.0"

    const val koin_version = "3.1.5"

    const val dateLib = "1.2.3"

    const val coil = "0.9.2"
    const val retrofit = "2.7.1"

    const val okhttp = "4.3.1"
    const val gson = "2.8.6"
    const val opentok = "2.16.3"

    const val support_compat = "28.1.1"
    const val daimajia_easing = "2.0@aar"
    const val daimajia_animations = "2.3@aar"

    const val androidx_testing = "1.1.1"
    const val androidx_core_testing = "2.1.0"
    const val androidx_espresso = "3.5.0-alpha03"
    const val androidx_junit = "1.1.4-alpha03"
    const val junit = "4.13"
    const val hamcrest = "1.3"
    const val robolectric = "4.3.1"
    const val mockito_kotlin = "2.2.0"
    const val mockito_android = "2.6.3"
    const val mockk = "1.9"

    const val leakCanaryVersion = "2.1"
    const val stetho = "1.5.1"
    const val timber = "4.7.1"

    const val gradleandroid = "4.1.3"
    const val kotlin = "1.3.72"
    const val gradleversions = "0.28.0"

    const val google_services = "4.3.3"
    const val firebase_analytics = "17.2.2"
    const val firebase_crashlytics_gradle = "2.0.0-beta01"
    const val firebase_crashlytics = "17.0.0-beta01"
    const val firebase_messaging = "20.1.3"

    const val androidx_card_view = "1.0.0"
    const val glide = "4.11.0"
    const val glide_transformations = "4.1.0"

    const val room = "2.4.1"

    const val biometric = "1.0.1"
}


object Dependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${DependencyVersions.kotlin_version}"
    const val androidx_core = "androidx.core:core-ktx:${DependencyVersions.androidx_core}"
    const val androidx_appcompat =
        "androidx.appcompat:appcompat:${DependencyVersions.androidx_appCompat}"
    const val androidx_constraintlayout =
        "androidx.constraintlayout:constraintlayout:${DependencyVersions.androidx_constraintLayout}"
    const val androidx_material =
        "com.google.android.material:material:${DependencyVersions.material}"
    const val androidx_recyclerview =
        "androidx.recyclerview:recyclerview:${DependencyVersions.androidx_recyclerview}"

    const val android_pin_view = "com.chaos.view:pinview:${DependencyVersions.pin_view}"

    const val androidx_view_pager_two = "androidx.viewpager2:viewpager2:${DependencyVersions.view_pager_two}"

    const val android_core_ktx = "androidx.core:core-ktx:${DependencyVersions.ktx_core}"
    const val android_preferences_ktx =
        "androidx.preference:preference-ktx:${DependencyVersions.ktx_preference}"
    const val android_viewmodel_ktx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${DependencyVersions.ktx_lifecycle}"
    const val coroutines_core =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${DependencyVersions.kotlin_coroutines_core}"
    const val coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${DependencyVersions.kotlin_coroutines}"
    const val lifecycle_viewmodel_ktx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${DependencyVersions.ktx_lifecycle}"
    const val lifecycle_runtime_ktx =
        "androidx.lifecycle:lifecycle-runtime-ktx:${DependencyVersions.ktx_lifecycle}"
    const val lifecycle_extensions_ktx =
        "android.arch.lifecycle:extensions:${DependencyVersions.ktx_lifecycle}"

    const val kotlinx_coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${DependencyVersions.kotlinx_coroutines_test}"
    const val testandroidx_core = "androidx.arch.core:core-testing:${DependencyVersions.androidx_arch_testing_core}"
    const val androidx_core_testing =
        "androidx.arch.core:core-testing:${DependencyVersions.androidx_core_testing}"
    const val testandroidx_rules = "androidx.test:rules:${DependencyVersions.androidx_testing}"
    const val testandroidx_runner = "androidx.test:runner:${DependencyVersions.androidx_testing}"
    const val testandroidx_espressocore =
        "androidx.test.espresso:espresso-core:${DependencyVersions.androidx_espresso}"
    const val testandroidx_espresso_contib =
        "androidx.test.espresso:espresso-contrib:${DependencyVersions.androidx_espresso}"
    const val testandroidx_espresso_intents =
        "androidx.test.espresso:espresso-intents:${DependencyVersions.androidx_espresso}"
    const val androidx_junit = "androidx.test.ext:junit:${DependencyVersions.androidx_junit}"
    const val junit = "junit:junit:${DependencyVersions.junit}"
    const val hamcrest = "org.hamcrest:hamcrest-all:${DependencyVersions.hamcrest}"
    const val robolectric = "org.robolectric:robolectric:${DependencyVersions.robolectric}"
    const val mockito_kotlin =
        "com.nhaarman.mockitokotlin2:mockito-kotlin:${DependencyVersions.mockito_kotlin}"
    const val mockitoAndroid = "org.mockito:mockito-android:${DependencyVersions.mockito_android}"
    const val mockk = "io.mockk:mockk:${DependencyVersions.mockk}"

    const val koin_core = "io.insert-koin:koin-core:${DependencyVersions.koin_version}"
    const val koin_android = "io.insert-koin:koin-android:${DependencyVersions.koin_version}"
    const val koin_compat = "io.insert-koin:koin-android-compat:${DependencyVersions.koin_version}"
    const val koin_test = "org.koin:koin-test:${DependencyVersions.koin_version}"

    const val coil_imageloading = "io.coil-kt:coil:${DependencyVersions.coil}"
    const val glide_imageloading = "com.github.bumptech.glide:glide:${DependencyVersions.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${DependencyVersions.glide}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${DependencyVersions.retrofit}"
    const val retrofit_gson_converter =
        "com.squareup.retrofit2:converter-gson:${DependencyVersions.retrofit}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${DependencyVersions.okhttp}"
    const val okhttp_logging_interceptor =
        "com.squareup.okhttp3:logging-interceptor:${DependencyVersions.okhttp}"
    const val gson = "com.google.code.gson:gson:${DependencyVersions.gson}"

    const val support_compat =
        "com.android.support:support-compat:${DependencyVersions.support_compat}"
    const val daimajia_easing = "com.daimajia.easing:library:${DependencyVersions.daimajia_easing}"
    const val daimajia_animations =
        "com.daimajia.androidanimations:library:${DependencyVersions.daimajia_animations}"

    const val dateLib = "com.jakewharton.threetenabp:threetenabp:${DependencyVersions.dateLib}"

    const val tools_gradleandroid =
        "com.android.tools.build:gradle:${DependencyVersions.gradleandroid}"
    const val tools_kotlin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${DependencyVersions.kotlin}"
    const val tools_gradleversions =
        "com.github.ben-manes:gradle-versions-plugin:${DependencyVersions.gradleversions}"

    const val google_services =
        "com.google.gms:google-services:${DependencyVersions.google_services}"
    const val firebase_analytics =
        "com.google.firebase:firebase-analytics:${DependencyVersions.firebase_analytics}"
    const val firebase_crashlytics_gradle =
        "com.google.firebase:firebase-crashlytics-gradle:${DependencyVersions.firebase_crashlytics_gradle}"
    const val firebase_crashlytics =
        "com.google.firebase:firebase-crashlytics:${DependencyVersions.firebase_crashlytics}"

    const val firebase_messaging =
        "com.google.firebase:firebase-messaging:${DependencyVersions.firebase_messaging}"

    const val leakCanary =
        "com.squareup.leakcanary:leakcanary-android:${DependencyVersions.leakCanaryVersion}"
    val stetho = "com.facebook.stetho:stetho:${DependencyVersions.stetho}"
    val stethoOkhttp = "com.facebook.stetho:stetho-okhttp3:${DependencyVersions.stetho}"
    val timber = "com.jakewharton.timber:timber:${DependencyVersions.timber}"
    val androidx_card_view = "androidx.cardview:cardview:${DependencyVersions.androidx_card_view}"
    val glide = "com.github.bumptech.glide:glide:${DependencyVersions.glide}"
    val glide_transformations =
        "jp.wasabeef:glide-transformations:${DependencyVersions.glide_transformations}"

    val room_runtime = "androidx.room:room-runtime:${DependencyVersions.room}"
    val room_ktx = "androidx.room:room-ktx:${DependencyVersions.room}"
    val room_compiler = "androidx.room:room-compiler:${DependencyVersions.room}"
    val room_persistence_compiler =
        "android.arch.persistence.room:compiler:${DependencyVersions.room}"
    val room_testing = "androidx.room:room-testing:${DependencyVersions.room}"
    val testandroidx_test_core = "androidx.test:core:${DependencyVersions.androidx_testing}"

    val kotlin_extensions =
        "org.jetbrains.kotlin:kotlin-android-extensions:${DependencyVersions.kotlin_version}"

    val biometric = "androidx.biometric:biometric:${DependencyVersions.biometric}"
}