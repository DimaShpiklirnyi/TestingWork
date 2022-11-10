package com.dima_shpiklirnyi.testingwork.domain.Interfaces

import com.dima_shpiklirnyi.testingwork.Models.NotesModel

interface FireBase {
    fun getItems (limit:Int, function: (List<NotesModel>) -> Unit)
    fun addItem (notesModel: NotesModel)
    fun deleteItem (id : String)
    fun editItems (notesModel: NotesModel)
    fun getItemCount (function: (Int) -> Unit)
}