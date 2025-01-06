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
    private val onStudentClick: (Student) -> Unit // Callback לטיפול במעבר ל-Activity
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

            // עדכון הסטטוס של checked בעת לחיצה על ה-Checkbox
            checkboxChecked.setOnCheckedChangeListener { _, isChecked ->
                student.checked = isChecked
            }

            // לחיצה על כל הפריט למעבר לעמוד פרטי הסטודנט
            itemView.setOnClickListener {
                onStudentClick(student) // קריאה ל-Callback עם אובייקט הסטודנט
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
