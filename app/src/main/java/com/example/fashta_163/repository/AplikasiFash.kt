package com.example.fashta_163.repository

import android.app.Application

class AplikasiFash : Application() {
    lateinit var containerApp: ContainerApp
        private set

    override fun onCreate() {
        super.onCreate()
        containerApp = DefaultContainerApp()
    }
}
