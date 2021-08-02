package com.example.android.listmaker.ui.main

import androidx.lifecycle.ViewModel
import com.example.android.listmaker.database.ListmakerRepository

class MainViewModel : ViewModel() {

    private val listmakerRepository = ListmakerRepository.get()
    val footballClubs = listmakerRepository.getFootballClubs()
}