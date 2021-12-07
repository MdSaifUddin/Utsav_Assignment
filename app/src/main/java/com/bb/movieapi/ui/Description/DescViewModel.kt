package com.bb.movieapi.ui.Description

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bb.movieapi.API.Repository
import com.bb.movieapi.Data.Description
import com.bb.movieapi.Data.Images
import com.bb.movieapi.Data.MovieKey
import com.bb.movieapi.ui.Description.DescData.DescData
import kotlinx.coroutines.launch

class DescViewModel(val repository: Repository,val id:String): ViewModel() {
    init {
        viewModelScope.launch {
            repository.getImages(id)
            repository.getDescription(id)
            repository.getKey(id)
        }
    }
    val imageList:MutableLiveData<Images>
    get()=repository.imageList

    val desc:MutableLiveData<DescData>
    get()=repository.description

    val key:MutableLiveData<MovieKey>
    get()=repository.key
}