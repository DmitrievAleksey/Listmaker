package com.example.android.listmaker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import java.util.*

@Dao
interface ListmakerDao {

    @Query("SELECT * FROM FootballClub")
    fun getFootballClubs(): LiveData<List<FootballClub>>

    @Transaction
    @Query("SELECT * FROM FootballClub")
    fun getPlayersOfClub(): LiveData<List<PlayerOfClub>>

    @Query("SELECT * FROM Player WHERE playerId=(:id)")
    fun getPlayer(id: UUID): LiveData<Player?>

    @Insert
    fun addFootballClub(footballClub: FootballClub)
}