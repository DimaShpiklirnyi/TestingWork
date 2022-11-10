package com.dima_shpiklirnyi.testingwork.domain.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dima_shpiklirnyi.testingwork.MAIN
import com.dima_shpiklirnyi.testingwork.Repository.FirebaseServer.FireBaseRepository
import com.dima_shpiklirnyi.testingwork.Repository.NotesRoomDatabase
import com.dima_shpiklirnyi.testingwork.Repository.RoomDb.RoomRepository.NotesRepositoryRealization
import com.dima_shpiklirnyi.testingwork.domain.UseCase.Time

class NotesListViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val time by lazy { Time() }
    private val firebase by lazy { FireBaseRepository() }
    private val daoNotes by lazy { NotesRoomDatabase.getInstance(MAIN).getNotesDao() }
    private val realization by lazy { NotesRepositoryRealization(daoNotes) }


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesListViewModel(time, firebase, realization) as T
    }
}