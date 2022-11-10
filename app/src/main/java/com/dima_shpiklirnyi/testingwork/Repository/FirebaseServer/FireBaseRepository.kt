package com.dima_shpiklirnyi.testingwork.Repository.FirebaseServer

import com.dima_shpiklirnyi.testingwork.Models.NotesModel
import com.dima_shpiklirnyi.testingwork.domain.Interfaces.FireBase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class FireBaseRepository : FireBase {

    override fun getItems(limit: Int, function: (List<NotesModel>) -> Unit) {
        REF_DATABASE_ROOT.child(NODE_TESTING_WORK).orderByChild("time").limitToLast(limit+1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val allNotes = snapshot.children.map {
                        it.getValue(NotesModel::class.java)!!
                    }
                    function(allNotes)
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }


    override fun addItem(notesModel: NotesModel) {
        val map = hashMapOf<String, Any>()
        map["title"] = notesModel.title
        map["description"] = notesModel.description
        map["id"] = notesModel.id
        map["time"] = notesModel.time
        REF_DATABASE_ROOT.child(NODE_TESTING_WORK).child(notesModel.id).updateChildren(map)
    }

    override fun deleteItem(id: String) {
        REF_DATABASE_ROOT.child(NODE_TESTING_WORK).child(id).setValue(null)
    }

    override fun editItems(notesModel: NotesModel) {
        val map = hashMapOf<String, Any>()
        map["title"] = notesModel.title
        map["description"] = notesModel.description
        map["id"] = notesModel.id
        map["time"] = notesModel.time
        REF_DATABASE_ROOT.child(NODE_TESTING_WORK).child(notesModel.id).updateChildren(map)
    }

    override fun getItemCount(function:(Int) -> Unit) {
        REF_DATABASE_ROOT.child(NODE_TESTING_WORK)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val itemCount = snapshot.childrenCount.toInt()
                    function (itemCount)
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
    }
}

