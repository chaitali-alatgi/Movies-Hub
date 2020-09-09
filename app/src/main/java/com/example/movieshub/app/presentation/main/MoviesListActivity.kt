package com.example.movieshub.app.presentation.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieshub.R
import com.example.movieshub.app.BaseActivity
import com.example.movieshub.databinding.ActivityMovieslistBinding
import com.example.movieshub.app.presentation.adapter.PaginationScrollListener
import com.example.movieshub.domain.model.movie_list.Result
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MoviesListActivity: BaseActivity() {

    @Inject lateinit var viewModel: MoviesListViewModel
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMovieslistBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_movieslist)
        screenComponent.inject(this)
        binding.viewModel = viewModel
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(R.string.app_name)
        viewModel.bound()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        disposable.clear()
        super.onPause()
    }

    override fun onDestroy() {
        viewModel.unbound()
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        @BindingAdapter("adapter")
        fun bindList(recyclerView: RecyclerView, viewModel: MoviesListViewModel) {
            val adapter =
                MovieAdapter(
                    viewModel.moviesList
                )
            adapter.onItemClickListener = {
                viewModel.onMovieClicked(it as Result)
            }

            var gridLayoutManager = GridLayoutManager(recyclerView.context, 2)
            recyclerView.layoutManager = gridLayoutManager
            recyclerView.adapter = adapter
            recyclerView.addOnScrollListener(object: PaginationScrollListener(gridLayoutManager) {
                override fun onLoadMore() {
                    viewModel.loadMoreData()
                }
                override val isLastPage = viewModel.isLastPage
                override var currentPage: Int = viewModel.currentPage
            })
        }
    }
}