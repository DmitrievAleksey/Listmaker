package com.example.android.listmaker.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.*

@Entity
data class FootballClub(
    @PrimaryKey val footballClubId: UUID = UUID.randomUUID(),
    var footballClubName: String = "",
    val footballClubLogo: String = ""
)

@Entity
data class Player(
    @PrimaryKey val playerId: UUID = UUID.randomUUID(),
    var playerOfClub: UUID,
    val playerName: String,
    var playerPhoto: String = ""
)

data class PlayerOfClub(
    @Embedded val footballClub: FootballClub,
    @Relation(
        parentColumn = "footballClubId",
        entityColumn = "playerOfClub"
    )
    val players: List<Player>
)