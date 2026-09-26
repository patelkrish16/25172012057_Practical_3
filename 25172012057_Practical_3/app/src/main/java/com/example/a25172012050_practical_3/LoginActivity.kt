package com.example.a25172012050_practical_3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val emailEditText = findViewById<EditText>(R.id.editTextText)
        val passwordEditText = findViewById<EditText>(R.id.editTextText2)
        val loginButton = findViewById<MaterialButton>(R.id.login1button)
        val forgotPasswordText = findViewById<TextView>(R.id.forgottextView)
        val skipButton = findViewById<Button>(R.id.skipButton)

        // Login Button Click Listener -> Demonstrates Explicit Intent with ID and Password
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty()) {
                emailEditText.error = "Please enter Email or ID"
                emailEditText.requestFocus()
                Toast.makeText(this, "Please enter Email or ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                passwordEditText.error = "Please enter Password"
                passwordEditText.requestFocus()
                Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Login Successful! Welcome, $email", Toast.LENGTH_LONG).show()

            // Explicit Intent: Navigate from LoginActivity to MainActivity, passing user credentials
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("USER_ID", email)
                putExtra("USER_PASSWORD", password)
                putExtra("IS_LOGGED_IN", true)
            }
            startActivity(intent)
            finish()
        }

        // Direct shortcut to MainActivity
        skipButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("USER_ID", "Guest User")
                putExtra("IS_LOGGED_IN", false)
            }
            startActivity(intent)
            finish()
        }

        forgotPasswordText.setOnClickListener {
            Toast.makeText(this, "Forgot Password clicked. Reset link would be sent.", Toast.LENGTH_SHORT).show()
        }
    }
}