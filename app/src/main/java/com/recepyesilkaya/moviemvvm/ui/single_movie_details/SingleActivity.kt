package com.recepyesilkaya.moviemvvm.ui.single_movie_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.recepyesilkaya.moviemvvm.R
import com.recepyesilkaya.moviemvvm.data.api.POSTER_BASE_URL
import com.recepyesilkaya.moviemvvm.data.api.TheMovieDBClient
import com.recepyesilkaya.moviemvvm.data.api.TheMovieDBInterface
import com.recepyesilkaya.moviemvvm.data.repository.NetworkState
import com.recepyesilkaya.moviemvvm.data.vo.MovieDetails
import kotlinx.android.synthetic.main.activity_single.*
import java.text.NumberFormat
import java.util.*

class SingleActivity : AppCompatActivity() {

    private lateinit var viewModel:SingleMovieViewModel
    private  lateinit var movieRepository:MovieDetailsRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)

        var movieId:Int=intent.getIntExtra("id",1)
        println("id "+movieId)

        val apiService: TheMovieDBInterface=TheMovieDBClient.getClient()
        movieRepository= MovieDetailsRepository(apiService)

        viewModel=getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility=if (it== NetworkState.LOADING)View.VISIBLE else View.GONE
            txt_error.visibility=if (it== NetworkState.ERROR)View.VISIBLE else View.GONE
        })

    }

    private fun bindUI(it: MovieDetails?) {
        if (it != null) {
            movie_title.text = it.title
            movie_tagline.text = it.tagline
            movie_release_date.text = it.releaseDate
            movie_rating.text = it.rating.toString()
            movie_runtime.text = it.runtime.toString() + " minutes"
            movie_overview.text = it.overview

            val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
            movie_budget.text = formatCurrency.format(it.budget)
            movie_revenue.text = formatCurrency.format(it.revenue)

            val moviePosterURL = POSTER_BASE_URL + it.posterPath
            Glide.with(this)
                .load(moviePosterURL)
                .into(iv_movie_poster)
        }

    }


    private fun getViewModel(movieId:Int) : SingleMovieViewModel{
        return ViewModelProviders.of(this, object :ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieRepository,movieId)as T
            }

        })[SingleMovieViewModel::class.java]
    }

}
