package com.example.deb3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.deb3.models.AdminInDB
import com.example.deb3.models.SpecialityInDB
import com.example.deb3.models.StudentInDB
import com.example.deb3.models.UniversityInDB
import com.example.deb3.models.UserInDB

@Database(
    entities = [UserInDB::class,
    StudentInDB::class,
    SpecialityInDB::class,
    UniversityInDB::class,
    AdminInDB::class],
    version = 1
)
abstract class UniversityDataBase: RoomDatabase() {
    abstract fun getDao() : Dao

    companion object{
        fun getDataBase(context: Context): UniversityDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                UniversityDataBase::class.java,
                "university_course2"
            ).build()
        }
    }
}