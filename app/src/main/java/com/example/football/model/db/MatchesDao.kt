package com.example.football.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MatchesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMatches(vararg matches: MatchesDBObject)

    @Query("SELECT * FROM matches_list")
    fun getAllMatches(): LiveData<List<MatchesDBObject>>
}