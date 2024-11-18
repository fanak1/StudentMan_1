package com.example.studentman_1

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddEditStudentDialog(
    context: Context,
    private val student: StudentModel?,
    private val onSave: (StudentModel) -> Unit
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_edit_student)

        val editName = findViewById<EditText>(R.id.edit_student_name)
        val editId = findViewById<EditText>(R.id.edit_student_id)
        val btnSave = findViewById<Button>(R.id.btn_save)
        val btnCancel = findViewById<Button>(R.id.btn_cancel)

        student?.let {
            editName.setText(it.studentName)
            editId.setText(it.studentId)
        }

        btnSave.setOnClickListener {
            val name = editName.text.toString()
            val id = editId.text.toString()
            if (name.isNotBlank() && id.isNotBlank()) {
                onSave(StudentModel(name, id))
                dismiss()
            } else {
                Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }
}
