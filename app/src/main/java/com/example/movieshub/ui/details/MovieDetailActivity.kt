package com.example.movieshub.ui.details

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieshub.R
import com.example.movieshub.ui.base.BaseActivity
import com.example.movieshub.databinding.ActivityMovieDetailBinding
import com.example.movieshub.domain.model.movie_list.Result
import com.example.movieshub.utils.ratingsView
import kotlinx.android.synthetic.main.activity_movie_detail.*
import javax.inject.Inject


class MovieDetailActivity: BaseActivity() {

    companion object {
        const val EXTRA_MOVIE_DETAILS = "EXTRA_MOVIE_DETAILS"

        @JvmStatic
        @BindingAdapter("adapter")
        fun bindList(recyclerView: RecyclerView, viewModel: MovieDetailViewModel) {
            val adapter =
                ReviewAdapter(
                    viewModel.reviews
                )
            adapter.onItemClickListener = {

            }

            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = adapter

        }
    }

    @Inject lateinit var viewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMovieDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        screenComponent.inject(this)
        binding.viewModel = viewModel
        val movie = intent.getSerializableExtra(EXTRA_MOVIE_DETAILS) as Result
        supportActionBar!!.title = movie.original_title
        viewModel.bound(movie)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
        }
        return false
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        viewModel.unbound()
        super.onPause()
    }

    override fun onDestroy() {
        viewModel.unbound()
        super.onDestroy()
    }

    fun addRatings(ratings: Double) {
        ratingsView(ratings, ll_rating, this)
    }
}