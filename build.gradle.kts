plugins {
    java
    jacoco
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    // needed for logging
    implementation("org.apache.logging.log4j", "log4j-slf4j-impl", "2.13.3")

    // needed to serialize-deserialize objects such as UserPreferences
    implementation("com.google.code.gson", "gson", "2.8.6")

    testImplementation("junit", "junit", "4.12")
    // Helps testing equals and hashcode.
    testImplementation("nl.jqno.equalsverifier", "equalsverifier", "3.4.1")
    // Helps testing toString
    testImplementation ("com.jparams", "to-string-verifier" ,"1.4.8")

}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}


tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        html.isEnabled = false
    }
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
            // Last one does not need to include a line feed.
            versionFile.appendText("timestamp=${timestamp}")
        }
        catch (ex: java.io.IOException)
        {
            // FIXME Log me
            ex.printStackTrace()
        }
    }
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

