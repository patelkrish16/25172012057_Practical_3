package com.example.a25172012050_practical_3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.CallLog
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Window insets padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        // Display user ID / Email and Password if passed via Explicit Intent from LoginActivity
        val statusTextView = findViewById<TextView>(R.id.statusTextView)
        val userId = intent.getStringExtra("USER_ID")
        val userPassword = intent.getStringExtra("USER_PASSWORD")
        val isLoggedIn = intent.getBooleanExtra("IS_LOGGED_IN", false)

        if (!userId.isNullOrEmpty()) {
            if (isLoggedIn) {
                statusTextView.text = "✓ Explicit Intent Data Received:\n• User Name: $userId\n• Password: $userPassword"
                statusTextView.setTextColor(getColor(android.R.color.holo_green_light))
            } else {
                statusTextView.text = "Welcome, $userId"
            }
        }

        // 1. Implicit Intent - Browse Web URL
        val urlEditText = findViewById<EditText>(R.id.weburleditTextText)
        findViewById<Button>(R.id.weburlbutton).setOnClickListener {
            var url = urlEditText.text.toString().trim()
            if (url.isEmpty()) {
                url = "https://www.google.com"
            }
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://$url"
            }
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Could not open URL: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        // 2. Implicit Intent - Dial Phone Number
        val phoneEditText = findViewById<EditText>(R.id.phonenoeditTextText2)
        findViewById<Button>(R.id.phonenobutton).setOnClickListener {
            var phone = phoneEditText.text.toString().trim()
            if (phone.isEmpty()) {
                phone = "9898573586"
            }
            try {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Could not open dialer: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        // 3. Implicit Intent - View Call Log
        findViewById<Button>(R.id.callogbutton).setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    type = CallLog.Calls.CONTENT_TYPE
                }
                startActivity(intent)
            } catch (e: Exception) {
                try {
                    val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse("content://call_log/calls"))
                    startActivity(fallbackIntent)
                } catch (ex: Exception) {
                    Toast.makeText(this, "Call Log is not available on this device", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 4. Implicit Intent - Open Gallery
        findViewById<Button>(R.id.gallarybutton).setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivity(intent)
            } catch (e: Exception) {
                try {
                    val fallbackIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
                        type = "image/*"
                    }
                    startActivity(fallbackIntent)
                } catch (ex: Exception) {
                    Toast.makeText(this, "Gallery app not found", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 5. Implicit Intent - Capture Image with Camera
        findViewById<Button>(R.id.camerabutton).setOnClickListener {
            try {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Camera app not found: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        // 6. Implicit Intent - Set Alarm
        findViewById<Button>(R.id.alarmbutton).setOnClickListener {
            try {
                val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                    putExtra(AlarmClock.EXTRA_HOUR, 7)
                    putExtra(AlarmClock.EXTRA_MINUTES, 0)
                    putExtra(AlarmClock.EXTRA_MESSAGE, "Wake Up")
                    putExtra(AlarmClock.EXTRA_SKIP_UI, false)
                }
                startActivity(intent)
            } catch (e: Exception) {
                try {
                    val showAlarmsIntent = Intent(AlarmClock.ACTION_SHOW_ALARMS)
                    startActivity(showAlarmsIntent)
                } catch (ex: Exception) {
                    Toast.makeText(this, "Alarm app not found: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 7. Explicit Intent - Open Login Activity
        findViewById<Button>(R.id.loginbutton).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java).apply {
                putExtra("EXTRA_SOURCE", "MainActivity")
            }
            startActivity(intent)
        }
    }
}