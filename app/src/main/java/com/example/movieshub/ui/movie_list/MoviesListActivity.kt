package com.example.movieshub.ui.movie_list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieshub.R
import com.example.movieshub.ui.base.BaseActivity
import com.example.movieshub.databinding.ActivityMovieslistBinding
import com.example.movieshub.ui.adapter.PaginationScrollListener
import com.example.movieshub.ui.base.toast
import com.example.movieshub.ui.viewmodel.MoviesListViewModel
import com.example.movieshub.ui.viewmodel.model.ErrorMoviesListState
import com.example.movieshub.ui.viewmodel.model.LoadingMoviesListState
import com.example.movieshub.ui.viewmodel.model.MoviesListState
import com.example.movieshub.ui.viewmodel.model.SuccessMoviesListState
import com.example.movieshub.domain.model.Response
import com.example.movieshub.domain.model.movie_list.Result
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MoviesListActivity: BaseActivity() {

    @Inject
    lateinit var viewModel: MoviesListViewModel

    private val disposable = CompositeDisposable()
    lateinit var binding: ActivityMovieslistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movieslist)
        screenComponent.inject(this)
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(R.string.app_name)
        binding.viewModel = viewModel
        viewModel.bound()
        observeMovieListViewModel()
    }

    private fun observeMovieListViewModel() {
        viewModel.moviesListStateLiveData.observe(this, movieListStateObserver)
    }

    private val movieListStateObserver = Observer<MoviesListState> { state ->
        state?.let {
            when (state) {
                is SuccessMoviesListState -> {
                    viewModel.progressVisibility.set(false)
                    val success = it.response as Response.Success
                    viewModel.loadPage(success.data)
                }
                is LoadingMoviesListState -> {
                    viewModel.progressVisibility.set(false)
                }
                is ErrorMoviesListState -> {
                    showError(it)
                }
            }
        }
    }

    private fun showError(state: MoviesListState) {
        val error = state.response as Response.Error
        toast(error.exception.message, Toast.LENGTH_LONG)
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
}