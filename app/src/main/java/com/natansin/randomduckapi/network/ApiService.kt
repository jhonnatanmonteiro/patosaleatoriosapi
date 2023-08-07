package com.natansin.randomduckapi.network

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("v2/quack")
    suspend fun getDuckRandom() : Response<Duck>
}