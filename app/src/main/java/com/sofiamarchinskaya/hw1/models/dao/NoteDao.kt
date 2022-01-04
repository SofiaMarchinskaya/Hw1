package com.sofiamarchinskaya.hw1.models.dao

import androidx.room.*
import com.sofiamarchinskaya.hw1.models.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAll(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id=:id")
    suspend fun findById(id: Long): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note): Long

}