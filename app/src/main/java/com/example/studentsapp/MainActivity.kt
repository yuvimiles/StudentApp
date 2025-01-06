package com.example.studentsapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val studentList = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewStudents)
        val fabAddStudent: FloatingActionButton = findViewById(R.id.fabAddStudent)

        // הגדרת ActivityResultLauncher
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val studentId = data?.getStringExtra("id")
                val isChecked = data?.getBooleanExtra("checked", false) ?: false

                // עדכון הסטטוס ברשימת הסטודנטים
                studentList.find { it.id == studentId }?.checked = isChecked

                // עדכון ה-RecyclerView
                adapter.notifyDataSetChanged()
            }
        }

        // הגדרת Adapter ל-RecyclerView
        adapter = StudentAdapter(studentList) { student ->
            val intent = Intent(this, StudentDetailsActivity::class.java).apply {
                putExtra("name", student.name)
                putExtra("id", student.id)
                putExtra("phone", student.phone)
                putExtra("address", student.address)
                putExtra("imageResId", student.imageResId)
                putExtra("checked", student.checked)
            }
            resultLauncher.launch(intent) // שימוש ב-ActivityResultLauncher
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // לחיצה על כפתור הוספת סטודנט
        fabAddStudent.setOnClickListener {
            Toast.makeText(this, "Add Student clicked", Toast.LENGTH_SHORT).show()
        }

        // נתונים זמניים לבדיקה
        studentList.add(Student("John Doe", "12345", "050-1234567", "Tel Aviv", false, R.drawable.ic_student))
        studentList.add(Student("Jane Smith", "67890", "052-7654321", "Jerusalem", true, R.drawable.ic_student))
        studentList.add(Student("Alice Brown", "11223", "054-9876543", "Haifa", false, R.drawable.ic_student))

        adapter.notifyDataSetChanged()
    }
}