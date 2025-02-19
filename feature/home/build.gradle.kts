plugins {
    id("plugin.android-common")
}

android {
    namespace = "com.puskal.home"
}

dependencies {
    COMMON_THEME
    COMMON_COMPOSABLE
    DOMAIN
    DATA
    CORE
    media3Dependency()
}