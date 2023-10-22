package com.example.joebosta.api

import com.example.joebosta.models.Album
import com.example.joebosta.response.AlbumsListResponse
import com.example.joebosta.response.PhotosListResponse
import com.example.joebosta.response.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {
    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): UsersResponse

    @GET("users/{userId}/albums")
    suspend fun getAlbums(@Path("userId") userId: Int): AlbumsListResponse

    @GET("albums/{albumId}/photos")
    suspend fun getPhotos(@Path("albumId") albumId: Int): PhotosListResponse
}
