package com.example.studentsapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val nameEditText: EditText = findViewById(R.id.editTextName)
        val idEditText: EditText = findViewById(R.id.editTextId)
        val phoneEditText: EditText = findViewById(R.id.editTextPhone)
        val addressEditText: EditText = findViewById(R.id.editTextAddress)
        val addButton: Button = findViewById(R.id.buttonAddStudent)
        val cancelButton: Button = findViewById(R.id.buttonCancel)

        addButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val id = idEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val address = addressEditText.text.toString()

            if (name.isNotBlank() && id.isNotBlank() && phone.isNotBlank() && address.isNotBlank()) {
                val intent = Intent().apply {
                    putExtra("name", name)
                    putExtra("id", id)
                    putExtra("phone", phone)
                    putExtra("address", address)
                }
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        cancelButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}
