plugins {
    id("plugin.android-common")
}

android {
    namespace = "com.puskal.cameramedia"
}

dependencies {
    COMMON_THEME
    COMMON_COMPOSABLE
    DOMAIN
    DATA
    CORE
    cameraXDependencies()
    testDependencies()
}