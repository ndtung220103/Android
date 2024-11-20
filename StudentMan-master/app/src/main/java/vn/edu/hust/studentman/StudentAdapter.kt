package vn.edu.hust.studentman

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class  StudentAdapter(val students: MutableList<StudentModel>): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
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

    holder.imageEdit.setOnClickListener{
      val dialogview = LayoutInflater.from(holder.imageEdit.context)
        .inflate(R.layout.custom_dialog_layout,null)
      dialogview.findViewById<EditText>(R.id.name).setText(student.studentName.toString())
      dialogview.findViewById<EditText>(R.id.code).setText(student.studentId.toString())
      val name = dialogview.findViewById<EditText>(R.id.name)
      val code = dialogview.findViewById<EditText>(R.id.code)

      AlertDialog.Builder(holder.imageEdit.context)
        .setTitle("Change information")
        .setView(dialogview)
        .setPositiveButton("YES"){
            dialogInterface: DialogInterface, i: Int ->
          students[position].studentName = name.text.toString()
          students[position].studentId = code.text.toString()

          Toast.makeText(holder.imageEdit.context,"Information changed", Toast.LENGTH_LONG)
            .show()

          notifyDataSetChanged()
        }
        .setNegativeButton("CANCEL",null)
        .show()
    }

    holder.imageRemove.setOnClickListener{

      AlertDialog.Builder(holder.imageRemove.context)
        .setTitle("Are you sure to delete ?")
        .setMessage(student.studentName)
        .setPositiveButton("YES"){
            dialogInterface: DialogInterface, i: Int ->

          students.removeAt(position)
          notifyItemRemoved(position)

          Snackbar.make(holder.imageRemove,"Student removed", Snackbar.LENGTH_LONG)
            .setAction("UNDO"){
              students.add(position,student)
              notifyItemInserted(position)
            }
            .show()

        }
        .setNegativeButton("CANCEL",null)
        .show()
    }
  }
}