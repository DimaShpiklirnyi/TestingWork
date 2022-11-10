package com.dima_shpiklirnyi.testingwork.Repository.FirebaseServer

import com.google.firebase.database.FirebaseDatabase

fun initFirebase() {
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
}

