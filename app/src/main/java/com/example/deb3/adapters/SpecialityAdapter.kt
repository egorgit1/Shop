package com.example.deb3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deb3.R
import com.example.deb3.databinding.SpecialityModelBinding
import com.example.deb3.models.SpecialityInDB

class SpecialityAdapter:RecyclerView.Adapter<SpecialityAdapter.SpecialityHolder>() {

    private val listOfSpecialities = ArrayList<SpecialityInDB>()

    class SpecialityHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = SpecialityModelBinding.bind(view)

        fun bind(spec: SpecialityInDB){
            val name = spec.name
            val info = spec.information
            binding.tvName.text= name
            binding.tvInfo.text = info
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialityHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.speciality_model,parent,false)
        return SpecialityHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfSpecialities.size
    }

    override fun onBindViewHolder(holder: SpecialityHolder, position: Int) {
        holder.bind(listOfSpecialities[position])
    }

    fun addAllSpecialities(list: List<SpecialityInDB>){
        listOfSpecialities.clear()
        listOfSpecialities.addAll(list)
        notifyDataSetChanged()
    }
}