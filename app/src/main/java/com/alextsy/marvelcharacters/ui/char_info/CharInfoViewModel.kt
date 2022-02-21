package com.alextsy.marvelcharacters.ui.char_info

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.alextsy.marvelcharacters.data.models.Result

class CharInfoViewModel @ViewModelInject constructor(
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private val _response = MutableLiveData<Result>()

    val resultLD: LiveData<Result>
        get() = _response

    val result = state.get<Result>("result")

    init {

    }
}