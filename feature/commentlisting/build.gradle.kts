plugins {
    id("plugin.android-common")
}

android {
    namespace = "com.puskal.commentlisting"
}

dependencies {
    COMMON_THEME
    DOMAIN
    DATA
    CORE
    COMMON_COMPOSABLE
    media3Dependency()
}