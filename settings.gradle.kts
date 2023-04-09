pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "tiktokcompose"
include(":app")
include(":common:theme")
include(":domain")
include(":data")
include(":core")
include(":common:composable")
include(":feature:home")
include(":feature:commentlisting")
include(":feature:creatorprofile")
include(":feature:inbox")
include(":feature:authentication")
include(":feature:loginwithemailphone")
include(":feature:friends")
include(":feature:myprofile")
include(":feature:setting")
include(":feature:cameramedia")
