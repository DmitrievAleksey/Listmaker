package com.example.android.listmaker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FootballClub::class, Player::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class ListmakerDatabase: RoomDatabase() {

    abstract fun listmakerDao(): ListmakerDao
}