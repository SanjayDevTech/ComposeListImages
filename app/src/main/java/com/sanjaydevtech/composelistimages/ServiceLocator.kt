package com.sanjaydevtech.composelistimages

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {
    private var _pexelsService: PexelsService? = null
    val pexelsService: PexelsService
        get() {
            return _pexelsService ?: Retrofit.Builder()
                .baseUrl("https://api.pexels.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PexelsService::class.java)
        }
}