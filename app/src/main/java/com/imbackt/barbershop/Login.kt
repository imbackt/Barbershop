package com.imbackt.barbershop

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_login)

        signUp.setOnClickListener {
            val pairs = arrayOf(
                Pair<View, String>(image, "logo_image"),
                Pair<View, String>(logo_name, "logo_text"),
                Pair<View, String>(slogn, "logo_desc"),
                Pair<View, String>(loginUsername, "username_trans"),
                Pair<View, String>(loginPassword, "password_trans"),
                Pair<View, String>(login, "login_trans"),
                Pair<View, String>(signUp, "new_user_trans")
            )
            val options = ActivityOptions.makeSceneTransitionAnimation(this, *pairs)
            startActivity(Intent(this, SignUp::class.java), options.toBundle())
        }
    }

    private fun validateUsername(): Boolean {
        val value = loginUsername.editText?.text.toString()

        return if (value.isEmpty()) {
            loginUsername.error = "Username cannot be empty"
            false
        } else {
            loginUsername.error = null
            loginUsername.isErrorEnabled = false
            true
        }
    }

    private fun validatePassword(): Boolean {
        val value = loginPassword.editText?.text.toString()

        return if (value.isEmpty()) {
            loginPassword.error = "Password cannot be empty"
            false
        } else {
            loginPassword.error = null
            loginPassword.isErrorEnabled = false
            true
        }
    }

    fun loginUser(view: View) {
        if (!validateUsername() or !validatePassword()) {
            return
        } else {
            isUser()
        }
    }

    private fun isUser() {
        val userUsername = loginUsername.editText?.text.toString()
        val userPassword = loginPassword.editText?.text.toString()
        val reference = Firebase.database.getReference("users")
        val checkUser = reference.orderByChild("username").equalTo(userUsername)
        checkUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    loginUsername.error = null
                    loginUsername.isErrorEnabled = false

                    val passwordFromDB = snapshot.child(userUsername).child("password").value.toString()
                    if (passwordFromDB == userPassword) {

                        loginPassword.error = null
                        loginPassword.isErrorEnabled = false

                        val nameFromDB = snapshot.child(userUsername).child("name").value.toString()
                        val usernameFromDB = snapshot.child(userUsername).child("username").value.toString()
                        val emailFromDB = snapshot.child(userUsername).child("email").value.toString()
                        val phoneFromDB = snapshot.child(userUsername).child("phone").value.toString()

                        startActivity(Intent(this@Login, UserProfile::class.java).apply {
                            putExtra("name", nameFromDB)
                            putExtra("username", usernameFromDB)
                            putExtra("email", emailFromDB)
                            putExtra("phone", phoneFromDB)
                            putExtra("password", passwordFromDB)
                        })
                    } else {
                        loginPassword.error = "Wrong Password!"
                        loginPassword.requestFocus()
                    }
                } else {
                    loginUsername.error = "No such User exist"
                    loginUsername.requestFocus()
                }
            }

        })
    }
}