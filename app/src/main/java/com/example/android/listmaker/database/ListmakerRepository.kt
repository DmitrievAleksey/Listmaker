package com.example.android.listmaker.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "listmaker-database"

class ListmakerRepository private constructor(context: Context) {

    private val database: ListmakerDatabase = Room.databaseBuilder(
        context.applicationContext,
        ListmakerDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val listmakerDao = database.listmakerDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getFootballClubs(): LiveData<List<FootballClub>> = listmakerDao.getFootballClubs()

    fun getClub(id: UUID?): LiveData<FootballClub?>? = id?.let { listmakerDao.getClub(it) }

    fun getPlayersOfClub(id: UUID?): LiveData<PlayersOfClub?>? = id?.let {
        listmakerDao.getPlayersOfClub(it)
    }

    fun getPlayer(id: UUID): LiveData<Player?> = listmakerDao.getPlayer(id)

    fun addFootballClub(footballClub: FootballClub) {
        executor.execute {
            listmakerDao.addFootballClub(footballClub)
        }
    }

    fun addPlayer(player: Player) {
        executor.execute {
            listmakerDao.addPlayer(player)
        }
    }

    fun updateFootballClub(footballClub: FootballClub) {
        executor.execute {
            listmakerDao.updateFootballClub(footballClub)
        }
    }

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