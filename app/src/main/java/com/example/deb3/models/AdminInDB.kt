package com.example.deb3.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity("admin")
data class AdminInDB(
    @PrimaryKey
    var passportId:String,
    @ColumnInfo("surname")
    var surname:String,
    @ColumnInfo("name")
    var name:String,
    @ColumnInfo("patronymic")
    var patronymic:String,
    @ColumnInfo("password")
    var password:String,
    @ColumnInfo("role")
    var role:String
): Serializable
