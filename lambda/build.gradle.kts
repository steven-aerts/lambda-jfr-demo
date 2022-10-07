plugins { `java-library` }

repositories { mavenCentral() }

java { toolchain { languageVersion.set(JavaLanguageVersion.of(11)) } }

dependencies {
    implementation(platform("software.amazon.awssdk:bom:2.15.0"))

    implementation("com.amazonaws:aws-lambda-java-core:1.2.1")
    implementation("com.amazonaws:aws-lambda-java-events:3.11.0")
    implementation("software.amazon.awssdk:s3") {
        exclude(group = "software.amazon.awssdk", module = "netty-nio-client")
        exclude(group = "software.amazon.awssdk", module = "apache-client")
    }
    implementation("software.amazon.awssdk:aws-crt-client:2.15.0-PREVIEW")
}

tasks.register<Zip>("buildZip") {
    from(tasks.compileJava)
    from(tasks.processResources)
    into("lib") {
        from(configurations.runtimeClasspath)
    }
}
tasks.assemble{ dependsOn("buildZip") }