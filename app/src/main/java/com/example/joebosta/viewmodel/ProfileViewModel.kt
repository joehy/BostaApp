package com.example.joebosta.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.joebosta.repositry.DataRepository
import com.example.joebosta.response.AlbumsListResponse
import com.example.joebosta.response.UsersResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel() {

    private val _userData = MutableLiveData<UsersResponse>()
    val userData: LiveData<UsersResponse> = _userData

    private val _albumListData = MutableLiveData<AlbumsListResponse>()
    val albumListData: LiveData<AlbumsListResponse> = _albumListData


    fun fetchUserWithAlbums(userId: Int) {
        // Fetch user data and album list data from the repository and update the LiveData
        viewModelScope.launch {
            try {
                // Fetch user data
                val user = dataRepository.getUser(userId)
                _userData.value = user

                // Fetch album list data
                val albums = dataRepository.getAlbums(userId)
                Timber.d("albums list size : {${albums.size}}")
                _albumListData.postValue(albums)

            } catch (e: Exception) {
                Timber.e("fetchUserWithAlbums error: ${e.printStackTrace()}")
            }
        }
    }

}
