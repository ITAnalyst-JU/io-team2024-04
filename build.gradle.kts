plugins {
    id("java")
    id("java-library")
    id("application")
}

group = "the.great.tcs" /* ;) */
version = "0.0.1"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

val gdxVersion = "1.12.1"
val roboVMVersion = "2.3.21"
val box2DLightsVersion = "1.5"
val ashleyVersion = "1.7.4"
val aiVersion = "1.8.2"
val gdxControllersVersion = "2.2.1"

dependencies {
    api("com.badlogicgames.gdx:gdx-backend-lwjgl3:$gdxVersion")
    api("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop")
    api("com.badlogicgames.gdx:gdx-bullet-platform:$gdxVersion:natives-desktop")
    api("com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion:natives-desktop")
    api("com.badlogicgames.gdx-controllers:gdx-controllers-desktop:$gdxControllersVersion")
    api("com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-desktop")
    api("com.badlogicgames.gdx:gdx:$gdxVersion")
    api("com.badlogicgames.gdx:gdx-bullet:$gdxVersion")
    api("com.badlogicgames.box2dlights:box2dlights:$box2DLightsVersion")
    api("com.badlogicgames.gdx:gdx-freetype:$gdxVersion")
    api("com.badlogicgames.ashley:ashley:$ashleyVersion")
    api("com.badlogicgames.gdx:gdx-ai:$aiVersion")
    api("com.badlogicgames.gdx-controllers:gdx-controllers-core:$gdxControllersVersion")
    api("com.badlogicgames.gdx:gdx-box2d:$gdxVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.+")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.assertj:assertj-core:3.+")
    testImplementation("org.mockito:mockito-core:5.+")
    testImplementation("com.ginsberg:junit5-system-exit:1.1.1")
    implementation("org.xerial:sqlite-jdbc:3.41.2.2")
    implementation("com.sparkjava:spark-core:2.9.3")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("org.slf4j:slf4j-nop:2.0.16")
}

application {
    mainClass.set("desktop.DesktopLauncher")
    tasks.run.get().workingDir = file("./src/main/resources")
}

tasks.test {
    description = "Runs the unit tests"
    useJUnitPlatform()
    workingDir = file("./src/test/resources")
    jvmArgs("-Xshare:off", "-XX:+EnableDynamicAgentLoading")
}

/* below tasks only for learning purposes */
tasks.register("danceWithGradleD(a)emon", JavaExec::class) {
    description = "Runs this project as a JVM application"
    dependsOn("classes")
    mainClass.set("desktop.DesktopLauncher")
    classpath = sourceSets["main"].runtimeClasspath
    standardInput = System.`in`
    workingDir = file("./src/main/resources")
    setIgnoreExitValue(true)
    if (System.getProperty("os.name").contains("mac", true)) {
        jvmArgs("$jvmArgs -XstartOnFirstThread")
    }
}

tasks.register("debug", JavaExec::class) {
    description = "Debug"
    dependsOn("classes")
    mainClass.set("desktop.DesktopLauncher")
    classpath = sourceSets["main"].runtimeClasspath
    standardInput = System.`in`
    workingDir = file("./src/main/resources")
    setIgnoreExitValue(true)
    if (System.getProperty("os.name").contains("mac", true)) {
        jvmArgs("$jvmArgs -XstartOnFirstThread")
    }

    debug = true
}