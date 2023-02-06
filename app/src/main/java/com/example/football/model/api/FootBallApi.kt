package com.example.football.model.api

import retrofit2.Response
import retrofit2.http.GET

interface FootBallApi {
    @GET("/v2/competitions/2021/matches")
    suspend fun getFootBallList(): Response<FootBallApiObject>
}