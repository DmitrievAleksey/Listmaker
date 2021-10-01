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
    var footballClubLogo: String = ""
)

@Entity
data class Player(
    @PrimaryKey val playerId: UUID = UUID.randomUUID(),
    var clubId: UUID? = null,
    var playerName: String = "",
    var playerPhoto: String = ""
)

data class PlayersOfClub(
    @Embedded val footballClub: FootballClub,
    @Relation(
        parentColumn = "footballClubId",
        entityColumn = "clubId"
    )
    val players: List<Player>
)