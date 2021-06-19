package com.imbackt.barbershop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        showAllUserData()
    }

    private fun showAllUserData() {
        val userUsername = intent.getStringExtra("username")
        val userName = intent.getStringExtra("name")
        val userEmail = intent.getStringExtra("email")
        val userPhone = intent.getStringExtra("phone")
        val userPassword = intent.getStringExtra("password")

        fullName.text = userName
        username.text = userUsername
        name.editText?.setText(userName)
        email.editText?.setText(userEmail)
        phone.editText?.setText(userPhone)
        password.editText?.setText(userPassword)
    }
}