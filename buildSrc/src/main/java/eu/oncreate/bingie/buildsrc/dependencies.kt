package eu.oncreate.bingie.buildsrc

object Versions {
    const val detekt = "1.9.1"
}

object Libs {

    const val androidGradlePlugin = "com.android.tools.build:gradle:4.0.1"
    const val junit = "junit:junit:4.12"
    const val material = "com.google.android.material:material:1.3.0-alpha01"

    const val timber = "com.jakewharton.timber:timber:4.7.1"

    const val numberSlidingPicker = "com.github.sephiroth74:NumberSlidingPicker:1.0.3"
    const val ticker = "com.robinhood.ticker:ticker:2.0.2"

    const val store = "com.dropbox.mobile.store:store4:4.0.0-alpha07"

    const val jdkDeps = "com.github.pengrad:jdk9-deps:1.0"

    object Kotlin {
        private const val version = "1.4.0"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Coroutines {
        private const val version = "1.3.9"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val rx2 = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.1.0"
        const val coreKtx = "androidx.core:core-ktx:1.3.0"
        const val constraint = "androidx.constraintlayout:constraintlayout:2.0.0-beta6"

        const val legacy = "androidx.legacy:legacy-support-v4:1.0.0"

        object Lifecycle {
            private const val version = "2.2.0"
            const val java8 = "androidx.lifecycle:lifecycle-common-java8:$version"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val viewmodelSavedState =
                "androidx.lifecycle:lifecycle-viewmodel-savedstate:$version"
        }

        object Navigation {
            private const val version = "2.3.0"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
        }

        object Room {
            private const val version = "2.2.5"
            const val runtime = "androidx.room:room-runtime:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val rxJava2 = "androidx.room:room-rxjava2:$version"
            const val testing = "androidx.room:room-testing:$version"
            const val compiler = "androidx.room:room-compiler:$version"
        }

//        object Compose {
//            const val snapshot = ""
//            const val version = "1.0.0-alpha01"
//
//            const val core = "androidx.compose.ui:ui:$version"
//            const val foundation = "androidx.compose.foundation:foundation:$version"
//            const val layout = "androidx.compose.foundation:foundation-layout:$version"
//            const val material = "androidx.compose.material:material:$version"
//            const val materialIconsExtended =
//                "androidx.compose.material:material-icons-extended:$version"
//            const val runtime = "androidx.compose.runtime:runtime:$version"
//            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:$version"
//            const val tooling = "androidx.ui:ui-tooling:$version"
//            const val test = "androidx.compose.test:test-core:$version"
//            const val uiTest = "androidx.ui:ui-test:$version"
//        }

        object Test {
            const val unit = "androidx.test.ext:junit:1.1.1"
            const val espresso = "androidx.test.espresso:espresso-core:3.2.0"
        }
    }

    object Dagger {
        private const val version = "2.22.1"
        private const val assistVersion = "0.4.0"

        const val dagger = "com.google.dagger:dagger:$version"
        const val compiler = "com.google.dagger:dagger-compiler:$version"
        const val support = "com.google.dagger:dagger-android-support:$version"
        const val processor = "com.google.dagger:dagger-android-processor:$version"

        const val assist =
            "com.squareup.inject:assisted-inject-annotations-dagger2:$assistVersion"
        const val assistProcessor =
            "com.squareup.inject:assisted-inject-processor-dagger2:$assistVersion"
    }

    object Squareup {
        object Moshi {
            private const val version = "1.8.0"
            const val moshi = "com.squareup.moshi:moshi:$version"
            const val kotlin = "com.squareup.moshi:moshi-kotlin:$version"
            const val codegen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
            const val adapters = "com.squareup.moshi:moshi-adapters:$version"
        }

        object Retrofit {
            private const val version = "2.8.1"
            private const val loggingVersions = "4.2.1"
            const val retrofit = "com.squareup.retrofit2:retrofit:$version"
            const val moshiConverter =
                "com.squareup.retrofit2:converter-moshi:$version"
            const val rx2Adapter =
                "com.squareup.retrofit2:adapter-rxjava2:$version"
            const val logging =
                "com.squareup.okhttp3:logging-interceptor:$loggingVersions"
        }

    }

    object RxJava {
        const val android = "io.reactivex.rxjava2:rxandroid:2.1.1"
        const val java = "io.reactivex.rxjava2:rxjava:2.2.19"
        const val kotlin = "io.reactivex.rxjava2:rxkotlin:2.4.0"
    }

    object Airbnb {
        private const val epoxyVersion = "3.10.0"
        const val mvrx = "com.airbnb.android:mvrx:1.3.0"

        const val epoxy = "com.airbnb.android:epoxy:$epoxyVersion"
        const val epoxyProcessor = "com.airbnb.android:epoxy-processor:$epoxyVersion"
    }

    object Glide {
        private const val version = "4.11.0"
        const val glide = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
    }

}
