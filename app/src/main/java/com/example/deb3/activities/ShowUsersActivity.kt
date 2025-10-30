package com.example.deb3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deb3.R
import com.example.deb3.database.UniversityDataBase
import com.example.deb3.adapters.UserAdapter
import com.example.deb3.models.UserInDB
import com.example.deb3.databinding.ActivityShowUsersBinding
import kotlin.concurrent.thread

class ShowUsersActivity : AppCompatActivity(), UserAdapter.Listener {

    lateinit var conditions: Array<String>
    lateinit var binding: ActivityShowUsersBinding
    val adapter = UserAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowUsersBinding.inflate(layoutInflater)
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
            adapter.addAllUsers(listOfUsers)
        }
        binding.bSearch.setOnClickListener{
            if (makeSearchCorrect(binding.edSearch.text.toString()) == null)
                Toast.makeText(
                    this@ShowUsersActivity,
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
        Toast.makeText(this, "${user.surname} ${user.name} ${user.patronymic}", Toast.LENGTH_SHORT).show()
    }

    private fun makeSearchCorrect(message: String): String? {
        val newmessage = message.trim().replace(" ", "")
        return if (newmessage.length < 2)
            null
        else
            newmessage
    }
}