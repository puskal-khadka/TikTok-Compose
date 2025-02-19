plugins {
    id("plugin.android-common")
}

android {
    namespace = "com.puskal.creatorprofile"
}

dependencies {
    COMMON_THEME
    COMMON_COMPOSABLE
    DOMAIN
    DATA
    CORE
}