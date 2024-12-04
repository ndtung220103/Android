package vn.edu.hust.studentman

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

class ListFragment : Fragment() {

    private val sharedViewModel: MyViewModel by activityViewModels()
    private lateinit var students: MutableList<StudentModel>
    private lateinit var studentAdapter : StudentAdapter
    var pos = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        students = sharedViewModel.students
        studentAdapter = StudentAdapter(students)

        val listview = view.findViewById<ListView>(R.id.list_view_students)
        listview.adapter = studentAdapter

        registerForContextMenu(listview)
        val pos = arguments?.getInt("param0")
        val newName = arguments?.getString("param1")
        val newMssv = arguments?.getString("param2")
        if(pos == -1){
            if (newName != null) {
                if (newMssv != null) {
                    if (newName.isNotEmpty() && newMssv.isNotEmpty()) {
//                        students.add(StudentModel(newName, newMssv))
                        sharedViewModel.addStudent(StudentModel(newName, newMssv))
                        studentAdapter.notifyDataSetChanged()
                    }
                }
            }
        }else{
            if (newName != null) {
                if (newMssv != null) {
                    if (newName.isNotEmpty() && newMssv.isNotEmpty()) {
//                        students[pos!!] = StudentModel(newName,newMssv)
                        if (pos != null) {
                            sharedViewModel.updateStudent(newMssv,newName,pos)
                        }
                        this.pos = -1
                        studentAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
        return view
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        requireActivity().menuInflater.inflate(R.menu.student_delete_adit,menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        pos = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        when(item.itemId){
            R.id.edit_student ->{
                val args = Bundle()
                args.putInt("param0",pos)
                args.putString("param1",students[pos].studentName )
                args.putString("param2", students[pos].studentId)
                findNavController().navigate(R.id.action_listFragment_to_studentFragment,args)
            }
            R.id.delete_student ->{
                val student = students[pos]
                context?.let {
                    AlertDialog.Builder(it)
                        .setTitle("Are you sure to delete ?")
                        .setMessage(students[pos].studentName)
                        .setPositiveButton("YES"){ dialogInterface: DialogInterface, i: Int ->
                            students.removeAt(pos)
                            studentAdapter.notifyDataSetChanged()
                            view?.let { it1 ->
                                Snackbar.make(it1.findViewById(R.id.list_view_students),"Student removed", Snackbar.LENGTH_LONG)
                                    .setAction("UNDO"){
                                        students.add(pos,student)
                                        studentAdapter.notifyDataSetChanged()
                                    }
                                    .show()
                            }

                        }
                        .setNegativeButton("CANCEL",null)
                        .show()
                }
            }
        }
        return super.onContextItemSelected(item)
    }


}