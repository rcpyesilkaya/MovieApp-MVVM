package com.recepyesilkaya.moviemvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.recepyesilkaya.moviemvvm.data.api.TheMovieDBInterface
import com.recepyesilkaya.moviemvvm.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MovieDetailsNetworkDataSource(private val apiService: TheMovieDBInterface,private val compositeDisposable: CompositeDisposable) {

    private val _networkState=MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get()=_networkState

    private val _downloadedMoviewDetailsResponse=MutableLiveData<MovieDetails>()
    val downloadedMoviewDetailsResponse : LiveData<MovieDetails>
        get()=_downloadedMoviewDetailsResponse

    fun fetchMovieDetails(movieId:Int){

        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getData(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedMoviewDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDetailsDataSource",it.message)
                        }
                    )
            )



        }catch (e:Exception){
            Log.e("MovieDetailsDataSource",e.message)
        }

    }


}
