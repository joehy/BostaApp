package com.example.joebosta.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.joebosta.models.Photo
import com.example.joebosta.repositry.DataRepository
import com.example.joebosta.response.AlbumsListResponse
import com.example.joebosta.response.PhotosListResponse
import com.example.joebosta.response.UsersResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel() {



    private val _displayedImages = MutableLiveData<PhotosListResponse>()
    val displayedImages: LiveData<PhotosListResponse> = _displayedImages

    fun searchWithTitle(imageTitle: String) {
        _displayedImages.observeForever { images ->
            if (images != null) {
                val filteredImages = images.filter { image -> image.title.contains(imageTitle, ignoreCase = true) }
                val photosListResponse = PhotosListResponse()
                photosListResponse.addAll(filteredImages)
                _displayedImages.postValue(photosListResponse)
            }
        }
    }

    fun fetchAlbumImages(albumId: Int) {
        // Fetch user data and album list data from the repository and update the LiveData
        viewModelScope.launch {
            try {
                // Fetch album list data
                val images = dataRepository.getPhotos(albumId)
                Timber.d("images list size : {${images.size}}")
                _displayedImages.postValue(images)
            } catch (e: Exception) {
                Timber.e("fetchAlbumImages error: ${e.printStackTrace()}")
            }
        }
    }

}
