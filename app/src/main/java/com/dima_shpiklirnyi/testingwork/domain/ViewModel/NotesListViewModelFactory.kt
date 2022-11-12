package com.dima_shpiklirnyi.testingwork.domain.ViewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dima_shpiklirnyi.testingwork.App.APP
import com.dima_shpiklirnyi.testingwork.DI.AppComponent
import com.dima_shpiklirnyi.testingwork.DI.DaggerAppComponent
import com.dima_shpiklirnyi.testingwork.MAIN
import com.dima_shpiklirnyi.testingwork.R
import com.dima_shpiklirnyi.testingwork.Repository.FirebaseServer.FireBaseRepository
import com.dima_shpiklirnyi.testingwork.Repository.NotesRoomDatabase
import com.dima_shpiklirnyi.testingwork.Repository.RoomDb.RoomRepository.NotesRepositoryRealization
import com.dima_shpiklirnyi.testingwork.appCompanent
import com.dima_shpiklirnyi.testingwork.domain.Interfaces.FireBase
import com.dima_shpiklirnyi.testingwork.domain.UseCase.Time
import javax.inject.Inject

class NotesListViewModelFactory(
    context: Context, val time: Time,
    val firebase: FireBaseRepository, val realization:NotesRepositoryRealization
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesListViewModel(time, firebase, realization) as T
    }
}