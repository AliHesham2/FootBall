package com.example.football.model.api

import android.os.Parcelable
import com.example.football.model.db.MatchesDBObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class FootBallApiObject(val matches:List<FootBallApiDetailsObject>): Parcelable


@Parcelize
data class FootBallApiDetailsObject(
                                    val id:Int,
                                    val utcDate:String,
                                    val status:String,
                                    val matchday:Int,
                                    val score:ScoreObject,
                                    val homeTeam:TeamObject,
                                    val awayTeam:TeamObject): Parcelable
@Parcelize
data class ScoreObject(val fullTime:TimeObject,
                       val halfTime:TimeObject,
                       val extraTime:TimeObject,
                       val penalties:TimeObject): Parcelable

@Parcelize
data class TimeObject(val homeTeam:String?,
                      val awayTeam:String?): Parcelable

@Parcelize
data class TeamObject(val id:Int,
                      val name:String): Parcelable

@Parcelize
data class ErrorObject(val error:String?): Parcelable


/*  To convert Api object into db */
fun List<FootBallApiDetailsObject>.asDataBaseModel(): List<MatchesDBObject>{
    return map {
        MatchesDBObject(
            id = it.id,
            uctDate = it.utcDate,
            status = it.status,
            matchDay = it.matchday,
            homeTeam = it.homeTeam,
            awayTeam = it.awayTeam,
            score = it.score
        )
    }
}

