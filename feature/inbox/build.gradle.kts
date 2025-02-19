plugins {
    id("plugin.android-common")
}

android {
    namespace = "com.puskal.inbox"
}

dependencies {
    COMMON_THEME
    COMMON_COMPOSABLE
    DOMAIN
    DATA
    CORE
}