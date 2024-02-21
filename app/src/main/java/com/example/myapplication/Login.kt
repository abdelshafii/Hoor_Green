package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.goRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.emailLogin.text.toString()
            val password = binding.passwordLogin.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val verification = firebaseAuth.currentUser?.isEmailVerified
                        if (verification == false) {
                            Toast.makeText(
                                this,
                                "please verify your email",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            val intent = Intent(this, Home::class.java)
                            startActivity(intent)
                            this.finish()
                        }
                    } else {
                        binding.emailLogin.error = "Email is not correct"
                        binding.passwordLogin.error = "Password is not correct"
                        Toast.makeText(
                            this,
                            "the email or password is not matching",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } else {
                binding.emailLogin.error = "Email is not correct"
                binding.passwordLogin.error = "Password is not correct"
                Toast.makeText(
                    this,
                    "please enter the empty fields",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        // ctrl + alt + l    to format code
        //ctrl + shift + /   to comment
        /*var login_btn = findViewById<Button>(R.id.btn_login)
       var email = findViewById<EditText>(R.id.email_login)
       var password = findViewById<EditText>(R.id.password_login)
       login_btn.setOnClickListener {
       }*/
    }


    fun hideSoftware(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}