plugins { 
    id("com.github.johnrengelman.shadow") version "7.1.2"
    `java-library`
}

repositories { mavenCentral() }

java { toolchain { languageVersion.set(JavaLanguageVersion.of(11)) } }

dependencies {
    implementation(platform("software.amazon.awssdk:bom:2.15.0"))

    implementation("software.amazon.awssdk:s3") {
        exclude(group = "software.amazon.awssdk", module = "netty-nio-client")
        exclude(group = "software.amazon.awssdk", module = "apache-client")
    }
    implementation("software.amazon.awssdk:aws-crt-client:2.15.0-PREVIEW")
}

tasks.jar {
    manifest {
        attributes("Premain-Class" to "com.airties.demo.JFRDumper")
    }
}

tasks.register<Zip>("buildZip") {
    into("jfr-layer") {
        from(tasks.shadowJar)
    }
}
tasks.assemble{ dependsOn("buildZip") }