plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // 💡 kotlinx-serialization을 사용하려면 이 플러그인을 추가해야 합니다.
    // 🚩 오류 수정: 모듈 수준에서 'version'을 제거하고 ID만 남깁니다.
    id("org.jetbrains.kotlin.plugin.serialization")
    // id("com.google.gms.google-services") // Firebase를 위한 플러그인
}

android {
    namespace = "com.example.giftguard_login"
    // compileSdk를 현재 최신 안정화 버전(34)으로 조정했습니다.
    // 36은 아직 preview 단계일 수 있습니다.
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.giftguard_login"
        minSdk = 24
        targetSdk = 34 // compileSdk와 동일하게 조정
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // 📌 View Binding 기능 활성화
    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    // JVM Target을 17 또는 1.8(8)로 변경하는 것이 더 일반적입니다. (11도 가능)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // ----------------------------------------------------------------------------------
    // 🔥 Retrofit 및 Ktor 서버 통신 관련 종속성
    // ----------------------------------------------------------------------------------

    // Retrofit (HTTP 클라이언트)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Kotlin Serialization (JSON 데이터 직렬화/역직렬화)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // Retrofit에서 Kotlinx Serialization을 사용하기 위한 컨버터
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")

    // OkHttp 로깅 인터셉터 (네트워크 요청/응답 디버깅에 유용)
    // 최신 OkHttp 버전(4.12.0)에 맞춰 사용
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // ----------------------------------------------------------------------------------
    // 🔒 Firebase/Google Auth 종속성
    // ----------------------------------------------------------------------------------

    // Firebase BOM (Bill of Materials) - 버전 관리
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))

    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth:22.3.1")

    // Google Auth 서비스
    implementation("com.google.android.gms:play-services-auth:21.0.0")

    // ----------------------------------------------------------------------------------
    // 기본 AndroidX 및 테스트 종속성
    // ----------------------------------------------------------------------------------
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // ConstraintLayout, RecyclerView 등 레이아웃 관련 유용한 라이브러리를 추가할 수 있습니다.

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}