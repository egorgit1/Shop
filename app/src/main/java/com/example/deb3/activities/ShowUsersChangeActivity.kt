package com.example.deb3.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deb3.R
import com.example.deb3.database.UniversityDataBase
import com.example.deb3.adapters.UserAdapter
import com.example.deb3.models.UserInDB
import com.example.deb3.databinding.ActivityShowUsersChangeBinding
import java.io.Serializable
import kotlin.concurrent.thread

class ShowUsersChangeActivity  : AppCompatActivity(), UserAdapter.Listener {

    lateinit var conditions: Array<String>
    lateinit var binding: ActivityShowUsersChangeBinding
    val adapter = UserAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowUsersChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conditions = resources.getStringArray(R.array.conditions)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, conditions)
        binding.atv.setAdapter(arrayAdapter)
    }

    override fun onResume() {
        super.onResume()
        binding.rcView.adapter = adapter
        binding.rcView.layoutManager = LinearLayoutManager(this)
        val db = UniversityDataBase.getDataBase(this)
        lateinit var listOfUsers: List<UserInDB>
        thread {
            listOfUsers = db.getDao().getListOfUsers()
        }
        binding.bSearch.setOnClickListener{
            adapter.clear()
            if (makeSearchCorrect(binding.edSearch.text.toString()) == null)
                Toast.makeText(
                    this@ShowUsersChangeActivity,
                    resources.getString(R.string.warning_for_search),
                    Toast.LENGTH_SHORT
                ).show()
            else {
                when(binding.atv.text.toString()){
                    conditions[0] ->{
                        listOfUsers.forEach {
                            if(binding.edSearch.text.toString().lowercase() in it.surname.lowercase())
                                adapter.addUser(it)
                        }
                    }

                    conditions[1] ->{
                        listOfUsers.forEach {
                            if(binding.edSearch.text.toString().lowercase() in it.name.lowercase())
                                adapter.addUser(it)
                        }
                    }

                    conditions[2] ->{
                        listOfUsers.forEach {
                            if(binding.edSearch.text.toString().lowercase() in it.patronymic.lowercase())
                                adapter.addUser(it)
                        }
                    }

                    conditions[3] -> {
                        listOfUsers.forEach {
                            if(binding.edSearch.text.toString().lowercase() in it.passportId.lowercase())
                                adapter.addUser(it)
                        }
                    }
                }
            }
        }

    }

    override fun onClick(user: UserInDB) {
        startActivity(Intent(this,ChangeUserActivity::class.java).apply{
            putExtra(resources.getString(R.string.key_for_user),user as Serializable)
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