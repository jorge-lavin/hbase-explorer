plugins {
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.logging.log4j", "log4j-slf4j-impl", "2.13.3")
    testImplementation("junit", "junit", "4.12")

}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

// https://discuss.gradle.org/t/how-to-run-execute-string-as-a-shell-command-in-kotlin-dsl/32235/7
fun String.runCommand(workingDir: File = file("./")): String {
    val parts = this.split("\\s".toRegex())
    val proc = ProcessBuilder(*parts.toTypedArray())
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()

    proc.waitFor(1, TimeUnit.MINUTES)
    return proc.inputStream.bufferedReader().readText().trim()
}

fun File.appendLine(text: String) {
    this.appendText(text + System.lineSeparator())
}

tasks.register("writeVersionFile") {
    val path = "src/main/resources/version.properties";
    doLast {
        delete(path)
        val versionFile = file(path)
        versionFile.appendLine("version=${version}")

        try
        {
            // 39974bfe5b997005d2a8d2b65059043c7a0f040c|lavinj87|1597662779|HEAD -> dev, origin/dev|Added README.md
            val gitStatus = "git log -1 --pretty=format:\"%H|%an|%at|%D|%s\"".runCommand()
            val gitStatusParts = gitStatus.split(Regex("\\|"), 5)

            val hash = gitStatusParts[0]
            val author = gitStatusParts[1]
            val timestamp = gitStatusParts[2].toLong()
            // HEAD -> dev, origin/dev
            val branches = gitStatusParts[3]
            val branch = branches.split(",")[1].split("/")[1]
            val message = gitStatusParts[4]


            versionFile.appendLine("author=${author}")
            versionFile.appendLine("branch=${branch}")
            versionFile.appendLine("hash=${hash}")
            versionFile.appendLine("message=${message}")
            versionFile.appendLine("timestamp=${timestamp}")
        }
        catch (ex: java.io.IOException)
        {
            // FIXME Log me
            ex.printStackTrace()
        }
    }
}