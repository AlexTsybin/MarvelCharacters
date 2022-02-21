package com.alextsy.marvelcharacters.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.alextsy.marvelcharacters.api.MarvelApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarvelRepository @Inject constructor(private val marvelApi: MarvelApi) {

    fun getCharactersResult(search: String?) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                maxSize = 100
            ),
            pagingSourceFactory = { MarvelPagingSource(marvelApi, search) },
        ).liveData
}