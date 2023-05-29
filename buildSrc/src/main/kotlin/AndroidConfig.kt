object AndroidConfig {

    object Project {
        const val NAMESPACE = "com.sdamashchuk"
    }

    object App {
        private const val VERSION_MAJOR = 0
        private const val VERSION_MINOR = 0
        private const val VERSION_PATCH = 1

        const val APPLICATION_ID = "com.sdamashchuk.lightningforecast"
        const val VERSION_CODE = (VERSION_MAJOR * 1_000_000) + (VERSION_MINOR * 1_000) + VERSION_PATCH
        const val VERSION_NAME = "${VERSION_MAJOR}.${VERSION_MINOR}.${VERSION_PATCH}"
    }

    object SDK {
        const val COMPILE_VERSION = 33
        const val MIN_VERSION = 24
        const val TARGET_VERSION = 33
    }

    object ApplicationOutput {
        const val APPLICATION_NAME = "Lightning-Forecast"
        const val VERSION_CODE = App.VERSION_CODE
        const val VERSION_NAME = App.VERSION_NAME
    }

    object Kotlin {
        const val VERSION = "1.8.21"
    }

    object Compose {
        const val COMPILER_VERSION = "1.4.7"
    }

    object Test {
        const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
    }
}