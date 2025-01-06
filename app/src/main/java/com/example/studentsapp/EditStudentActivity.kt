package com.example.studentsapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class EditStudentActivity : AppCompatActivity() {

    companion object {
        const val DELETE_RESULT = 2 // קוד ייחודי למחיקה
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        // קישור רכיבים
        val nameEditText: EditText = findViewById(R.id.editTextEditName)
        val idEditText: EditText = findViewById(R.id.editTextEditId)
        val phoneEditText: EditText = findViewById(R.id.editTextEditPhone)
        val addressEditText: EditText = findViewById(R.id.editTextEditAddress)
        val saveButton: Button = findViewById(R.id.buttonSaveStudent)
        val deleteButton: Button = findViewById(R.id.buttonDeleteStudent)

        // קבלת נתונים
        val name = intent.getStringExtra("name") ?: ""
        val id = intent.getStringExtra("id") ?: ""
        val phone = intent.getStringExtra("phone") ?: ""
        val address = intent.getStringExtra("address") ?: ""

        nameEditText.setText(name)
        idEditText.setText(id)
        phoneEditText.setText(phone)
        addressEditText.setText(address)

        // שמירת שינויים
        saveButton.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("name", nameEditText.text.toString())
                putExtra("id", idEditText.text.toString())
                putExtra("phone", phoneEditText.text.toString())
                putExtra("address", addressEditText.text.toString())
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        // מחיקת סטודנט
        deleteButton.setOnClickListener {
            Log.d("EditStudentActivity", "Delete button clicked for ID: $id")
            val resultIntent = Intent().apply {
                putExtra("id", id)
            }
            setResult(DELETE_RESULT, resultIntent)
            finish()
        }

    }
}
