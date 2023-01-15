plugins {
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.kotlin.android") apply false
    id("com.google.dagger.hilt.android") apply false
}

val installGitHook: Task by tasks.creating {
    println("Copying files from the 'rootDir/git-hooks' folder into the '.git/hooks' folder")
    copy { from("$rootDir/git-hooks").into("$rootDir/.git/hooks") }
}

tasks.getByPath(":app:preBuild").dependsOn(installGitHook)