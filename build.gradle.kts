import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    id("com.github.johnrengelman.shadow") version "8.1.1"
    `maven-publish`
    signing
    application
}

group = "io.github.kingsword09"
version = "0.1.0"

application {
    mainClass.set("io.github.kingsword09.svg2compose.CliKt")
}

dependencies {
    implementation(libs.android.tools.sdk)
    implementation(libs.com.squareup)
    implementation(libs.org.ogce)

    testImplementation(kotlin("test-junit"))
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    compilerOptions.jvmTarget.set(JvmTarget.JVM_21)
}

java {
    withSourcesJar()
    withJavadocJar()
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of("21"))
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])

            artifact(tasks.kotlinSourcesJar)
            artifact(tasks.named("javadocJar"))

            pom {
                name.set("svg-to-compose")
                description.set("Converts SVG or Android Vector Drawable to Compose code.")
                url.set("https://github.com/kingsword09/svg-to-compose")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/kingsword09/svg-to-compose/blob/main/LICENSE")
                    }
                }

                developers {
                    developer {
                        id.set("kingsword09")
                        name.set("kingsword09")
                        email.set("kingsword09@gmail.com")
                        url.set("https://github.com/kingsword09")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/kingsword09/svg-to-compose.git")
                    developerConnection.set("scm:git:ssh://github.com/kingsword09/svg-to-compose.git")
                    url.set("https://github.com/kingsword09/svg-to-compose")
                }

                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/kingsword09/svg-to-compose/issues")
                }
            }
        }
    }

    repositories {
        maven {
            name = "deployments"

            val releasesRepoUrl = "https://ossrh-staging-api.central.sonatype.com/service/local/staging/deploy/maven2/"
            url = uri(releasesRepoUrl)

            credentials(HttpHeaderCredentials::class) {
                name = "Authorization"
                value = "Bearer ${System.getenv("SONATYPE_AUTH_BASE64")}"
            }

            authentication {
                create<HttpHeaderAuthentication>("header")
            }
        }
    }
}

signing {
    val signingKey = project.findProperty("signingKey") as String? ?: System.getenv("SIGNING_KEY")
    val signingPassword = project.findProperty("signingPassword") as String? ?: System.getenv("SIGNING_PASSWORD")

    if (signingKey != null && signingPassword != null) {
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications)
    }
}
