package com.example.android.listmaker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.android.listmaker.database.FootballClub
import com.example.android.listmaker.database.ListmakerRepository
import com.example.android.listmaker.database.Player
import com.example.android.listmaker.database.PlayersOfClub
import java.util.*

class MainViewModel : ViewModel() {

    private val listmakerRepository = ListmakerRepository.get()
    private val clubIdLiveData = MutableLiveData<UUID?>()

    val footballClubs: LiveData<List<FootballClub>> = listmakerRepository.getFootballClubs()

    val clubLiveData: LiveData<FootballClub?> = Transformations.switchMap(clubIdLiveData) {
            clubId -> listmakerRepository.getClub(clubId)
    }
    val playersOfClubLiveData: LiveData<PlayersOfClub?> =
        Transformations.switchMap(clubIdLiveData) {
                clubId -> listmakerRepository.getPlayersOfClub(clubId)
        }

    fun loadClubId(clubId: UUID?) {
        clubIdLiveData.value = clubId
    }

    fun addPlayer(player: Player) {
        listmakerRepository.addPlayer(player)
    }

    fun saveFootballClub(footballClub: FootballClub) {
        listmakerRepository.updateFootballClub(footballClub)
    }

    fun addFootballClub(footballClub: FootballClub) {
        listmakerRepository.addFootballClub(footballClub)
    }
}