package com.alextsy.marvelcharacters.api

import com.alextsy.marvelcharacters.BuildConfig
import com.alextsy.marvelcharacters.data.models.CharactersResponse
import com.alextsy.marvelcharacters.util.md5
import retrofit2.http.GET
import retrofit2.http.Query
import java.sql.Timestamp

interface MarvelApi {

    // Network related constants
    companion object {
        const val BASE_URL = "https://gateway.marvel.com/v1/public/"
        const val CLIENT_ID = BuildConfig.MARVEL_API_KEY
        const val PRIVATE_KEY = BuildConfig.MARVEL_PRIVATE_KEY

        val ts = Timestamp(System.currentTimeMillis()).time.toString()
        fun md5Hash(): String {
            return md5("$ts$PRIVATE_KEY$CLIENT_ID")
        }
    }

    @GET("characters")
    suspend fun getCharacters(
        @Query("ts") ts: String = MarvelApi.ts,
        @Query("apikey") apiKey: String = BuildConfig.MARVEL_API_KEY,
        @Query("hash") hash: String = md5Hash(),
        @Query("nameStartsWith") nameStartsWith: String?,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int
    ): CharactersResponse
}