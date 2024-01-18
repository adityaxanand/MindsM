package com.example.mindsmom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ProgressBar

import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    lateinit var editTextEmail: TextInputEditText
    lateinit var editTextPassword: TextInputEditText
    lateinit var buttonLogin: Button
    lateinit var progressBar: ProgressBar
    lateinit var textView: TextView

    private val auth = FirebaseAuth.getInstance()

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val newIntent = Intent(this, dashboard_container::class.java)
            startActivity(newIntent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        buttonLogin = findViewById(R.id.btn_login)
        progressBar = findViewById(R.id.progressBar)
        textView = findViewById(R.id.registerNow)

        // Set click listener for textView
        textView.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
            finish()
        }

        buttonLogin.setOnClickListener{
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            progressBar.visibility = View.VISIBLE

            if (TextUtils.isEmpty(email)) {

                Toast.makeText(this@Login, "Enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this@Login, "Enter Password", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE // Hiding progress bar on error
                return@setOnClickListener
            }


            // Add your login logic here (using Firebase auth)
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this@Login) { task ->

                    progressBar.visibility = View.GONE

                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(this@Login, "Login Successful", Toast.LENGTH_SHORT).show()
                        val newIntent = Intent(this, dashboard_container::class.java)
                        startActivity(newIntent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@Login,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this@Login, message, Toast.LENGTH_SHORT).show()
    }
}
