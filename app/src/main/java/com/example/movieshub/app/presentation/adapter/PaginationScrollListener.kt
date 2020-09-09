package com.example.movieshub.app.presentation.adapter

import androidx.annotation.NonNull
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(
    private val gridLayoutManager: GridLayoutManager
): RecyclerView.OnScrollListener() {

    private var previousTotalItemCount = 0
    private var isLoading = true

    override fun onScrolled(
        @NonNull recyclerView: RecyclerView, dx: Int,
        dy: Int
    ) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleThreshold = gridLayoutManager.findLastCompletelyVisibleItemPosition()
        val totalItemCount = gridLayoutManager.itemCount
        val lastVisibleItemPosition = gridLayoutManager.findLastCompletelyVisibleItemPosition()
        if (isLoading && (totalItemCount > previousTotalItemCount)) {
            isLoading = false
            previousTotalItemCount = totalItemCount
        }

        if (!isLoading && ((visibleThreshold + lastVisibleItemPosition) >= totalItemCount
                    && recyclerView.adapter!!.itemCount > visibleThreshold)) {
            onLoadMore()
            isLoading = true
        }
    }

    abstract fun onLoadMore()
    abstract val isLastPage: Boolean
    abstract var currentPage: Int
}