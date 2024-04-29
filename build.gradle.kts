plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "ru.valerit"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

project(":server") {
    apply (plugin="application")
    configure<JavaApplication> {
        mainClass = "server.App"
    }
    dependencies {
        implementation(project(":common"))
    }
    tasks.withType<Jar> {
        manifest {
            attributes["Main-Class"] = "server.App"
        }
    }
}


project(":client") {
    apply (plugin="application")
    configure<JavaApplication> {
        mainClass = "client.App"
    }
    dependencies {
        implementation(project(":common"))
    }
    val run by tasks.getting(JavaExec::class) {
        standardInput = System.`in`
    }
    tasks.withType<Jar> {
        manifest {
            attributes["Main-Class"] = "client.App"
        }
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}