package com.dima_shpiklirnyi.testingwork.domain.ViewModel


import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dima_shpiklirnyi.testingwork.Models.NotesModel
import com.dima_shpiklirnyi.testingwork.Repository.RoomDb.RoomRepository.NotesRepositoryRealization
import com.dima_shpiklirnyi.testingwork.domain.Interfaces.FireBase
import com.dima_shpiklirnyi.testingwork.domain.UseCase.Time
import kotlinx.coroutines.*


class NotesListViewModel(
    val time: Time, val fireBase: FireBase,
    val roomRealization: NotesRepositoryRealization
) : ViewModel() {

    var liveDataDb = roomRealization.allNotes
    private  var tempArray = mutableListOf<NotesModel>()
    val liveDataList = MediatorLiveData<List<NotesModel>>()
    var isEmpty = MutableLiveData(false)
    var itemCountServer = MutableLiveData(0)
    var getItemServer = MutableLiveData(0)
    var stopGetServerData = MutableLiveData(false)
    var completeGet = MutableLiveData(false)

// Получение данных из Room
    init {
        liveDataList.addSource(liveDataDb) { liveDataList.value = it }
    }

// Количество обьектов на сервере
    fun serverItemCount(onSuccess: () -> Unit) {
        fireBase.getItemCount {
            itemCountServer.value = it
            if (it == 0 && liveDataList.value?.isEmpty() == true) isEmpty.value = true
            onSuccess()
        }
    }
// Запускам получение данных с сервера
    fun startReciveData(isStop: Boolean) {
        stopGetServerData.value = isStop
        if (getItemServer.value == 0 && completeGet.value == false) {
            serverItemCount { itemCountServer.value?.let { getServerToDatabase(it) } }
        }
    }

// Остановка передачи данных с сервера
    fun stopReciveData(isStop: Boolean) {
        stopGetServerData.value = isStop
        getItemServer.value = 0
    }

// Сохранение данных с сервера в базу данных. Задержка времени установлена для наглядности получения данных с сервера
    fun getServerToDatabase(limit: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            for (i in 1..limit) {
                fireBase.getItems(i) { list ->
                    addNotesToDb(list) {}
                }
                delay(2000)
                getItemServer.value = getItemServer.value?.plus(1)
                if (stopGetServerData.value == true) {
                    this.coroutineContext.job.cancel()
                    getItemServer.value = 0
                    break
                }
                if (limit == getItemServer.value) completeGet.value = true
            }
        }
    }

// Добавление новой заметки в массив и на сервер. В Room они попадут после перезапуска приложения. Имитанция получения данных с сервера
    fun addNewNotes() {
        val note = NotesModel("Новая заметка", time.getTime())
        liveDataList.value = liveDataList.value?.plus(note) ?: listOf(note)
        fireBase.addItem(note)
    }
// Удаление заметки из всех источников
    fun deleteItem(index: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            liveDataList.value?.get(index)?.let {
                roomRealization.deleteNotes(it)
            }
            liveDataList.value?.get(index)?.let { fireBase.deleteItem(it.id) }
            liveDataList.value =
                liveDataList.value?.minus(liveDataList.value?.get(index) ?: NotesModel())
        }
    }
// Редактирование заметок
    fun editNotes(notes: NotesModel) {
        CoroutineScope(Dispatchers.Main).launch {
            roomRealization.insertNotes(notes)
        }
        fireBase.editItems(notes)
        tempArray = liveDataList.value?.toMutableList()!!
        for (i in tempArray.withIndex()) if (i.value.id == notes.id) {
            tempArray[i.index] = notes
        }
        liveDataList.removeSource(liveDataDb)
        liveDataList.value = tempArray

    }

//Сохранение заметок в базу данных
    fun addNotesToDb(notesModelList: List<NotesModel>, onSuccess: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            for (i in notesModelList) {
                roomRealization.insertNotes(i)
            }
        }
        onSuccess()
    }

}