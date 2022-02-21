package com.alextsy.marvelcharacters.ui.characters

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alextsy.marvelcharacters.data.MarvelRepository

class CharactersViewModel @ViewModelInject constructor(
    private val repository: MarvelRepository,
    @Assisted state: SavedStateHandle
) : ViewModel() {

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val characters = currentQuery.switchMap { queryString ->
        repository.getCharactersResult(if (queryString.isEmpty()) null else queryString).cachedIn(viewModelScope)
    }

    fun searchHeroes(query: String?) {
        currentQuery.value = query
    }

    // Search related constants
    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = ""
    }
}