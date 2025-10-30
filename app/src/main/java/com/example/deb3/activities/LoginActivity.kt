package com.example.deb3.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.deb3.models.AdminInDB
import com.example.deb3.R
import com.example.deb3.database.UniversityDataBase
import com.example.deb3.models.UserInDB
import com.example.deb3.databinding.ActivityLoginBinding
import java.io.Serializable
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var admin: AdminInDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        val db = UniversityDataBase.getDataBase(this)
        lateinit var listOfUsers: List<UserInDB>
        thread {
            admin = db.getDao().getAdmin()
            listOfUsers = db.getDao().getListOfUsers()
        }
        lateinit var user: UserInDB
        binding.bAddUser.setOnClickListener {
            if (!isFieldEmpty()) {
                user = isDataMatched(
                    listOfUsers,
                    admin,
                    binding.edPassport.text.toString(),
                    binding.edPassword.text.toString()
                )
                if (user.passportId == "0")
                    Toast.makeText(
                        this@LoginActivity,
                        resources.getString(R.string.warning_for_login),
                        Toast.LENGTH_SHORT
                    ).show()
                else {
                    val intent = Intent()
                    intent.putExtra(getString(R.string.key_for_user), user as Serializable)
                    if(user.role == resources.getString(R.string.key_for_user))
                        intent.putExtra(resources.getString(R.string.key_for_result),resources.getString(R.string.key_for_user))
                    else if(user.role == resources.getString(R.string.key_for_admin))
                        intent.putExtra(resources.getString(R.string.key_for_result),resources.getString(R.string.key_for_admin))
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
        }
    }



    private fun isDataMatched(list: List<UserInDB>, admin: AdminInDB, login:String, password:String): UserInDB {
        list.forEach {
                if(it.passportId == login && it.password==password) {
                    return it
                }
        }
        return if(admin.passportId == login && admin.password == password){
            UserInDB(
                admin.passportId,
                admin.surname,
                admin.name,
                admin.patronymic,
                admin.password,
                resources.getString(R.string.key_for_admin)
            )
        } else UserInDB("0",
            "0",
            "0",
            "0",
            "0",
            "0")
    }
    private fun isFieldEmpty(): Boolean{
        binding.apply {
            warning(edPassport)||warning(edPassword)
            return warning(edPassport)||warning(edPassword)
        }
    }
    private fun warning(edit: EditText):Boolean{
        if(edit.text.isNullOrEmpty()) edit.error = resources.getString(R.string.warning_for_ed)
        return edit.text.isNullOrEmpty()
    }
}