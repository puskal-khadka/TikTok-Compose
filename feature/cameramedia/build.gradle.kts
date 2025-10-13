plugins {
    id("plugin.android-common")
}


dependencies {
    COMMON_THEME
    COMMON_COMPOSABLE
    DOMAIN
    DATA
    CORE
    cameraXDependencies()
    media3Dependency()
    implementation(project(":feature:video-trimmer"))
    // Camera capture and mp4compose external libraries
    implementation("com.otaliastudios:cameraview:2.7.2")
    implementation("com.github.MasayukiSuda:Mp4Composer-android:0.4.1")
}