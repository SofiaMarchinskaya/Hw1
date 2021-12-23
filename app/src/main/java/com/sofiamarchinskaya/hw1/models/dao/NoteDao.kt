package com.sofiamarchinskaya.hw1.models.dao

import androidx.room.*
import com.sofiamarchinskaya.hw1.models.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    suspend fun getAll(): List<Note>

    @Query("SELECT * FROM notes WHERE id=:id")
    suspend fun findById(id: Long): Note

    @Insert
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note)

}