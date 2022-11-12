package com.dima_shpiklirnyi.testingwork.App

import android.app.Application
import com.dima_shpiklirnyi.testingwork.DI.AppComponent
import com.dima_shpiklirnyi.testingwork.DI.AppModule
import com.dima_shpiklirnyi.testingwork.DI.DaggerAppComponent
import com.dima_shpiklirnyi.testingwork.appCompanent

class APP : Application() {

    override fun onCreate() {
        appCompanent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        super.onCreate()
    }
}