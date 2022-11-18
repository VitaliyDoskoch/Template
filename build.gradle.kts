buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Plugins.android)
        classpath(Plugins.kotlin)
    }
}

tasks {
    register("clean", Delete::class) { delete(rootProject.buildDir) }
}

val installGitHook: Task by tasks.creating {
    println("Copying files from the 'rootDir/git-hooks' folder into the '.git/hooks' folder")
    copy { from("$rootDir/git-hooks").into("$rootDir/.git/hooks") }
}

tasks.getByPath(":app:preBuild").dependsOn(installGitHook)