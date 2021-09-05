package com.sanjaydevtech.composelistimages

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PexelsService {

    @Headers("Authorization: 563492ad6f917000010000013b68d3e217514ab7a4185f3a741ba48c")
    @GET("search")
    suspend fun getImages(@Query("query") query: String, @Query("per_page") perPage: Int = 1): Response
}
