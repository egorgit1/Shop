package com.example.deb3.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity("university")
data class UniversityInDB(
    @PrimaryKey
    var id:Int,
    @ColumnInfo("name")
    var name:String,
    @ColumnInfo("place")
    var place:String,
    @ColumnInfo("amount_specialities")
    var amountOfSpecialities:Int,
    @ColumnInfo("information")
    var information:String
): Serializable
