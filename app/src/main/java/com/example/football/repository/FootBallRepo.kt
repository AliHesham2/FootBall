package com.example.football.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.football.model.api.FootBallApi
import com.example.football.model.api.FootBallApiDetailsObject
import com.example.football.model.db.MatchesDB
import com.example.football.model.db.MatchesDBObject
import com.example.football.model.db.asApiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FootBallRepo
@Inject
constructor(private val db: MatchesDB, private val api: FootBallApi){

    var offlineMatches : MutableLiveData<List<FootBallApiDetailsObject>> = Transformations.map(db.matchDao.getAllMatches()){ it.asApiModel() } as MutableLiveData<List<FootBallApiDetailsObject>>

    suspend fun getMatchesList() = api.getFootBallList()

    suspend fun saveMatchesIntoDB(results: Array<MatchesDBObject>) {
        withContext(Dispatchers.IO) {
            db.matchDao.insertMatches(*results)
        }
    }

}