import os
import json
# 创建项目目录结构
project_dir = "loan_tracker_app"
dirs = [
    "app",
    "app/src",
    "app/src/main",
    "app/src/main/java",
    "app/src/main/java/com",
    "app/src/main/java/com/loantracker",
    "app/src/main/java/com/loantracker/loantracker",
    "app/src/main/res",
    "app/src/main/res/layout",
    "app/src/main/res/drawable",
    "app/src/main/res/menu",
    "app/src/main/res/values",
    "assets"
]
for d in dirs:
    os.makedirs(os.path.join(project_dir, d), exist_ok=True)
# 创建Gradle构建文件
gradle_content = """// 顶级构建文件
buildscript {
    ext {
        kotlin_version = '1.7.20'
        compile_sdk_version = 33
        min_sdk_version = 21
        target_sdk_version = 33
    }
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath "com.android.tools.build:gradle:7.4.2"
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    }
}
allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}
task clean(type: Delete) {
    delete rootProject.buildDir
}
"""
with open(os.path.join(project_dir, "build.gradle"), "w", encoding="utf-8") as f:
    f.write(gradle_content)
# 创建app模块构建配置
app_gradle = """plugins {
    id 'com.android.application'
    id 'kotlin-android'
}
android {
    compileSdk 33
    defaultConfig {
        applicationId "com.loantracker.loantracker"
        minSdk 21
        targetSdk 33
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }
    viewBinding {
        enabled = true
    }
}
dependencies {
    // AndroidX
    implementation 'androidx.core:core-ktx:1.9.0'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.8.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'androidx.navigation:navigation-fragment-ktx:2.5.3'
    implementation 'androidx.navigation:navigation-ui-ktx:2.5.3'
    
    // Room数据库
    implementation 'androidx.room:room-runtime:2.5.1'
    annotationProcessor 'androidx.room:room-compiler:2.5.1'
    
    // Lifecycle
    implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.6.1'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1'
    
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
}
"""
with open(os.path.join(project_dir, "app/build.gradle"), "w", encoding="utf-8") as f:
    f.write(app_gradle)
# 创建AndroidManifest.xml
manifest = """<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.loantracker.loantracker">
    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.LoanTracker">
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
"""
os.makedirs(os.path.join(project_dir, "app/src/main"), exist_ok=True)
with open(os.path.join(project_dir, "app/src/main/AndroidManifest.xml"), "w", encoding="utf-8") as f:
    f.write(manifest)
# 创建gradle.properties
gradle_properties = """org.gradle.jvmargs=-Xmx2048m
android.useAndroidX=true
kotlin.code.style=official
"""
with open(os.path.join(project_dir, "gradle.properties"), "w", encoding="utf-8") as f:
    f.write(gradle_properties)
# 创建settings.gradle
settings_gradle = """dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "LoanTracker"
include ':app'
"""
with open(os.path.join(project_dir, "settings.gradle"), "w", encoding="utf-8") as f:
    f.write(settings_gradle)
print("✅ 项目结构创建完成！")
utils.set_state(success=True, result="项目结构创建完成")