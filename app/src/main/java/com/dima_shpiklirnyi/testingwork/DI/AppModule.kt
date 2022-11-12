package com.dima_shpiklirnyi.testingwork.DI

import android.content.Context
import com.dima_shpiklirnyi.testingwork.Repository.FirebaseServer.FireBaseRepository
import com.dima_shpiklirnyi.testingwork.Repository.NotesRoomDatabase
import com.dima_shpiklirnyi.testingwork.Repository.RoomDb.Dao.NotesDao
import com.dima_shpiklirnyi.testingwork.Repository.RoomDb.RoomRepository.NotesRepositoryRealization
import com.dima_shpiklirnyi.testingwork.Screens.NotesList.NotesListFragment
import com.dima_shpiklirnyi.testingwork.domain.Interfaces.FireBase
import com.dima_shpiklirnyi.testingwork.domain.Interfaces.NoInternetFunc
import com.dima_shpiklirnyi.testingwork.domain.UseCase.ChekInternetConnection
import com.dima_shpiklirnyi.testingwork.domain.UseCase.Time
import com.dima_shpiklirnyi.testingwork.domain.ViewModel.NotesListViewModel
import com.dima_shpiklirnyi.testingwork.domain.ViewModel.NotesListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {

    @Provides
    fun provideContext(): Context{
        return context
    }

    @Provides
    fun privideTime():Time {
        return Time()
    }

    @Provides
    fun provideTime(fireBaseRepository: FireBaseRepository): FireBase {
        return fireBaseRepository
    }

    @Provides
    fun provideDaoNotes(notesRoomDatabase: NotesRoomDatabase): NotesDao {
        return notesRoomDatabase.getNotesDao()
}

    @Provides
    fun provideRealization(dao: NotesDao): NotesRepositoryRealization {
        return NotesRepositoryRealization(dao)
    }

    @Provides
    fun providesNotesListViewModelFactory(context: Context, notesDao: NotesDao): NotesListViewModelFactory{
       return NotesListViewModelFactory(context, time = Time(), firebase = FireBaseRepository(), realization = NotesRepositoryRealization(notesDao))
    }

    @Provides
    fun provideNotesRoom():NotesRoomDatabase{
        return NotesRoomDatabase.getInstance(context)
    }

    @Provides
    fun provideChekinternet(context: Context, notesListFragment: NotesListFragment) : ChekInternetConnection{
        return ChekInternetConnection(context, notesListFragment)
    }
    @Provides
    fun providenotesListFragment(notesListFragment: NotesListFragment):NoInternetFunc = notesListFragment

    @Provides
    fun provienotesList() : NotesListFragment{
        return NotesListFragment()
    }
    @Provides
    fun provideFirebase() : FireBaseRepository{
        return FireBaseRepository()
    }


}



