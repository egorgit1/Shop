package com.example.deb3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.deb3.R
import com.example.deb3.models.StudentInDB
import com.example.deb3.database.UniversityDataBase
import com.example.deb3.models.UserInDB
import com.example.deb3.databinding.ActivityAddUserBinding
import com.example.deb3.models.AdminInDB
import kotlin.concurrent.thread


class AddUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        val list1 = makeStudents(89,99,passports,surnames,names,patronymics)
        thread{
            list1.forEach {
                db.getDao().insertUser(it)
            }
        }

*/




        binding.bAddUser.setOnClickListener {
            if (!isPassportUnique(listOfStudents,listOfUsers, binding.edPassport.text.toString())
                ||makePassportIdCorrect(binding.edPassport.text.toString())==null)
                Toast.makeText(
                    this@AddUserActivity,
                    resources.getString(R.string.warning_for_passport),
                    Toast.LENGTH_SHORT
                ).show()
            else if (!isFieldEmpty()) {
                val user = UserInDB(
                    binding.edPassport.text.toString(),
                    binding.edSurname.text.toString(),
                    binding.edName.text.toString(),
                    binding.edPatronymic.text.toString(),
                    binding.edPassword.text.toString(),
                    resources.getString(R.string.key_for_user)
                )
                thread {
                    db.getDao().insertUser(user)
                }
                finish()
            }
        }

    }
/*
    fun makeStudents(first:Int,second:Int, listpasp:List<String>,listsur:List<String>, listnam:List<String>, listpatr:List<String>):ArrayList<UserInDB>{
        val list =ArrayList<UserInDB>()
        lateinit var student: UserInDB
        for (i in first..second){
            student= UserInDB(
                listpasp.random()+"$i",
                listsur.random(),
                listnam.random(),
                listpatr.random(),
                randomPassword().toString(),
                resources.getString(R.string.key_for_user)
            )
            list.add(student)
        }
        return list
    }

    fun randomPassword():Int{
        return (100000..999999).random()
    }
*/




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

    private fun makePassportIdCorrect(message:String):String?{
        val newmessage = message.trim().replace(" ", "").uppercase()
        return if (newmessage.length != 4)
            null
        else
            newmessage
    }

    private fun isFieldEmpty(): Boolean{
        binding.apply {
            warning(edPassport)
            warning(edSurname)
            warning(edName)
            warning(edPatronymic)
            warning(edPassword)
            return warning(edSurname)||warning(edName)||warning(edPatronymic)||warning(edPassport)||warning(edPassword)
        }
    }
    private fun warning(edit: EditText):Boolean{
        if(edit.text.isNullOrEmpty()) edit.error = resources.getString(R.string.warning_for_ed)
        return edit.text.isNullOrEmpty()
    }
}