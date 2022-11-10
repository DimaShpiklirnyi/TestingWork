package com.dima_shpiklirnyi.testingwork.Repository.RoomDb.RoomRepository

import androidx.lifecycle.LiveData
import com.dima_shpiklirnyi.testingwork.Models.NotesModel

interface NotesRepository {
    val allNotes: LiveData<List<NotesModel>>
    suspend fun insertNotes (notesModel: NotesModel)
    suspend fun deleteNotes (notesModel: NotesModel)
}