import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.gradlePlugin() {
    classpath("com.android.tools.build:gradle:${Versions.ANDROID_GRADLE}")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}")
}

fun DependencyHandler.common() {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN}")
    implementation("androidx.appcompat:appcompat:${Versions.APPCOMPAT}")
    implementation("androidx.core:core-ktx:${Versions.CORE_KTX}")
    implementation("androidx.collection:collection-ktx:${Versions.COLLECTION_KTX}")
    implementation("androidx.activity:activity-ktx:${Versions.ACTIVITY_KTX}")
    implementation("androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}")
    implementation("com.google.android.material:material:${Versions.MATERIAL}")
    implementation("androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINTLAYOUT}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}")
}

fun DependencyHandler.junit4() {
    testImplementation("junit:junit:${Versions.JUNIT4}")
    androidTestImplementation("androidx.test.ext:junit:${Versions.JUNIT4_TEST_EXT}")
}

fun DependencyHandler.truth() {
    testImplementation("com.google.truth:truth:${Versions.TRUTH}")
}

fun DependencyHandler.robolectric() {
    testImplementation("org.robolectric:robolectric:${Versions.ROBOLECTRIC}")
}

fun DependencyHandler.lifecycle() {
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}")
//    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.LIFECYCLE}")
//    implementation("androidx.lifecycle:lifecycle-service:${Versions.LIFECYCLE}")
//    implementation("androidx.lifecycle:lifecycle-process:${Versions.LIFECYCLE}")
    kapt("androidx.lifecycle:lifecycle-compiler:${Versions.LIFECYCLE}")
}

fun DependencyHandler.work() {
//    implementation("androidx.work:work-runtime:${Versions.WORK}") // java only
    implementation("androidx.work:work-runtime-ktx:${Versions.WORK}") // kotlin + coroutines
//    implementation("androidx.work:work-rxjava2:${Versions.WORK}")
//    implementation("androidx.work:work-gcm:${Versions.WORK}")
//    androidTestImplementation("androidx.work:work-testing:${Versions.WORK}")
//    implementation("androidx.work:work-multiprocess:${Versions.WORK}")
}

fun DependencyHandler.room() {
    implementation("androidx.room:room-runtime:${Versions.ROOM}")
    kapt("androidx.room:room-compiler:${Versions.ROOM}")
    implementation("androidx.room:room-ktx:${Versions.ROOM}")
    testImplementation("androidx.room:room-testing:${Versions.ROOM}")
}

fun DependencyHandler.paging() {
    implementation("androidx.paging:paging-runtime:${Versions.PAGING}")
    testImplementation("androidx.paging:paging-common:${Versions.PAGING}")
}

fun DependencyHandler.dagger() {
    implementation("com.google.dagger:dagger:${Versions.DAGGER}")
    implementation("com.google.dagger:dagger-android:${Versions.DAGGER}")
    implementation("com.google.dagger:dagger-android-support:${Versions.DAGGER}")
    kapt("com.google.dagger:dagger-android-processor:${Versions.DAGGER}")
    kapt("com.google.dagger:dagger-compiler:${Versions.DAGGER}")
}

fun DependencyHandler.hiltPlugin() {
    classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}")
}

fun DependencyHandler.hilt() {
    implementation("com.google.dagger:hilt-android:${Versions.HILT}")
    kapt("com.google.dagger:hilt-compiler:${Versions.HILT}")
    testImplementation("com.google.dagger:hilt-android-testing:${Versions.HILT}")
    kaptTest("com.google.dagger:hilt-compiler:${Versions.HILT}")
    androidTestImplementation("com.google.dagger:hilt-android-testing:${Versions.HILT}")
    kaptAndroidTest("com.google.dagger:hilt-compiler:${Versions.HILT}")
}

fun DependencyHandler.retrofit() {
    implementation("com.squareup.retrofit2:retrofit:${Versions.RETROFIT}")
    implementation("com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}")
}

fun DependencyHandler.glide() {
    implementation("com.github.bumptech.glide:glide:${Versions.GLIDE}")
    kapt("com.github.bumptech.glide:compiler:${Versions.GLIDE}")
}

private fun DependencyHandler.kapt(depName: String) {
    add("kapt", depName)
}

private fun DependencyHandler.kaptTest(depName: String) {
    add("kaptTest", depName)
}

private fun DependencyHandler.kaptAndroidTest(depName: String) {
    add("kaptAndroidTest", depName)
}

private fun DependencyHandler.implementation(depName: String) {
    add("implementation", depName)
}

private fun DependencyHandler.testImplementation(depName: String) {
    add("testImplementation", depName)
}

private fun DependencyHandler.androidTestImplementation(depName: String) {
    add("androidTestImplementation", depName)
}

private fun DependencyHandler.classpath(depName: String) {
    add("classpath", depName)
}