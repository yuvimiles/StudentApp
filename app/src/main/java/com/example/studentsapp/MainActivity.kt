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
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    companion object {
        val studentList = mutableListOf<Student>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewStudents)
        val fabAddStudent: FloatingActionButton = findViewById(R.id.fabAddStudent)

        // הגדרת ActivityResultLauncher
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    val data = result.data
                    val id = data?.getStringExtra("id") ?: return@registerForActivityResult
                    val name = data.getStringExtra("name") ?: return@registerForActivityResult
                    val phone = data.getStringExtra("phone") ?: return@registerForActivityResult
                    val address = data.getStringExtra("address") ?: return@registerForActivityResult
                    val checked = data.getBooleanExtra("checked", false)

                    val index = studentList.indexOfFirst { it.id == id }
                    if (index != -1) {
                        val existingStudent = studentList[index]
                        existingStudent.name = name
                        existingStudent.phone = phone
                        existingStudent.address = address
                        existingStudent.checked = checked
                        adapter.notifyItemChanged(index)
                        Log.d("MainActivity", "Updated student: $existingStudent")
                    } else {
                        val newStudent = Student(name, id, phone, address, checked, R.drawable.ic_student)
                        studentList.add(newStudent)
                        adapter.notifyItemInserted(studentList.size - 1)
                        Log.d("MainActivity", "Added new student: $newStudent")
                    }
                }

                EditStudentActivity.DELETE_RESULT -> {
                    val data = result.data
                    val id = data?.getStringExtra("id") ?: return@registerForActivityResult

                    val index = studentList.indexOfFirst { it.id == id }
                    if (index != -1) {
                        studentList.removeAt(index)
                        adapter.notifyItemRemoved(index)
                        Log.d("MainActivity", "Deleted student with ID: $id")
                        Toast.makeText(this, "Student deleted successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.d("MainActivity", "Student with ID: $id not found")
                        Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // הגדרת Adapter ל-RecyclerView
        adapter = StudentAdapter(studentList) { student ->
            val intent = Intent(this, EditStudentActivity::class.java).apply {
                putExtra("name", student.name)
                putExtra("id", student.id)
                putExtra("phone", student.phone)
                putExtra("address", student.address)
            }
            resultLauncher.launch(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        fabAddStudent.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            resultLauncher.launch(intent)
        }

        if (studentList.isEmpty()) {
            studentList.add(Student("John Doe", "12345", "050-1234567", "Tel Aviv", false, R.drawable.ic_student))
            studentList.add(Student("Jane Smith", "67890", "052-7654321", "Jerusalem", true, R.drawable.ic_student))
            studentList.add(Student("Alice Brown", "11223", "054-9876543", "Haifa", false, R.drawable.ic_student))
        }
    }
}
