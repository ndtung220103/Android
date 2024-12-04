package vn.edu.hust.studentman

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("StudentPrefs", Context.MODE_PRIVATE)

    private val gson = Gson()

    // Hàm lưu danh sách sinh viên
    fun saveStudents(students: List<StudentModel>) {
        val jsonString = gson.toJson(students)
        sharedPreferences.edit().putString("students_key", jsonString).apply()
    }

    // Hàm lấy danh sách sinh viên
    fun getStudents(): MutableList<StudentModel> {
        val jsonString = sharedPreferences.getString("students_key", null)
        return if (jsonString != null) {
            val type = object : TypeToken<MutableList<StudentModel>>() {}.type
            gson.fromJson(jsonString, type)
        } else {
            mutableListOf()
        }
    }
}
