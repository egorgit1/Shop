package com.example.deb3.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.deb3.models.AdminInDB
import com.example.deb3.models.SpecialityInDB
import com.example.deb3.models.StudentInDB
import com.example.deb3.models.UniversityInDB
import com.example.deb3.models.UserInDB

@Dao
interface Dao {
    @Insert
    fun insertUser(user: UserInDB)
    @Insert
    fun insertAdmin(adminInDB: AdminInDB)
    @Insert
    fun insertSpeciality(specialityInDB: SpecialityInDB)
    @Insert
    fun insertUniversity(universityInDB: UniversityInDB)
    @Insert
    fun insertStudent(student: StudentInDB)
    @Query("SELECT * FROM user")
    fun getListOfUsers(): List<UserInDB>
    @Query("SELECT * FROM student")
    fun getListOfStudents(): List<StudentInDB>
    @Query("SELECT * FROM speciality")
    fun getListOfSpecialities(): List<SpecialityInDB>
    @Query("SELECT * FROM admin")
    fun getAdmin(): AdminInDB
    @Query("DELETE FROM user WHERE passportId = :passportId")
    fun deleteUserByPassportId(passportId: String)
    @Query("DELETE FROM student WHERE passportId = :passportId")
    fun deleteStudentByPassportId(passportId: String)
}