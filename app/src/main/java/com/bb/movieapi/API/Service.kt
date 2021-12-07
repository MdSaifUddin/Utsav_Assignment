package com.bb.movieapi.API

import com.bb.movieapi.Data.Description
import com.bb.movieapi.Data.FilePath
import com.bb.movieapi.Data.Images
import com.bb.movieapi.Data.MainData
import com.bb.movieapi.Data.MovieKey
import com.bb.movieapi.ui.Description.DescData.DescData
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val baseUrl="https://api.themoviedb.org/3/movie/"
const val apiKey="db75be3f6da59e6c54d0b9f568d19d16"
const val poster_baseUrl="https://image.tmdb.org/t/p/w500"
const val youtube_baseUrl="https://www.youtube.com/watch?v="

interface Service {
    @GET("upcoming?api_key=$apiKey")
    suspend fun getUpcoming(): Response<MainData>

    @GET("popular?api_key=$apiKey")
    suspend fun getPopular(): Response<MainData>

    @GET("top_rated?api_key=$apiKey")
    suspend fun getTopRated(): Response<MainData>

    @GET("{movieId}?api_key=$apiKey")
    suspend fun getDescription(@Path("movieId") movieId:String): Response<DescData>

    @GET("{movieId}/videos?api_key=$apiKey")
    suspend fun getMovieKey(@Path("movieId") movieId:String): Response<MovieKey>

    @GET("${baseUrl}{movieId}/images?api_key=$apiKey")
    suspend fun getImages(@Path("movieId") movieId:String): Response<Images>

}
