package com.example.joebosta.repositry

import com.example.joebosta.api.NetworkService
import com.example.joebosta.response.AlbumsListResponse
import com.example.joebosta.response.PhotosListResponse
import com.example.joebosta.response.UsersResponse
import javax.inject.Inject

class DataRepository @Inject constructor(private val networkService: NetworkService) {

    // Fetch a user by ID
    suspend fun getUser(userId: Int): UsersResponse {
        return networkService.getUser(userId)
    }

    // Fetch albums for a user
    suspend fun getAlbums(userId: Int): AlbumsListResponse {
        return networkService.getAlbums(userId)
    }

    // Fetch photos for an album
    suspend fun getPhotos(albumId: Int): PhotosListResponse {
        return networkService.getPhotos(albumId)
    }
}
