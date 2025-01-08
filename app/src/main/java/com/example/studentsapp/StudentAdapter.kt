package com.example.studentsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val students: List<Student>,
    private val onStudentClick: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewName)
        private val idTextView: TextView = itemView.findViewById(R.id.textViewId)
        private val imageViewStudent: ImageView = itemView.findViewById(R.id.imageViewStudent)
        private val checkboxChecked: CheckBox = itemView.findViewById(R.id.checkboxChecked)

        fun bind(student: Student) {
            nameTextView.text = student.name
            idTextView.text = student.id
            imageViewStudent.setImageResource(student.imageResId)
            checkboxChecked.isChecked = student.checked

            checkboxChecked.setOnCheckedChangeListener { _, isChecked ->
                student.checked = isChecked
            }

            itemView.setOnClickListener {
                onStudentClick(student)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(students[position])
    }

    override fun getItemCount(): Int = students.size
}