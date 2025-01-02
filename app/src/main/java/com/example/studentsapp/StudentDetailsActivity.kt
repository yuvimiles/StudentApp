package com.example.studentsapp

import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class StudentDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        // הגדרת ה-Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // הוספת כפתור חזרה
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // קבלת הנתונים מה-Intent
        val name = intent.getStringExtra("name") ?: "Unknown"
        val id = intent.getStringExtra("id") ?: "Unknown"
        val phone = intent.getStringExtra("phone") ?: "Unknown"
        val address = intent.getStringExtra("address") ?: "Unknown"
        val imageResId = intent.getIntExtra("imageResId", R.drawable.ic_student)
        val isChecked = intent.getBooleanExtra("checked", false)

        // קישור Views
        val imageView: ImageView = findViewById(R.id.imageViewStudentDetails)
        val nameTextView: TextView = findViewById(R.id.textViewStudentName)
        val idTextView: TextView = findViewById(R.id.textViewStudentId)
        val phoneTextView: TextView = findViewById(R.id.textViewStudentPhone)
        val addressTextView: TextView = findViewById(R.id.textViewStudentAddress)
        val checkboxChecked: CheckBox = findViewById(R.id.checkboxCheckedDetails)

        // עדכון Views עם הנתונים
        imageView.setImageResource(imageResId)
        nameTextView.text = "Name: $name"
        idTextView.text = "ID: $id"
        phoneTextView.text = "Phone: $phone"
        addressTextView.text = "Address: $address"
        checkboxChecked.isChecked = isChecked
    }

    // טיפול בלחיצה על כפתור החזרה
    override fun onSupportNavigateUp(): Boolean {
        finish() // חזרה לעמוד הקודם
        return true
    }
}
