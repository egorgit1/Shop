package com.example.deb3.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.deb3.R
import com.example.deb3.models.UserInDB
import com.example.deb3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var launcherToLogin: ActivityResultLauncher<Intent>? = null
    lateinit var user: UserInDB
    var loginResult:String? =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showMenuAsStudent()
        launcherToLogin =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    loginResult =
                        result.data?.getStringExtra(resources.getString(R.string.key_for_result))
                    user =
                        result.data?.getSerializableExtra(resources.getString(R.string.key_for_user)) as UserInDB
                    val text = "${user.surname} ${user.name[0]}. ${user.patronymic[0]}."
                    binding.tvToLogin.text = text
                    binding.imToLogin.setImageResource(R.drawable.ic_logged)
                    if(user.role == resources.getString(R.string.key_for_user)){
                        showMenuAsUser()
                    }
                    else if(user.role == resources.getString(R.string.key_for_admin))
                        showMenuAsAdmin()
                }
            }
    }

    override fun onResume() {
        super.onResume()
        binding.imToLogin.setOnClickListener{
            launcherToLogin?.launch(Intent(this@MainActivity, LoginActivity::class.java))
        }
        binding.bAddUser.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddUserActivity::class.java))
        }
        binding.bAddStudent.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddStudentActivity::class.java))
        }
        binding.bWatchDbUsers.setOnClickListener {
            startActivity(Intent(this@MainActivity, ShowUsersActivity::class.java))
        }
        binding.bWatchSpecialities.setOnClickListener {
            startActivity(Intent(this, ShowSpecialitiesActivity::class.java))
        }
        binding.ChangeUser.setOnClickListener {
            startActivity(Intent(this,ShowUsersChangeActivity::class.java))
        }
        binding.bChangeStudent.setOnClickListener {
            startActivity(Intent(this,ShowStudentsChangeActivity::class.java))
        }
        binding.bWatchDbStudents.setOnClickListener {
            startActivity(Intent(this,ShowStudentsActivity::class.java))
        }
    }

    fun showMenuAsStudent(){
        binding.tvMain.text = resources.getString(R.string.menu_student)
        binding.bChangeStudent.visibility = View.GONE
        binding.bAddStudent.visibility = View.GONE
        binding.bWatchDbUsers.visibility = View.GONE
        binding.ChangeUser.visibility = View.GONE
        binding.ChangeUser.visibility = View.GONE
        binding.bAddUser.visibility = View.GONE
    }

    fun showMenuAsUser(){
        binding.tvMain.text = resources.getString(R.string.menu_user)
        binding.bChangeStudent.visibility = View.VISIBLE
        binding.bAddStudent.visibility = View.VISIBLE
        binding.bWatchDbUsers.visibility = View.VISIBLE
        binding.ChangeUser.visibility = View.VISIBLE
        binding.ChangeUser.visibility = View.GONE
        binding.bAddUser.visibility = View.GONE
    }

    fun showMenuAsAdmin(){
        binding.tvMain.text = resources.getString(R.string.menu_admin)
        binding.bChangeStudent.visibility = View.VISIBLE
        binding.bAddStudent.visibility = View.VISIBLE
        binding.bWatchDbUsers.visibility = View.VISIBLE
        binding.ChangeUser.visibility = View.VISIBLE
        binding.ChangeUser.visibility = View.VISIBLE
        binding.bAddUser.visibility = View.VISIBLE
    }

}
