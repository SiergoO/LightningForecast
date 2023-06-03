package dependencies

object Dependency {

    object Compose {
        private const val VERSION_COMPOSE = "1.4.3"
        private const val VERSION_ACCOMPANIST = "0.30.1"
        const val UI = "androidx.compose.ui:ui:$VERSION_COMPOSE"
        const val TOOLING = "androidx.compose.ui:ui-tooling:$VERSION_COMPOSE"
        const val PREVIEW = "androidx.compose.ui:ui-tooling-preview:$VERSION_COMPOSE"
        const val FOUNDATION = "androidx.compose.foundation:foundation:$VERSION_COMPOSE"
        const val MATERIAL = "androidx.compose.material:material:$VERSION_COMPOSE"
        const val MATERIAL3 = "androidx.compose.material3:material3:1.1.0"
        const val MATERIAL_ICONS = "androidx.compose.material:material-icons-extended:$VERSION_COMPOSE"
        const val ACTIVITY = "androidx.activity:activity-compose:1.7.1"
        const val ANIMATED_NAVIGATION = "com.google.accompanist:accompanist-navigation-animation:$VERSION_ACCOMPANIST"
        const val LOTTIE = "com.airbnb.android:lottie-compose:6.0.0"
    }

    object OrbitMVI {
        private const val VERSION = "6.0.0"
        const val CORE = "org.orbit-mvi:orbit-core:$VERSION"
        const val VIEWMODEL = "org.orbit-mvi:orbit-viewmodel:$VERSION"
        const val COMPOSE = "org.orbit-mvi:orbit-compose:$VERSION"
        const val TEST = "org.orbit-mvi:orbit-test:$VERSION"
    }

    object ViewModel {
        private const val VERSION = "2.6.1"
        const val KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:$VERSION"
        const val COMPOSE = "androidx.lifecycle:lifecycle-viewmodel-compose:$VERSION"
    }

    object Coroutines {
        private const val VERSION = "1.7.1"
        const val CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$VERSION"
        const val ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$VERSION"
    }

    object KoIn {
        private const val VERSION = "3.4.0"
        const val ANDROID = "io.insert-koin:koin-android:$VERSION"
        const val CORE = "io.insert-koin:koin-core:$VERSION"
        const val COMPOSE = "io.insert-koin:koin-androidx-compose:$VERSION"
    }

    object Ktor {
        private const val VERSION = "2.3.0"
        const val CORE = "io.ktor:ktor-client-core:$VERSION"
        const val CIO = "io.ktor:ktor-client-cio:$VERSION"
        const val OKHTTP = "io.ktor:ktor-client-okhttp:$VERSION"
        const val NEGOTIATION = "io.ktor:ktor-client-content-negotiation:$VERSION"
        const val SERIALIZATION = "io.ktor:ktor-serialization-kotlinx-json:$VERSION"
        const val DARWIN = "io.ktor:ktor-client-darwin:$VERSION"
        const val LOGGING = "io.ktor:ktor-client-logging:$VERSION"
        const val AUTH = "io.ktor:ktor-client-auth:$VERSION"
    }

    object Kotlin {
        private const val VERSION = "1.8.21"
        const val REFLECT = "org.jetbrains.kotlin:kotlin-reflect:$VERSION"
    }

    object PlayServices {
        private const val VERSION = "17.0.0"
        const val PLACES = "com.google.android.gms:play-services-places:$VERSION"
        const val LOCATION = "com.google.android.gms:play-services-location:$VERSION"
    }

    object Coil {
        private const val VERSION = "2.4.0"
        const val COMPOSE = "io.coil-kt:coil-compose:$VERSION"
    }

    object Test {
        const val JUNIT = "junit:junit:4.13.2"
        const val JUNIT_KTX = "androidx.test.ext:junit-ktx:1.1.5"
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:3.5.1"
    }

    const val SPLASH = "androidx.core:core-splashscreen:1.0.1"
    const val SERIALIZATION = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1"
    const val DATASTORE = "androidx.datastore:datastore-preferences:1.0.0"
    const val TIMBER = "com.jakewharton.timber:timber:5.0.1"
}