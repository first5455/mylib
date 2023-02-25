plugins {
    kotlin("jvm") version "1.8.0"
    id("java-library")
    id("maven-publish")
}

group = "org.example"
version = "1.2-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("http://localhost:8081")
        isAllowInsecureProtocol = true
        credentials {
            username = "admin"
            password = "myPassword123"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("myLib") {
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("My Library")
                description.set("A concise description of my library")
                url.set("http://www.example.com/library")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("johnd")
                        name.set("John Doe")
                        email.set("john.doe@example.com")
                    }
                }
            }
            repositories {
                maven {
                    isAllowInsecureProtocol = true
                    url = uri("http://localhost:8081/repository/maven-snapshots/")
                    credentials {
                        username = "admin"
                        password = "myPassword123"
                    }
                }
            }
        }
    }
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}