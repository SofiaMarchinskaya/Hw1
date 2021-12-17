package com.sofiamarchinskaya.hw1.models.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sofiamarchinskaya.hw1.Constants
import com.sofiamarchinskaya.hw1.models.dao.NoteDao
import com.sofiamarchinskaya.hw1.models.entity.Note

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDataBase(): AppDatabase {
            return INSTANCE!!
        }

        fun createDataBase(context: Context){
            INSTANCE = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                Constants.DATABASE_NAME
            ).build()
        }
    }
}
