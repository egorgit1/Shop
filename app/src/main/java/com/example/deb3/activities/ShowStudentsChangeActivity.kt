package com.example.deb3.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deb3.R
import com.example.deb3.adapters.StudentAdapter
import com.example.deb3.models.StudentInDB
import com.example.deb3.database.UniversityDataBase
import com.example.deb3.databinding.ActivityShowStudentsChangeBinding
import java.io.Serializable
import kotlin.concurrent.thread

class ShowStudentsChangeActivity : AppCompatActivity(), StudentAdapter.Listener {

    lateinit var conditions: Array<String>
    lateinit var binding: ActivityShowStudentsChangeBinding
    val adapter = StudentAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowStudentsChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conditions = resources.getStringArray(R.array.conditions)
        conditions.plus("Специальность")
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, conditions)
        binding.atv.setAdapter(arrayAdapter)
    }

    override fun onResume() {
        super.onResume()
        binding.rcView.adapter = adapter
        binding.rcView.layoutManager = LinearLayoutManager(this)
        val db = UniversityDataBase.getDataBase(this)
        lateinit var listOfStudents: List<StudentInDB>
        thread {
            listOfStudents = db.getDao().getListOfStudents()
        }
        binding.bSearch.setOnClickListener{
            adapter.clear()
            if (makeSearchCorrect(binding.edSearch.text.toString()) == null)
                Toast.makeText(
                    this@ShowStudentsChangeActivity,
                    resources.getString(R.string.warning_for_search),
                    Toast.LENGTH_SHORT
                ).show()
            else {
                when(binding.atv.text.toString()){
                    conditions[0] ->{
                        listOfStudents.forEach {
                            if(binding.edSearch.text.toString().lowercase() in it.surname.lowercase())
                                adapter.addStudent(it)
                        }
                    }

                    conditions[1] ->{
                        listOfStudents.forEach {
                            if(binding.edSearch.text.toString().lowercase() in it.name.lowercase())
                                adapter.addStudent(it)
                        }
                    }

                    conditions[2] ->{
                        listOfStudents.forEach {
                            if(binding.edSearch.text.toString().lowercase() in it.patronymic.lowercase())
                                adapter.addStudent(it)
                        }
                    }

                    conditions[3] -> {
                        listOfStudents.forEach {
                            if(binding.edSearch.text.toString().lowercase() in it.passportId.lowercase())
                                adapter.addStudent(it)
                        }
                    }
                    conditions[4] -> {
                        listOfStudents.forEach {
                            if(binding.edSearch.text.toString().lowercase() in it.speciality.lowercase())
                                adapter.addStudent(it)
                        }
                    }
                }
            }
        }

    }

    override fun onClick(student: StudentInDB) {
        startActivity(Intent(this,ChangeStudentActivity::class.java).apply{
            putExtra(resources.getString(R.string.key_for_user),student as Serializable)
        })
    }

    private fun makeSearchCorrect(message: String): String? {
        val newmessage = message.trim().replace(" ", "")
        return if (newmessage.length < 2)
            null
        else
            newmessage
    }
}