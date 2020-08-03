package com.recepyesilkaya.moviemvvm.data.api

import com.recepyesilkaya.moviemvvm.data.vo.MovieDetails
import com.recepyesilkaya.moviemvvm.data.vo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {

   // https://api.themoviedb.org/3/movie/popular?api_key=8835e164059f2a901b52381b193852d0&page=1

    //Detail
    //https://api.themoviedb.org/3/movie/547016?api_key=8835e164059f2a901b52381b193852d0

    //BASE_URL
    //https://api.themoviedb.org/3/

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getData(@Path("movie_id") id :Int): Single<MovieDetails>
}