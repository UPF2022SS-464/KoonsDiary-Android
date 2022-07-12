pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

/*
val githubProperties = java.util.Properties().apply {
    load(java.io.FileInputStream(File("local.properties")))
}
*/

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://devrepo.kakao.com/nexus/content/groups/public/")
        }
/*
        maven {
            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/UPF2022SS-464/KoonsDiary-Android")
            credentials {
                username = githubProperties["github_username"]?.toString() ?: System.getenv("github_username").toString()
                password = githubProperties["github_access_token"]?.toString() ?: System.getenv("github_access_token").toString()
            }
        }
*/
    }
}

rootProject.name = "Koon's Diary"
include(
    ":app",
    ":domain",
    ":data",
    ":local",
    ":remote",
    ":presentation",
    ":kakao",
    ":firebase",
    ":common"
)
