package com.dima_shpiklirnyi.testingwork.Repository.RoomDb.RoomRepository

import androidx.lifecycle.LiveData
import com.dima_shpiklirnyi.testingwork.Models.NotesModel
import com.dima_shpiklirnyi.testingwork.Repository.RoomDb.Dao.NotesDao
import javax.inject.Inject

class NotesRepositoryRealization @Inject constructor(val notesDao: NotesDao):NotesRepository
{
    override val allNotes: LiveData<List<NotesModel>>
        get() = notesDao.getAllNotes()

    override suspend fun insertNotes(notesModel: NotesModel) {
        notesDao.insert(notesModel)
    }

    override suspend fun deleteNotes(notesModel: NotesModel) {
        notesDao.delete(notesModel)
    }

}