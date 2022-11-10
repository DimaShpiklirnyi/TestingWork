package com.dima_shpiklirnyi.testingwork.domain.UseCase

import java.util.*

class Time {
    //Текущее время
    fun getTime () : Long  {
        return System.currentTimeMillis()
    }
    //Функция для отображения времени в разных форматах
    fun setTime(oldTime:Long) : String {
        val dateFormat = java.text.SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val timeFormat = java.text.SimpleDateFormat("hh:mm", Locale.getDefault())
        val currentDate = dateFormat.format(getTime())
        val oldtime = dateFormat.format(oldTime)
        if (currentDate != oldtime) return dateFormat.format(oldTime)
        else return timeFormat.format(oldTime)
    }
}