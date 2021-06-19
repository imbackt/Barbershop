package com.imbackt.barbershop

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    private val database = Firebase.database
    private val reference = database.getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_sign_up)
    }

    private fun validateName(): Boolean {
        val value = regName.editText?.text.toString()

        return if (value.isEmpty()) {
            regName.error = "Name cannot be empty"
            false
        } else {
            regName.error = null
            regName.isErrorEnabled = false
            true
        }
    }

    private fun validateUsername(): Boolean {
        val value = regUsername.editText?.text.toString()

        return if (value.isEmpty()) {
            regUsername.error = "Username cannot be empty"
            false
        } else if (value.length > 32) {
            regUsername.error = "Username too long"
            false
        } else if (value.contains(" ")) {
            regUsername.error = "White spaces are not allowed"
            false
        } else {
            regUsername.error = null
            regUsername.isErrorEnabled = false
            true
        }
    }

    private fun validateEmail(): Boolean {
        val value = regEmail.editText?.text.toString()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        return if (value.isEmpty()) {
            regEmail.error = "Email cannot be empty"
            false
        } else if (!value.matches(emailPattern.toRegex())) {
            regEmail.error = "Invalid email address"
            false
        } else {
            regEmail.error = null
            true
        }
    }

    private fun validatePhoneNumber(): Boolean {
        val value = regPhone.editText?.text.toString()

        return if (value.isEmpty()) {
            regPhone.error = "Phone cannot be empty"
            false
        } else {
            regPhone.error = null
            true
        }
    }

    private fun validatePassword(): Boolean {
        val value = regPassword.editText?.text.toString()
        val  passwordVal = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%!\\-_?&])(?=\\S+\$).{8,}"

        return if (value.isEmpty()) {
            regPassword.error = "Password cannot be empty"
            false
        } else if (!value.matches(passwordVal.toRegex())){
            regPassword.error = "Password is weak"
            false
        } else {
            regPassword.error = null
            regPassword.isErrorEnabled = false
            true
        }
    }

    fun registerUser(view: View) {

        if (!validateName() or !validateUsername() or !validateEmail() or !validatePhoneNumber() or !validatePassword()) {
            return
        }

        val name = regName.editText?.text.toString()
        val username = regUsername.editText?.text.toString()
        val email = regEmail.editText?.text.toString()
        val phone = regPhone.editText?.text.toString()
        val password = regPassword.editText?.text.toString()

        startActivity(Intent(this, VerifyPhone::class.java).apply {
            putExtra("phone", phone)
        })


        //val user = User(name, username, email, phone, password)
        //reference.child(username).setValue(user)

        //Toast.makeText(this, "Your Account has been created successfully", Toast.LENGTH_SHORT).show()
        //startActivity(Intent(this, Login::class.java))
        //finish()
    }
}