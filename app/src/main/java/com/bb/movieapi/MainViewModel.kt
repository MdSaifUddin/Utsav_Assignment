package com.bb.movieapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bb.movieapi.API.Repository
import com.bb.movieapi.API.Response
import com.bb.movieapi.Data.MainData
import com.bb.movieapi.Database.MovieData
import kotlinx.coroutines.launch

class MainViewModel(val repository: Repository) : ViewModel() {
    init {
        viewModelScope.launch {
            repository.getUpcoming()
        }
    }
    val offlineList: ArrayList<MovieData>
        get() = repository.offlineList

    val upcoming: LiveData<Response<MainData>>
        get() = repository.upcomingList

    val popular: LiveData<Response<MainData>>
        get() = repository.popularList

    val topRated: LiveData<Response<MainData>>
        get() = repository.topRatedList
}