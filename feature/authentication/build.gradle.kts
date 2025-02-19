plugins {
    id("plugin.android-common")
}

android {
    namespace = "com.puskal.authentication"
}

dependencies {
    COMMON_THEME
    DOMAIN
    DATA
    CORE
    COMMON_COMPOSABLE
}