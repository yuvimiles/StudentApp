package com.example.studentsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import android.widget.CheckBox

class StudentAdapter(
    private val students: List<Student>,
    private val listener: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textViewName)
        val idTextView: TextView = itemView.findViewById(R.id.textViewId)
        val imageViewStudent: ImageView = itemView.findViewById(R.id.imageViewStudent)
        val checkboxChecked: CheckBox = itemView.findViewById(R.id.checkboxChecked)

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
                val intent = android.content.Intent(itemView.context, StudentDetailsActivity::class.java)
                intent.putExtra("name", student.name)
                intent.putExtra("id", student.id)
                intent.putExtra("phone", student.phone)
                intent.putExtra("address", student.address)
                intent.putExtra("imageResId", student.imageResId)
                intent.putExtra("checked", student.checked)
                itemView.context.startActivity(intent)
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