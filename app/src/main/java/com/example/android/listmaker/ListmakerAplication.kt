package com.example.android.listmaker

import android.app.Application
import com.example.android.listmaker.database.ListmakerRepository

class ListmakerAplication: Application() {

    override fun onCreate() {
        super.onCreate()
        ListmakerRepository.initialize(this)
    }
}