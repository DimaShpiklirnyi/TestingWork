package com.dima_shpiklirnyi.testingwork

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.dima_shpiklirnyi.testingwork.Repository.FirebaseServer.initFirebase
import com.dima_shpiklirnyi.testingwork.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mBinding : ActivityMainBinding
    lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        //Константа контекста MainActivity
        MAIN = this
        //Реализация навигации
        val navHostFragment =
        supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        // Подключение FireBase
        initFirebase()
    }
}