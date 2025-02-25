plugins {
    //alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    //alias(libs.plugins.google.gms.google.services)
    //alias(libs.plugins.google.gms.google.services)
    //id("com.google.gms.google-services")
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.chaquo.python")
}

android {
    namespace = "pl.mlynarczyk.pianoapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "pl.mlynarczyk.pianoapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        ndk {
             abiFilters += listOf("x86_64","armeabi-v7a") //arm64
           // abiFilters += listOf("armeabi-v7a","arm64-v8a", "x86_64","x86")
        }

        externalNativeBuild {
            cmake {
                cppFlags ("-std=c++14 -fexceptions -frtti")
                arguments ("-DANDROID_STL=c++_shared")
            }
        }


    }
    ndkVersion = "28.0.12674087"
    /*
    externalNativeBuild {
        cmake {
            path(file( "E:/Kamila/Inzynierka/PianoApp/app/CMakeList.txt"))
        }
    }*/
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    flavorDimensions += "pyVersion"
    productFlavors {
        //create("py310") { dimension = "pyVersion" }
        create("py38") { dimension = "pyVersion" }
    }
    sourceSets {
        getByName("main") {
            jniLibs.srcDirs("src/main/jniLibs")
        }
    }
}

chaquopy {
    productFlavors {
        //getByName("py310") { version = "3.10" }
        getByName("py38") { version = "3.8" }
    }
    sourceSets {
        getByName("main") {
            srcDir("src/main/java/pl/mlynarczyk/pianoapp/python")
        }
    }

    defaultConfig {
        //version = "3.8"

        pip {
            install("librosa==0.9.2")
            install("resampy==0.3.1")
            install("ffmpeg")
            install("pysoundfile")
            install("soundfile")
            install("tensorflow")
           // install("E:\\Kamila\\Inzynierka\\PianoApp\\app\\libs\\UNKNOWN-0.0.0-py3-none-any.whl")

           // install("matplotlib==3.3.4")
            //install("scikit-learn==1.1.1")
            //   install("pyAudioProcessing")
           // install("E:\\Kamila\\Inzynierka\\PianoApp\\app\\libs\\UNKNOWN-0.0.0-cp38-cp38-linux_x86_64.whl")//msgpack 1.1.0

           // install("E:\\Kamila\\Inzynierka\\PianoApp\\app\\libs\\UNKNOWN-0.0.0-py3-none-any.whl") //numpy 2.2.0
            //install("E:\\Kamila\\Inzynierka\\PianoApp\\app\\libs\\librosa-0.10.2.post1-py3-none-any.whl")




        }
        //buildPython("C:/Users/Kamila/AppData/Local/Programs/Python/Python38/python.exe")
       // buildPython("C:/Windows/py.exe", "-3.8")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.core.splashscreen) //przy ladowaniu aplikacji
    implementation (libs.androidx.fragment.compose)
    implementation(libs.androidx.navigation.compose) //nawigacja
    implementation (libs.androidx.room.runtime) //room
    implementation (libs.room.ktx)//room
    implementation (libs.kotlinx.coroutines.android)
    implementation(libs.firebase.database)
    implementation(platform(libs.firebase.bom.v3330))
    implementation(libs.firebase.ui.database)
    implementation(libs.material3)


    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation(libs.google.firebase.database)
    implementation (libs.firebase.database.v2030)

    implementation(libs.coil.compose)  // Upewnij się, że używasz najnowszej wersji
    implementation("io.coil-kt:coil-svg:2.2.2")
    implementation(libs.volley)
    implementation(libs.firebase.storage)
    kapt(libs.androidx.room.compiler) //room
    implementation ("androidx.compose.material:material-icons-extended:1.4.3")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation ("com.arthenica:mobile-ffmpeg-full:4.4.LTS")

  //  implementation("org.tensorflow:tensorflow-lite-task-vision:0.4.0")
   // implementation("org.tensorflow:tensorflow-lite-gpu-delegate-plugin:0.4.0")
   // implementation("org.tensorflow:tensorflow-lite-gpu:2.9.0")

    /*implementation( libs.tarsosdsp)

    implementation (libs.ui )// Compose UI dependency
    implementation (libs.androidx.foundation) // Compose foundation dependency
    implementation (libs.androidx.material )// Compose material dependency*/
   // implementation(files("libs/TarsosDSP-2.3.jar"))
    implementation("com.google.code.gson:gson:2.10.1") // do zapisu json
    //implementation ("E:\\Kamila\\Inzynierka\\PianoApp\\app\\libs\\TarsosDSP-Android-2.4.jar")
    //implementation ("com.github.hiteshsondhi88.libffmpeg:android-ffmpeg:0.3.2")
    //implementation(libs.accompanist.svg)
}