buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    dependencies {
        gradlePlugin()
        hiltPlugin()
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}