package com.example.android.listmaker.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.lang.IllegalStateException
import java.util.*

private const val DATABASE_NAME = "listmaker-database"

class ListmakerRepository private constructor(context: Context) {

    private val database: ListmakerDatabase = Room.databaseBuilder(
        context.applicationContext,
        ListmakerDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val listmakerDao = database.listmakerDao()

    fun getFootballClubs(): LiveData<List<FootballClub>> = listmakerDao.getFootballClubs()

    fun getPlayersOfClub(): LiveData<List<PlayerOfClub>> = listmakerDao.getPlayersOfClub()

    fun getPlayer(id: UUID): LiveData<Player?> = listmakerDao.getPlayer(id)

    companion object {
        private var INSTANCE: ListmakerRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ListmakerRepository(context)
            }
        }

        fun get(): ListmakerRepository {
            return INSTANCE ?:
            throw IllegalStateException("ListmakerRepository must be initialized")
        }
    }
}