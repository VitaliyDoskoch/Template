val ktlint by configurations.creating

dependencies {
    ktlint("com.pinterest:ktlint:0.47.1") {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}

val ktlintCheck by tasks.creating(JavaExec::class) {
    group = "ktlint"
    description = "Check Kotlin code style."
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("src/**/*.kt")
}

val ktlintFormat by tasks.creating(JavaExec::class) {
    group = "ktlint"
    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("-F", "src/**/*.kt")
}

val installGitHook by tasks.creating {
    println("Copying files from the 'rootDir/git-hooks' folder into the '.git/hooks' folder")
    copy { from("$rootDir/git-hooks").into("$rootDir/.git/hooks") }
}

tasks.getByPath(":app:preBuild").dependsOn(installGitHook)