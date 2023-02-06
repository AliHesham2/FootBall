package com.example.football.model.db

import androidx.room.TypeConverter
import com.example.football.model.api.ScoreObject
import com.example.football.model.api.TeamObject
import com.google.gson.Gson

class Converters {
        @TypeConverter
        fun TeamObjectToJson(value: TeamObject) = Gson().toJson(value)

        @TypeConverter
        fun ScoreObjectToJson(value: ScoreObject) = Gson().toJson(value)

        @TypeConverter
        fun jsonToTeamObject(value: String) = Gson().fromJson(value, TeamObject::class.java)

        @TypeConverter
        fun jsonToScoreObject(value: String) = Gson().fromJson(value, ScoreObject::class.java)
}