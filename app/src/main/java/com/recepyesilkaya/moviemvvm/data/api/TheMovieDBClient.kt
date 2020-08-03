package com.recepyesilkaya.moviemvvm.data.api

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


const val API_KEY="8835e164059f2a901b52381b193852d0"
const val BASE_URL="https://api.themoviedb.org/3/"
const val POSTER_BASE_URL="https://image.tmdb.org/t/p/w342"

const val FIRST_PAGE=1
const val POST_PER_PAGE=20

//https://api.themoviedb.org/3/movie/popular?api_key=8835e164059f2a901b52381b193852d0
//https://api.themoviedb.org/3/movie/547016?api_key=8835e164059f2a901b52381b193852d0
//https://image.tmdb.org/t/p/w342/cjr4NWURcVN3gW5FlHeabgBHLrY.jpg

object TheMovieDBClient{

    fun getClient() : TheMovieDBInterface{
        val requestInterceptor= Interceptor{chain ->

            val url=chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request=chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)

        }
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()


        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(TheMovieDBInterface::class.java)
    }

}