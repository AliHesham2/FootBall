package com.example.football.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.football.model.api.FootBallApiDetailsObject
import com.example.football.model.api.ScoreObject
import com.example.football.model.api.TeamObject


@Entity(tableName = "matches_list")
data class MatchesDBObject (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "uctDate")
    val uctDate: String,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "matchDay")
    val matchDay: Int,
    @ColumnInfo(name = "homeTeam")
    val homeTeam: TeamObject,
    @ColumnInfo(name = "awayTeam")
    val awayTeam: TeamObject,
    @ColumnInfo(name = "score")
    val score: ScoreObject,
    )

fun List<MatchesDBObject>.asApiModel(): List<FootBallApiDetailsObject>{
    return map {
        FootBallApiDetailsObject(
            id = it.id,
            status = it.status ,
            utcDate = it.uctDate,
            homeTeam = it.homeTeam,
            awayTeam = it.awayTeam,
            matchday = it.matchDay,
            score = it.score
        )
    }
}



