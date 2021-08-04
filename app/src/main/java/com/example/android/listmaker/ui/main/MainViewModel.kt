package com.example.android.listmaker.ui.main

import androidx.lifecycle.ViewModel
import com.example.android.listmaker.database.FootballClub
import com.example.android.listmaker.database.ListmakerRepository

class MainViewModel : ViewModel() {

    private val listmakerRepository = ListmakerRepository.get()
    val footballClubs = listmakerRepository.getFootballClubs()

    fun addFootballClub(footballClub: FootballClub) {
        listmakerRepository.addFootballClub(footballClub)
    }
}