package com.example.studentsapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class StudentDetailsActivity : AppCompatActivity() {
    private var isChecked: Boolean = false
    private var studentId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val name = intent.getStringExtra("name") ?: "Unknown"
        studentId = intent.getStringExtra("id") ?: "Unknown"
        val phone = intent.getStringExtra("phone") ?: "Unknown"
        val address = intent.getStringExtra("address") ?: "Unknown"
        val imageResId = intent.getIntExtra("imageResId", R.drawable.ic_student)
        isChecked = intent.getBooleanExtra("checked", false)

        val imageView: ImageView = findViewById(R.id.imageViewStudentDetails)
        val nameTextView: TextView = findViewById(R.id.textViewStudentName)
        val idTextView: TextView = findViewById(R.id.textViewStudentId)
        val phoneTextView: TextView = findViewById(R.id.textViewStudentPhone)
        val addressTextView: TextView = findViewById(R.id.textViewStudentAddress)
        val checkboxChecked: CheckBox = findViewById(R.id.checkboxCheckedDetails)
        val editButton: Button = findViewById(R.id.buttonEditStudent)

        imageView.setImageResource(imageResId)
        nameTextView.text = "Name: $name"
        idTextView.text = "ID: $studentId"
        phoneTextView.text = "Phone: $phone"
        addressTextView.text = "Address: $address"
        checkboxChecked.isChecked = isChecked

        checkboxChecked.setOnCheckedChangeListener { _, isChecked ->
            this.isChecked = isChecked
        }

        editButton.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java).apply {
                putExtra("name", name)
                putExtra("id", studentId)
                putExtra("phone", phone)
                putExtra("address", address)
                putExtra("checked", isChecked)
            }
            startActivityForResult(intent, 300)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            Activity.RESULT_OK -> {
                if (requestCode == 300) {
                    val updatedName = data?.getStringExtra("name") ?: return
                    val updatedPhone = data.getStringExtra("phone") ?: return
                    val updatedAddress = data.getStringExtra("address") ?: return
                    val updatedChecked = data.getBooleanExtra("checked", false)

                    findViewById<TextView>(R.id.textViewStudentName).text = "Name: $updatedName"
                    findViewById<TextView>(R.id.textViewStudentPhone).text = "Phone: $updatedPhone"
                    findViewById<TextView>(R.id.textViewStudentAddress).text = "Address: $updatedAddress"
                    findViewById<CheckBox>(R.id.checkboxCheckedDetails).isChecked = updatedChecked

                    isChecked = updatedChecked
                    intent.putExtra("name", updatedName)
                    intent.putExtra("phone", updatedPhone)
                    intent.putExtra("address", updatedAddress)
                    intent.putExtra("checked", updatedChecked)
                }
            }
            EditStudentActivity.DELETE_RESULT -> {
                val resultIntent = Intent()
                resultIntent.putExtra("id", studentId)
                resultIntent.putExtra("delete", true)
                setResult(EditStudentActivity.DELETE_RESULT, resultIntent)
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val resultIntent = intent
        resultIntent.putExtra("id", studentId)
        resultIntent.putExtra("checked", isChecked)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
        return true
    }
}