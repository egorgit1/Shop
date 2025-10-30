package com.example.deb3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.example.deb3.R
import com.example.deb3.models.StudentInDB
import com.example.deb3.database.UniversityDataBase
import com.example.deb3.models.UserInDB
import com.example.deb3.databinding.ActivityAddStudentBinding
import kotlin.concurrent.thread

class AddStudentActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val specialities = resources.getStringArray(R.array.specialities)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, specialities)
        binding.atv.setAdapter(arrayAdapter)
    }

    override fun onResume() {
        super.onResume()
        val db = UniversityDataBase.getDataBase(this)
        lateinit var listOfStudents : List<StudentInDB>
        lateinit var listOfUsers : List<UserInDB>
        thread {
            listOfStudents = db.getDao().getListOfStudents()
            listOfUsers = db.getDao().getListOfUsers()
        }
/*
        val surnames = listOf("Сергеев","Петров","Михайлов","Зуенок","Гранкин","Горбач",
            "Ничипорчик","Индерейкин","Шимановский","Лычковский","Сидоров")
        val stringOfMensNames ="Александр Дмитрий Максим Сергей Андрей Алексей Артём Илья Кирилл Михаил Матвей Роман Егор Арсений Иван Денис Евгений Тимофей Владислав Игорь Владимир Павел Руслан Марк Константин Тимур Олег Ярослав Антон Николай Данил"
        val names:List<String> = stringOfMensNames.split(" ")
        val stringOfMensPatr = "Александрович Алексеевич Анатольевич Андреевич Антонович Аркадьевич Борисович Валентинович Валериевич Васильевич Викторович Владимирович Вячеславович Геннадиевич Георгиевич Григорьевич Данилович Дмитриевич"
        val patronymics = stringOfMensPatr.split(" ")
        val specialities = resources.getStringArray(R.array.specialities)
        val passports = listOf("КВ","КК","ХБ","МП")
        val list1 = makeStudents(10,88,passports,surnames,names,patronymics,randomPassword(),specialities)
        thread{
            list1.forEach {
                db.getDao().insertStudent(it)
            }
        }

        val univ = UniversityInDB(
            0,
            "Университет",
            "Ул.Пушкина, д.17",
            5,
            "Университет был построен в 1990 году. Прием на дневную очную форму обучения осуществляется по 5 специальностям."
        )

        val spec1 = SpecialityInDB(
            0,
            "Математика",
            "Во время обучения студенты осваивают разделы высшей математики, языки программирования, а также проходят подготовку по отдельному блоку дисциплин, включающего психологию, педагогику, методику преподавания математики и информатики.")
        val spec2 = SpecialityInDB(
            0,
            "Экономика",
            "Подготовку по специальности «Экономика» осуществляет кафедра аналитической экономики экономического факультета БГУ. Учебный план специальности аналогичен лучшим зарубежным программам подготовки экономистов-аналитиков, которых отличает сочетание глубоких системных знаний с умением применять экономические знания на практике."
        )
        val spec3 = SpecialityInDB(
            0,
            "Информатика",
            "Подготовка высококвалифицированных специалистов осуществляется благодаря мощной математической подготовке как в области классической, так и дискретной математики, с одной стороны, так и в области информационных технологий, с другой."
        )
        val spec4 = SpecialityInDB(
            0,
            "Инженерия",
            "Подготовка специалистов ориентирована на ядерно-энергетическую отрасль.\n" +
                    "Студентами глубоко изучается устройство и функционирование ядерных реакторов, взаимодействие ионизирующего излучения с веществом, принципы радиационной защиты."
        )
        val spec5 = SpecialityInDB(
            0,
            "Филология",
            "Основная задача - подготовка к пониманию, интерпретации и анализу текстов, а также изучению языковых особенностей и их изменений во времени."
        )
        val admin = AdminInDB(
            "КК00",
            "Ермаков",
            "Егор",
            "Михайлович",
            "егор05",
            "admin"
        )
        thread{
            db.getDao().insertAdmin(admin)
            db.getDao().insertSpeciality(spec1)
            db.getDao().insertSpeciality(spec2)
            db.getDao().insertSpeciality(spec3)
            db.getDao().insertSpeciality(spec4)
            db.getDao().insertSpeciality(spec5)
            db.getDao().insertUniversity(univ)

        }
*/
        binding.bAddUser.setOnClickListener {
            if (!isPassportUnique(listOfStudents,listOfUsers, binding.edPassport.text.toString())
                ||makePassportIdCorrect(binding.edPassport.text.toString())==null)
                Toast.makeText(
                    this@AddStudentActivity,
                    resources.getString(R.string.warning_for_passport),
                    Toast.LENGTH_SHORT
                ).show()
            else if (!isFieldEmpty()){
                val student = StudentInDB(
                    binding.edPassport.text.toString(),
                    binding.edSurname.text.toString(),
                    binding.edName.text.toString(),
                    binding.edPatronymic.text.toString(),
                    binding.edResult.text.toString().toInt(),
                    binding.atv.text.toString()
                )
                thread {
                    db.getDao().insertStudent(student)
                }
                finish()
            }
        }

    }


    private fun isPassportUnique(listStudents: List<StudentInDB>, listUsers:List<UserInDB>, passportId:String):Boolean{
        var argument = true
        listStudents.forEach{
            if(it.passportId.lowercase() == passportId.lowercase())
                argument = false
        }
        listUsers.forEach{
            if(it.passportId.lowercase() == passportId.lowercase())
                argument = false
        }
        if(resources.getString(R.string.admin_passport_id).lowercase()==passportId.lowercase())
            argument = false
        return argument
    }
/*
    fun makeStudents(first:Int,second:Int, listpasp:List<String>,listsur:List<String>, listnam:List<String>, listpatr:List<String>,result:Int,listspec:Array<String>):ArrayList<StudentInDB>{
        val list =ArrayList<StudentInDB>()
        lateinit var student:StudentInDB
        for (i in first..second){
            student= StudentInDB(
                listpasp.random()+"$i",
                listsur.random(),
                listnam.random(),
                listpatr.random(),
                randomPassword(),
                listspec.random()
            )
            list.add(student)
        }
        return list
    }

    fun randomPassword():Int{
        return (280..400).random()
    }


 */
    private fun makePassportIdCorrect(message:String):String?{
        val newmessage = message.trim().replace(" ", "").uppercase()
        return if (newmessage.length != 4)
            null
        else
            newmessage
    }

    private fun isFieldEmpty(): Boolean {
            binding.apply {
                warning(edPassport)
                warning(edSurname)
                warning(edName)
                warning(edPatronymic)
                warning(edResult)
                return warning(edSurname)
                        || warning(edName)
                        || warning(edPatronymic)
                        || warning(edPassport)
                        || warning(edResult)
            }
    }
    private fun warning(edit: EditText):Boolean{
        if(edit.text.isNullOrEmpty()) edit.error = resources.getString(R.string.warning_for_ed)
        return edit.text.isNullOrEmpty()
    }

}