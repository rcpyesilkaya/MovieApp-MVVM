package com.recepyesilkaya.moviemvvm.data.vo

import android.app.Service
import androidx.lifecycle.LiveData
import com.recepyesilkaya.moviemvvm.data.api.TheMovieDBInterface
import com.recepyesilkaya.moviemvvm.data.repository.MovieDetailsNetworkDataSource
import com.recepyesilkaya.moviemvvm.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService: TheMovieDBInterface) {

    lateinit var movieDetailsNetworkDataSource:MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable,movieId:Int):LiveData<MovieDetails>{

        movieDetailsNetworkDataSource= MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMoviewDetailsResponse
    }

    fun getMovieDetailsNetworkState():LiveData<NetworkState>{
        return movieDetailsNetworkDataSource.networkState
    }
}