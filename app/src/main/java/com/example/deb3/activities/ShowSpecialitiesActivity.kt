package com.example.deb3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deb3.adapters.SpecialityAdapter
import com.example.deb3.models.SpecialityInDB
import com.example.deb3.database.UniversityDataBase
import com.example.deb3.databinding.ActivityShowSpecialitiesBinding
import kotlin.concurrent.thread

class ShowSpecialitiesActivity : AppCompatActivity() {

    lateinit var binding: ActivityShowSpecialitiesBinding
    val adapter = SpecialityAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowSpecialitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rcView.adapter = adapter
        binding.rcView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        val db = UniversityDataBase.getDataBase(this)
        lateinit var list:List<SpecialityInDB>
        thread{
            list = db.getDao().getListOfSpecialities()
            adapter.addAllSpecialities(list)
        }
    }
}