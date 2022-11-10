package com.dima_shpiklirnyi.testingwork.Repository.RoomDb.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dima_shpiklirnyi.testingwork.Models.NotesModel

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (notesModel: NotesModel)

    @Delete
    suspend fun delete (notesModel: NotesModel)

    @Query ("SELECT * FROM notes_table")
    fun getAllNotes () : LiveData<List<NotesModel>>


}