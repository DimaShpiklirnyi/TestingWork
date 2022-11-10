package com.dima_shpiklirnyi.testingwork.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*
//Модель заметок
@Entity(tableName = "notes_table", indices = [Index(value = ["id"],  unique = true)])
data class NotesModel(
var title: String = "",
var time:Long = 0,
var description: String = "",
@ColumnInfo(name = "id")
var id: String = UUID.randomUUID().toString(),
@PrimaryKey(autoGenerate = true)
var idDb: Int? = null,
) : Serializable