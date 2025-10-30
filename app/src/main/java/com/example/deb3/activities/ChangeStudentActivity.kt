package com.example.deb3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import com.example.deb3.R
import com.example.deb3.models.StudentInDB
import com.example.deb3.database.UniversityDataBase
import com.example.deb3.databinding.ActivityChangeStudentBinding
import kotlin.concurrent.thread

class ChangeStudentActivity : AppCompatActivity() {

    lateinit var binding:ActivityChangeStudentBinding
    private lateinit var student: StudentInDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        student = intent.getSerializableExtra(resources.getString(R.string.key_for_user)) as StudentInDB
        binding.apply {
            edSurname.hint = student.surname
            edName.hint = student.name
            edPatronymic.hint = student.patronymic
            edResult.hint = student.result.toString()
        }
        val specialities = resources.getStringArray(R.array.specialities)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, specialities)
        binding.atv.setAdapter(arrayAdapter)
    }

    override fun onResume() {
        super.onResume()
        val db = UniversityDataBase.getDataBase(this)
        binding.bDelete.setOnClickListener {
            thread {
                db.getDao().deleteStudentByPassportId(student.passportId)
                finish()
            }
        }
        binding.bChangeStudent.setOnClickListener {
            if (!isFieldEmpty()) {
                val newstudent = StudentInDB(
                    student.passportId,
                    binding.edSurname.text.toString(),
                    binding.edName.text.toString(),
                    binding.edPatronymic.text.toString(),
                    binding.edResult.text.toString().toInt(),
                    binding.atv.text.toString()
                )
                thread {
                    db.getDao().deleteStudentByPassportId(student.passportId)
                    db.getDao().insertStudent(newstudent)
                }
                finish()
            }
        }
    }


    private fun isFieldEmpty(): Boolean {
        binding.apply {
            warning(edSurname)
            warning(edName)
            warning(edPatronymic)
            warning(edResult)
            return warning(edSurname)
                    || warning(edName)
                    || warning(edPatronymic)
                    || warning(edResult)
        }
    }
    private fun warning(edit: EditText):Boolean{
        if(edit.text.isNullOrEmpty()) edit.error = resources.getString(R.string.warning_for_ed)
        return edit.text.isNullOrEmpty()
    }
}