package com.alextsy.marvelcharacters.data

import androidx.paging.PagingSource
import com.alextsy.marvelcharacters.api.MarvelApi
import com.alextsy.marvelcharacters.data.models.Result
import retrofit2.HttpException
import java.io.IOException

private const val MARVEL_STARTING_PAGE_INDEX = 1

class MarvelPagingSource(
    private val marvelApi: MarvelApi,
    private val search: String?
) : PagingSource<Int, Result>() {

    var tryNumber = 0
    override val keyReuseSupported: Boolean = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val page = params.key ?: MARVEL_STARTING_PAGE_INDEX
        val pageSize = params.loadSize

        tryNumber += 1

        return try {
            val characters = marvelApi.getCharacters(
                nameStartsWith = search,
                offset = (tryNumber - 1) * 10
            ).data.results

            LoadResult.Page(
                data = characters,
                nextKey = if (characters.size < page) null else page + 1,
                prevKey = if (page == 1) null else page - 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}