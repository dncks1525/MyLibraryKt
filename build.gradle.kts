buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        gradlePlugin()
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