package com.recepyesilkaya.moviemvvm.data.api

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val API_KEY="8835e164059f2a901b52381b193852d0"
const val BASE_URL="https://developers.themoviedb.org/3/"
const val POSTER_PAGE_URL="https://image.tmdb.org/t/p/w342"

//https://api.themoviedb.org/3/movie/popular?api_key=8835e164059f2a901b52381b193852d0
//https://api.themoviedb.org/3/movie/547016?api_key=8835e164059f2a901b52381b193852d0
//https://image.tmdb.org/t/p/w342/cjr4NWURcVN3gW5FlHeabgBHLrY.jpg

object TheMovieDBClient{

    fun getClient():TheMovieDBInterface{
        val requestInterceptor= Interceptor{chain ->

            val url:HttpUrl=chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request:Request=chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)

        }
        val okHttpClient:OkHttpClient=OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60,TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMovieDBInterface::class.java)
    }

}