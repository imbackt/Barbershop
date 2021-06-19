package com.imbackt.barbershop

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_verify_phone.*
import java.util.concurrent.TimeUnit

class VerifyPhone : AppCompatActivity() {

    lateinit var verificationCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_phone)
        val phone = intent.getStringExtra("phone")
        sendVerificationCodeToUser(phone)

        verify.setOnClickListener {
            val code = otpCode.text.toString()
            if (code.isEmpty() || code.length < 6L) {
                otpCode.error = "Wrong OTP"
                otpCode.requestFocus()
                return@setOnClickListener
            }
            verify.isEnabled = false
            progress.visibility = View.VISIBLE
            verifyCode(code)
        }
    }

    private fun sendVerificationCodeToUser(phone: String?) {
        getInstance().verifyPhoneNumber(
            "+966$phone",
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            callbacks
        )
    }

    private val callbacks = object : OnVerificationStateChangedCallbacks() {

        override fun onCodeSent(p0: String, p1: ForceResendingToken) {
            super.onCodeSent(p0, p1)
            verificationCode = p0
        }

        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            val code = p0.smsCode
            if (code != null) {
                progress.visibility = View.VISIBLE
                verifyCode(code)
            }
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            Toast.makeText(this@VerifyPhone, p0.message, Toast.LENGTH_SHORT).show()
        }

    }

    private fun verifyCode(code: String) {
        val credential = getCredential(verificationCode, code)
        signInByCredentials(credential)
    }

    private fun signInByCredentials(credential: PhoneAuthCredential) {
        val firebaseAuth = Firebase.auth
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(Intent(this, UserProfile::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
            } else {
                Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}