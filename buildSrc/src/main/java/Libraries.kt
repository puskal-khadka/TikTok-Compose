import Version.AndroidXTestVersion
import Version.AppCompat
import Version.CameraXVersion
import Version.ConstraintLayoutCompose
import Version.CoreKtx
import Version.EspressoCore
import Version.GsonVersion
import Version.GuavaAndroid
import Version.HiltAndroidVersion
import Version.HiltNavigationCompose
import Version.JunitExtKtx
import Version.LifecycleRunTimeKtx
import Version.Media3
import Version.NavigationCompose
import Version.SplashScreenApi
import Version.TestRunnerVersion
import Version.TruthExt

/**
 * Created by Puskal Khadka on 3/14/2023.
 */
object Version {
    const val CoreKtx = "1.9.0"
    const val AppCompat = "1.6.1"
    const val ComposeBom = "2023.01.00"
    const val LifecycleRunTimeKtx = "2.3.1"
    const val NavigationCompose = "2.5.3"
    const val AndroidXTestVersion = "1.5.0"
    const val EspressoCore = "3.5.1"
    const val TestRunnerVersion = "1.5.2"
    const val JunitExtKtx = "1.1.5"
    const val TruthExt = "1.5.0"
    const val Coil = "2.2.2"
    const val HiltNavigationCompose = "1.0.0"
    const val HiltAndroidVersion = "2.44"
    const val Media3 = "1.0.0-rc02"
    const val Accompanist = "0.28.0"
    const val SplashScreenApi = "1.1.0-alpha01"
    const val ConstraintLayoutCompose = "1.0.1"
    const val GsonVersion = "2.10.1"
    const val CameraXVersion = "1.3.0-alpha05"
    const val GuavaAndroid="31.0.1-android"
}


object Libraries {
    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:$CoreKtx"
        const val appCompat = "androidx.appcompat:appcompat:$AppCompat"
        const val lifecycleRunTimeKtx =
            "androidx.lifecycle:lifecycle-runtime-ktx:$LifecycleRunTimeKtx"
        const val splashScreen = "androidx.core:core-splashscreen:$SplashScreenApi"
    }

    object Compose {
        const val composeBom = "androidx.compose:compose-bom:${Version.ComposeBom}"
        const val composeUi = "androidx.compose.ui:ui"
        const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
        const val composeMaterial3 = "androidx.compose.material3:material3"
        const val composeFoundation = "androidx.compose.foundation:foundation"
        const val composeRuntime = "androidx.compose.runtime:runtime"
        const val composeActivity = "androidx.activity:activity-compose:1.6.1"
        const val composeUiUtil = "androidx.compose.ui:ui-util"
        const val constraintLayoutCompose =
            "androidx.constraintlayout:constraintlayout-compose:$ConstraintLayoutCompose"
    }

    object Google {
        const val gson = "com.google.code.gson:gson:$GsonVersion"
        const val guava="com.google.guava:guava:$GuavaAndroid"
    }

    object Accompanist {
        const val pager =
            "com.google.accompanist:accompanist-pager:0.29.2-rc"
        const val swiperefresh =
            "com.google.accompanist:accompanist-swiperefresh:${Version.Accompanist}"
        const val indicators =
            "com.google.accompanist:accompanist-pager-indicators:${Version.Accompanist}"
        const val systemuicontroller =
            "com.google.accompanist:accompanist-systemuicontroller:${Version.Accompanist}"
        const val webView = "com.google.accompanist:accompanist-webview:${Version.Accompanist}"
        const val navigationMaterial =
            "com.google.accompanist:accompanist-navigation-material:${Version.Accompanist}"
        const val navigationAnimation =
            "com.google.accompanist:accompanist-navigation-animation:${Version.Accompanist}"
        const val permission =
            "com.google.accompanist:accompanist-permissions:${Version.Accompanist}"
    }

    object Naviagtion {
        const val navigationCompose = "androidx.navigation:navigation-compose:$NavigationCompose"
    }

    object Coil {
        const val coilCompose = "io.coil-kt:coil-compose:${Version.Coil}"
        const val coilVideo = "io.coil-kt:coil-video:${Version.Coil}"
    }

    object AudioVideo {
        const val exoplayer = "androidx.media3:media3-exoplayer:$Media3"
        const val expplayerDash = "androidx.media3:media3-exoplayer-dash:$Media3"
        const val media3Ui = "androidx.media3:media3-ui:$Media3"
    }

    object Test {
        const val testCoreKtx = "androidx.test:core-ktx:$AndroidXTestVersion"
        const val espressorCore = "androidx.test.espresso:espresso-core:$EspressoCore"
        const val junitExtKtx = "androidx.test.ext:junit-ktx:$JunitExtKtx"
        const val truthExt = "androidx.test.ext:truth:$TruthExt"
        const val runner = "androidx.test:runner:$TestRunnerVersion"
    }


    object Hilt {
        const val hiltAndroid = "com.google.dagger:hilt-android:$HiltAndroidVersion"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:$HiltAndroidVersion"

        //hilt compose
        const val hiltNavigationCompse =
            "androidx.hilt:hilt-navigation-compose:$HiltNavigationCompose"
    }

    object CameraX {
        const val cameraCore = "androidx.camera:camera-core:$CameraXVersion"
        const val camera2 = "androidx.camera:camera-camera2:$CameraXVersion"
        const val cameraLifecycle = "androidx.camera:camera-lifecycle:$CameraXVersion"
        const val cameraVideo = "androidx.camera:camera-video:$CameraXVersion"
        const val cameraView = "androidx.camera:camera-view:$CameraXVersion"
        const val CameraExt = "androidx.camera:camera-extensions:$CameraXVersion"
    }
}


