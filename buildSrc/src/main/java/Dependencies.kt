import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {
    // stdlib
    private const val androidCore = "androidx.core:core-ktx:${Versions.androidxCoreVersion}"
    val stdLibraries = listOf(androidCore)

    // UI
    private const val material = "com.google.android.material:material:${Versions.materialVersion}"
    private const val appCompat = "androidx.appcompat:appcompat:${Versions.appcompatVersion}"
    private const val constrainLayout = "androidx.constraintlayout:constraintlayout:${Versions.constrainLayoutVersion}"
    private const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerViewVersion}"
    private const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragmentVersion}"
    private const val cardView = "androidx.cardview:cardview:${Versions.cardViewVersion}"
    private const val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshVersion}"
    private const val legacy = "androidx.legacy:legacy-support-v4:${Versions.legacySupportVersion}"
    val androidUILibraries = listOf(material, appCompat, constrainLayout, recyclerView, fragment, cardView, swipeRefresh, legacy)

    // glide
    const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    private const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glideCompilerVersion}"

    // timber
    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"

    // koin
    private const val koin = "io.insert-koin:koin-android:${Versions.koinVersion}"
    private const val koinCompat = "io.insert-koin:koin-android-compat:${Versions.koinVersion}"
    private const val koinNav = "io.insert-koin:koin-androidx-navigation:${Versions.koinVersion}"
    val koinLibraries = listOf(koin, koinCompat, koinNav)

    // navigation
    private const val navFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    private const val navUI = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"
    private const val navDF = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navigationVersion}"
    val navLibraries = listOf(navFragment, navUI, navDF)

    // architecture
    private const val lifeCycleExt = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtVersion}"
    private const val lifeCycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleVersion}"
    private const val lifeCycleCommon = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycleVersion}"
    private const val lifeCycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"
    val archLibraries = listOf(lifeCycleExt, lifeCycleRuntime, lifeCycleCommon, lifeCycleLiveData)

    // network
    private const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    private const val retrofitConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofitVersion}"
    private const val retrofitMoshi = "com.squareup.moshi:moshi:${Versions.retrofitMoshiVersion}"
    private const val okHTTPClient = "com.squareup.okhttp3:logging-interceptor:${Versions.retrofitInterceptorVersion}"
    val networkLibraries = listOf(retrofit, retrofitConverter, retrofitMoshi, okHTTPClient)

    // database
    private const val room = "androidx.room:room-ktx:${Versions.roomVersion}"
    private const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
    private const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    private const val roomTesting = "androidx.room:room-testing:${Versions.roomVersion}"
    val dbLibraries = listOf(room, roomRuntime, roomTesting)

    // reactive
    private const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    private const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
    val reactiveLibraries = listOf(coroutinesCore, coroutinesAndroid)

    // test
    private const val junit =  "junit:junit:${Versions.junitVersion}"
    private const val junitExt = "androidx.test.ext:junit:${Versions.junitExtVersion}"
    private const val espresso =  "androidx.test.espresso:espresso-core:${Versions.espressoCoreVersion}"
    val testLibraries = listOf(junit, junitExt)
    val androidTestLibraries = listOf(junit, junitExt, espresso)

    // lottie
    const val lottie = "com.airbnb.android:lottie:${Versions.lottieVersion}"

    // camera X
    private const val cameraX = "androidx.camera:camera-camera2:${Versions.cameraXVersion}"
    private const val cameraXLifeCycle = "androidx.camera:camera-lifecycle:${Versions.cameraXVersion}"
    private const val cameraXView = "androidx.camera:camera-view:${Versions.cameraXVersion}"
    val cameraLibraries = listOf(cameraX, cameraXLifeCycle, cameraXView)

    // google maps
    const val gMaps = "com.google.android.gms:play-services-maps:${Versions.mapsVersion}"

    // paging
    const val paging = "androidx.paging:paging-runtime-ktx:${Versions.pagingVersion}"

    val kaptLibraries = listOf(roomCompiler, glideCompiler)
}

fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("annotationProcessor", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}