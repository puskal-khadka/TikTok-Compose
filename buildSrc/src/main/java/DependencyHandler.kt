import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * Created by Puskal Khadka on 3/14/2023.
 */
fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

fun DependencyHandler.androidTestImplementation(dependencyNotation: Any): Dependency? =
    add("androidTestImplementation", dependencyNotation)

fun DependencyHandler.kapt(dependencyNotation: Any): Dependency? =
    add("kapt", dependencyNotation)

fun DependencyHandler.composeDependencies() {
    implementation(platform(Libraries.Compose.composeBom))
    implementation(Libraries.Compose.composeActivity)
    implementation(Libraries.Compose.composeUi)
    implementation(Libraries.Compose.composeUiToolingPreview)
    implementation(Libraries.Compose.composeUiUtil)
    implementation(Libraries.Compose.composeFoundation)
    implementation(Libraries.Compose.composeRuntime)
    implementation(Libraries.Compose.composeMaterial3)

    //navgation
    implementation(Libraries.Naviagtion.navigationCompose)

    //coil
    implementation(Libraries.Coil.coilCompose)
    implementation(Libraries.Coil.coilVideo)

    //hilt navigation
    implementation(Libraries.Hilt.hiltNavigationCompse)

    //accompanist
    accompanistDependencies()

    //constraint layout
    implementation(Libraries.Compose.constraintLayoutCompose)

}

fun DependencyHandler.baseDependencies() {
    implementation(Libraries.AndroidX.appCompat)
    implementation(Libraries.AndroidX.coreKtx)
    implementation(Libraries.AndroidX.lifecycleRunTimeKtx)
    implementation(Libraries.AndroidX.splashScreen)
    implementation(Libraries.Google.gson)
    implementation(Libraries.Hilt.hiltAndroid)
    kapt(Libraries.Hilt.hiltCompiler)
    implementation(Libraries.Google.guava)
}

fun DependencyHandler.accompanistDependencies() {
    implementation(Libraries.Accompanist.pager)
    implementation(Libraries.Accompanist.swiperefresh)
    implementation(Libraries.Accompanist.webView)
    implementation(Libraries.Accompanist.indicators)
    implementation(Libraries.Accompanist.systemuicontroller)
    implementation(Libraries.Accompanist.navigationMaterial)
    implementation(Libraries.Accompanist.navigationAnimation)
    implementation(Libraries.Accompanist.permission)
}

fun DependencyHandler.testDependencies() {
    androidTestImplementation(Libraries.Test.testCoreKtx)
    androidTestImplementation(Libraries.Test.espressorCore)
    androidTestImplementation(Libraries.Test.runner)
    androidTestImplementation(Libraries.Test.junitExtKtx)
    androidTestImplementation(Libraries.Test.truthExt)
}

fun DependencyHandler.media3Dependency() {
    implementation(Libraries.AudioVideo.exoplayer)
    implementation(Libraries.AudioVideo.expplayerDash)
    implementation(Libraries.AudioVideo.media3Ui)
}

fun DependencyHandler.cameraXDependencies() {
    implementation(Libraries.CameraX.cameraCore)
    implementation(Libraries.CameraX.camera2)
    implementation(Libraries.CameraX.cameraLifecycle)
    implementation(Libraries.CameraX.cameraVideo)
    implementation(Libraries.CameraX.cameraView)
    implementation(Libraries.CameraX.CameraExt)
}

fun DependencyHandler.moduleDependencies() {
    DOMAIN
    DATA
    CORE
    COMMON_THEME
    COMMON_COMPOSABLE
    FEATURE_HOME
    FEATURE_COMMENT_LISTING
    FEATURE_CREATOR_PROFILE
    FEATURE_INBOX
    FEATURE_AUTHENTICATION
    FEATURE_LOGIN_WITH_EMAIL_PHONE
    FEATURE_FRIENDS
    FEATURE_MY_PROFILE
    FEATURE_SETTING
    FEATURE_CAMERA_MEDIA
}


val DependencyHandler.DOMAIN
    get() = implementation(project(mapOf("path" to ":domain")))

val DependencyHandler.DATA
    get() = implementation(project(mapOf("path" to ":data")))

val DependencyHandler.CORE
    get() = implementation(project(mapOf("path" to ":core")))

val DependencyHandler.COMMON_COMPOSABLE
    get() = implementation(project(mapOf("path" to ":common:composable")))

val DependencyHandler.COMMON_THEME
    get() = implementation(project(mapOf("path" to ":common:theme")))

val DependencyHandler.FEATURE_HOME
    get() = implementation(project(mapOf("path" to ":feature:home")))

val DependencyHandler.FEATURE_COMMENT_LISTING
    get() = implementation(project(mapOf("path" to ":feature:commentlisting")))

val DependencyHandler.FEATURE_CREATOR_PROFILE
    get() = implementation(project(mapOf("path" to ":feature:creatorprofile")))

val DependencyHandler.FEATURE_INBOX
    get() = implementation(project(mapOf("path" to ":feature:inbox")))

val DependencyHandler.FEATURE_AUTHENTICATION
    get() = implementation(project(mapOf("path" to ":feature:authentication")))

val DependencyHandler.FEATURE_LOGIN_WITH_EMAIL_PHONE
    get() = implementation(project(mapOf("path" to ":feature:loginwithemailphone")))

val DependencyHandler.FEATURE_FRIENDS
    get() = implementation(project(mapOf("path" to ":feature:friends")))

val DependencyHandler.FEATURE_MY_PROFILE
    get() = implementation(project(mapOf("path" to ":feature:myprofile")))

val DependencyHandler.FEATURE_SETTING
    get() = implementation(project(mapOf("path" to ":feature:setting")))

val DependencyHandler.FEATURE_CAMERA_MEDIA
    get() = implementation(project(mapOf("path" to ":feature:cameramedia")))
