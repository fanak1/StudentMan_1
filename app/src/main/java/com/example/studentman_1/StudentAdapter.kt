package com.example.studentman_1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class StudentAdapter(val students: MutableList<StudentModel>): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
        val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
        val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
        val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item,
            parent, false)
        return StudentViewHolder(itemView)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]

        holder.textStudentName.text = student.studentName
        holder.textStudentId.text = student.studentId

        holder.imageEdit.setOnClickListener {
            val dialog = AddEditStudentDialog(holder.itemView.context, student) { updatedStudent ->
                students[position] = updatedStudent
                notifyItemChanged(position)
            }
            dialog.show()
        }

        holder.imageRemove.setOnClickListener {
            val context = holder.itemView.context
            AlertDialog.Builder(context)
                .setTitle("Xóa sinh viên")
                .setMessage("Bạn có chắc chắn muốn xóa ${student.studentName} không?")
                .setPositiveButton("Xóa") { _, _ ->
                    val removedStudent = students.removeAt(position)
                    notifyItemRemoved(position)

                    val rootView = (context as MainActivity).findViewById<View>(R.id.main)
                    Snackbar.make(rootView, "${removedStudent.studentName} đã bị xóa", Snackbar.LENGTH_LONG)
                        .setAction("Undo") {
                            students.add(position, removedStudent)
                            notifyItemInserted(position)
                        }.show()
                }
                .setNegativeButton("Hủy", null)
                .show()
        }
    }
}