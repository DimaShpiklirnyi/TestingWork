package com.dima_shpiklirnyi.testingwork.Repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.dima_shpiklirnyi.testingwork.Models.NotesModel
import androidx.room.RoomDatabase
import com.dima_shpiklirnyi.testingwork.Repository.RoomDb.Dao.NotesDao
import javax.inject.Inject

@Database(entities = [NotesModel::class], version = 1)
abstract class NotesRoomDatabase  : RoomDatabase() {

abstract fun getNotesDao() : NotesDao

companion object{
    private var database : NotesRoomDatabase ?= null
    fun getInstance(context : Context): NotesRoomDatabase{
        return if(database == null) {
            database = Room.databaseBuilder(context, NotesRoomDatabase::class.java, "db").build()
            database as NotesRoomDatabase
        }
        else {
            database as NotesRoomDatabase
        }
    }
}
}