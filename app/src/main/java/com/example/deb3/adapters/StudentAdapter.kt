package com.example.deb3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deb3.R
import com.example.deb3.databinding.StudentModelBinding
import com.example.deb3.models.StudentInDB

class StudentAdapter(val listener: Listener) : RecyclerView.Adapter<StudentAdapter.StudentHolder>(){

    private val listOfStudents =ArrayList<StudentInDB>()

    class StudentHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding= StudentModelBinding.bind(view)
        fun bind(student: StudentInDB, listener: Listener)=with(binding){
            val text = "${student.surname}  ${student.name} ${student.patronymic}"
            tvInitials.text = text
            tvPassport.text = student.passportId
            tvResult.text = student.result.toString()
            itemView.setOnClickListener{
                listener.onClick(student)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_model,parent,false)
        return StudentHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfStudents.size
    }

    override fun onBindViewHolder(holder: StudentHolder, position: Int) {
        holder.bind(listOfStudents[position], listener)
    }

    fun clear(){
        listOfStudents.clear()
        notifyDataSetChanged()
    }

    fun addStudent(student: StudentInDB){
        listOfStudents.add(student)
        notifyDataSetChanged()
    }

    fun addAllStudents(list : List<StudentInDB>){
        listOfStudents.clear()
        listOfStudents.addAll(list)
        notifyDataSetChanged()
    }

    interface Listener{
        fun onClick(student: StudentInDB)
    }
}