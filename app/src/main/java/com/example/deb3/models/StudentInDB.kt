package com.example.deb3.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity("student")
data class StudentInDB(
    @PrimaryKey
    var passportId:String,
    @ColumnInfo("surname")
    var surname:String,
    @ColumnInfo("name")
    var name:String,
    @ColumnInfo("patronymic")
    var patronymic:String,
    @ColumnInfo("result")
    var result:Int,
    @ColumnInfo("speciality")
    var speciality: String
): Serializable
