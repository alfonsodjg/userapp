package com.alfonso.usersapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alfonso.usersapp.data.local.dao.UserDao
import com.alfonso.usersapp.data.local.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDataBase: RoomDatabase() {
    abstract fun userDao(): UserDao
}