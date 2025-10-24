package com.example.giftguard_login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
// View Binding import 경로
import com.example.giftguard_login.databinding.ActivityGoogleLoginBinding

class GoogleLoginActivity : AppCompatActivity() {

    private val TAG = "GoogleLoginActivity"
    private val RC_SIGN_IN = 9001

    private lateinit var googleSignInClient: GoogleSignInClient
    // View Binding 인스턴스
    private lateinit var binding: ActivityGoogleLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🛑 보라색 액션바/툴바 제거 (Activity에서 숨기기)
        supportActionBar?.hide()

        // View Binding 초기화
        binding = ActivityGoogleLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. GoogleSignInClient 구성 및 초기화
        googleSignInClient = getGoogleClient()

        // 2. 로그인 버튼 클릭 리스너 설정
        // XML ID: google_sign_in_button_custom에 따라 View Binding 속성인
        // googleSignInButtonCustom을 사용합니다.
        binding.googleSignInButtonCustom.setOnClickListener {
            signIn()
        }
    }

    private fun getGoogleClient(): GoogleSignInClient {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(getString(R.string.google_login_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(this, googleSignInOption)
    }

    private fun signIn() {
        Log.d(TAG, "Starting Google Sign In flow...")
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            val serverAuthCode = account.serverAuthCode

            Log.d(TAG, "Sign-in successful! User ID: ${account.id}, Email: ${account.email}")
            Log.d(TAG, "Server Auth Code: $serverAuthCode")

            // TODO: 백엔드 인증 로직 구현

        } catch (e: ApiException) {
            Log.w(TAG, "Google sign in failed", e)
            Log.w(TAG, "Status Code: ${e.statusCode}")
        }
    }
}