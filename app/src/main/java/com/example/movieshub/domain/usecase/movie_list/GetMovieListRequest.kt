package com.example.movieshub.domain.usecase.movie_list

import com.example.movieshub.domain.usecase.base.BaseRequest

class GetMovieListRequest(val currentID: Int, val currentPage: Int): BaseRequest {
    override fun validate(): Boolean {
        return true
    }
}