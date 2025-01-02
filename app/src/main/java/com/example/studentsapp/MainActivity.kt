package com.example.studentsapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private val studentList = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewStudents)
        val fabAddStudent: FloatingActionButton = findViewById(R.id.fabAddStudent)

        adapter = StudentAdapter(studentList) { student ->
            Toast.makeText(this, "Clicked: ${student.name}", Toast.LENGTH_SHORT).show()
            // Intent to open Student Details Activity
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        fabAddStudent.setOnClickListener {
            // Intent to open Add Student Activity
        }

        // Temporary data for testing
        studentList.add(Student("John Doe", "12345", "050-1234567", "Tel Aviv", false, R.drawable.ic_student))
        studentList.add(Student("Jane Smith", "67890", "052-7654321", "Jerusalem", true, R.drawable.ic_student))
        studentList.add(Student("Alice Brown", "11223", "054-9876543", "Haifa", false, R.drawable.ic_student))

        adapter.notifyDataSetChanged()

    }
}
