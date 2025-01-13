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
        const val STUDENT_DETAILS_REQUEST = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewStudents)
        val fabAddStudent: FloatingActionButton = findViewById(R.id.fabAddStudent)

        // Setup ActivityResultLauncher
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    if (data?.getBooleanExtra("delete", false) == true) {
                        handleStudentDelete(data)
                    } else {
                        handleStudentUpdate(data)
                    }
                }
                EditStudentActivity.DELETE_RESULT -> {
                    handleStudentDelete(data)
                }
            }
        }

        adapter = StudentAdapter(studentList) { student ->
            val intent = Intent(this, StudentDetailsActivity::class.java).apply {
                putExtra("name", student.name)
                putExtra("id", student.id)
                putExtra("phone", student.phone)
                putExtra("address", student.address)
                putExtra("imageResId", student.imageResId)
                putExtra("checked", student.checked)
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
            studentList.add(Student("Imri Moyal", "00001", "052-7654321", "Rishon Lezion", true, R.drawable.ic_student))
            studentList.add(Student("Yuval Miles", "00002", "050-1234567", "Tel Aviv", false, R.drawable.ic_student))
            studentList.add(Student("Eviya babay", "00003", "052-7654344", "Jerusalem", true, R.drawable.ic_student))
            studentList.add(Student("Matan Ben Sahel", "00004", "054-9876555", "Haifa", false, R.drawable.ic_student))
            studentList.add(Student("Or Natan", "00005", "054-9876543", "Ramla", false, R.drawable.ic_student))
        }
    }

    private fun handleStudentUpdate(data: Intent?) {
        val id = data?.getStringExtra("id") ?: return
        val name = data.getStringExtra("name") ?: return
        val phone = data.getStringExtra("phone") ?: return
        val address = data.getStringExtra("address") ?: return
        val checked = data.getBooleanExtra("checked", false)

        val index = studentList.indexOfFirst { it.id == id }
        if (index != -1) {
            val existingStudent = studentList[index]
            existingStudent.apply {
                this.name = name
                this.phone = phone
                this.address = address
                this.checked = checked
            }
            adapter.notifyItemChanged(index)
            Log.d("MainActivity", "Updated student: $existingStudent")
        } else {
            val newStudent = Student(name, id, phone, address, checked, R.drawable.ic_student)
            studentList.add(newStudent)
            adapter.notifyItemInserted(studentList.size - 1)
            Log.d("MainActivity", "Added new student: $newStudent")
        }
    }

    private fun handleStudentDelete(data: Intent?) {
        val id = data?.getStringExtra("id") ?: return
        val index = studentList.indexOfFirst { it.id == id }

        if (index != -1) {
            studentList.removeAt(index)
            adapter.notifyItemRemoved(index)
            adapter.notifyItemRangeChanged(index, studentList.size)
            Log.d("MainActivity", "Deleted student with ID: $id")
            Toast.makeText(this, "Student deleted successfully", Toast.LENGTH_SHORT).show()
        } else {
            Log.d("MainActivity", "Student with ID: $id not found")
            Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show()
        }
    }
}