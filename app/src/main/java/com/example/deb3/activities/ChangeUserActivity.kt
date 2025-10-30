package com.example.deb3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.deb3.R
import com.example.deb3.database.UniversityDataBase
import com.example.deb3.models.UserInDB
import com.example.deb3.databinding.ActivityChangeUserBinding
import kotlin.concurrent.thread

class ChangeUserActivity : AppCompatActivity() {

    lateinit var binding :ActivityChangeUserBinding
    lateinit var user: UserInDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = intent.getSerializableExtra(resources.getString(R.string.key_for_user)) as UserInDB
        binding.apply{
            edSurname.hint = user.surname
            edName.hint = user.name
            edPatronymic.hint = user.patronymic
            edPassword.hint = user.password
        }
    }

    override fun onResume() {
        super.onResume()
        val db = UniversityDataBase.getDataBase(this)
        binding.bDelete.setOnClickListener {
            thread {
                db.getDao().deleteUserByPassportId(user.passportId)
                finish()
            }
        }
        binding.bChangeUser.setOnClickListener {
            if(!isFieldEmpty()){
                val newuser = UserInDB(
                    user.passportId,
                    binding.edSurname.text.toString(),
                    binding.edName.text.toString(),
                    binding.edPatronymic.text.toString(),
                    binding.edPassword.text.toString(),
                    user.role
                )
                thread{
                    db.getDao().deleteUserByPassportId(user.passportId)
                    db.getDao().insertUser(newuser)
                }
                finish()
            }
        }
    }


    private fun isFieldEmpty(): Boolean{
        binding.apply {
            warning(edSurname)
            warning(edName)
            warning(edPatronymic)
            warning(edPassword)
            return warning(edSurname)||warning(edName)||warning(edPatronymic)||warning(edPassword)
        }
    }
    private fun warning(edit: EditText):Boolean{
        if(edit.text.isNullOrEmpty()) edit.error = resources.getString(R.string.warning_for_ed)
        return edit.text.isNullOrEmpty()
    }
}

