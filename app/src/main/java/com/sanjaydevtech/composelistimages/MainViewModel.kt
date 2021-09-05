package com.sanjaydevtech.composelistimages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class Status {
    object Loading: Status()
    object Error: Status()
    object Idle: Status()
    object Success: Status()
}

class MainViewModel: ViewModel() {
    private val _images: MutableLiveData<Response> = MutableLiveData()
    val images: LiveData<Response>
        get() = _images

    private val _status: MutableLiveData<Status> = MutableLiveData(Status.Idle)
    val status: LiveData<Status>
        get() = _status

    fun setLoading(loading: Boolean) {
        when {
            loading -> {
                _status.value = Status.Loading
            }
            images.value != null -> {
                _status.value = Status.Success
            }
            else -> {
                _status.value = Status.Idle
            }
        }
    }

    suspend fun fetch(query: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val tmp = withContext(Dispatchers.IO){ ServiceLocator.pexelsService.getImages(query = query, perPage = 10) }
                _images.value = tmp
                _status.value = Status.Success
            } catch (e: Exception) {
                println(e)
                _status.value = Status.Error
            }
        }
    }

}