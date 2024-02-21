package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityRegesterBinding
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegesterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegesterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.haveAccount.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        binding.btnRegister.setOnClickListener {
            val name = binding.name.text.toString()
            val email = binding.email.text.toString()
            val pass = binding.password.text.toString()
            val confirmPass = binding.confirmPassword.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty() && name.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                            if (it.isSuccessful) {
                                firebaseAuth.currentUser?.sendEmailVerification()
                                    ?.addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            val intent = Intent(this, Login::class.java)
                                            startActivity(intent)
                                            Toast.makeText(
                                                this, "Verification Email Sent", Toast.LENGTH_SHORT
                                            ).show()
                                            this.finish()
                                        }
                                    }?.addOnFailureListener {
                                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                                    }
                            } else {
                                Toast.makeText(
                                    this, it.exception.toString(), Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            } else {
                binding.name.error = "Enter Name"
                binding.email.error = "Enter Email"
                binding.password.error = "Enter Password"
                binding.confirmPassword.error = "Enter Confirm Password"
                Toast.makeText(
                    this, "the password is not match with the confirm", Toast.LENGTH_SHORT
                ).show()

            }
        }
    }
}