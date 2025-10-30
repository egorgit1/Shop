package com.example.deb3.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity("speciality")
data class SpecialityInDB(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo("name")
    var name:String,
    @ColumnInfo("information")
    var information:String
): Serializable
