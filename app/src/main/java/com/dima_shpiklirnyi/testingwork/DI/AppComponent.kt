package com.dima_shpiklirnyi.testingwork.DI

import com.dima_shpiklirnyi.testingwork.MainActivity
import com.dima_shpiklirnyi.testingwork.Screens.NotesList.NotesListFragment
import com.dima_shpiklirnyi.testingwork.Screens.ViewAndEditNotes.ViewAndEditNotesFragment
import com.dima_shpiklirnyi.testingwork.domain.ViewModel.NotesListViewModelFactory
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject (notesListViewModelFactory: NotesListViewModelFactory)
    fun inject (notesListFragment: NotesListFragment)
    fun inject (mainActivity: MainActivity)
    fun inject (viewAndEditNotesFragment: ViewAndEditNotesFragment)
}