package com.example.android.listmaker.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import java.util.*

@Dao
interface ListmakerDao {

    @Query("SELECT * FROM FootballClub")
    fun getFootballClubs(): List<FootballClub>

    @Transaction
    @Query("SELECT * FROM FootballClub")
    fun getPlayersOfClub(): List<PlayerOfClub>

    @Query("SELECT * FROM Player WHERE playerId=(:id)")
    fun getPlayer(id: UUID): Player?
}