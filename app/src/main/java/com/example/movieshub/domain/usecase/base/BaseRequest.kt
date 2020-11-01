package com.example.movieshub.domain.usecase.base

interface BaseRequest {
    fun validate(): Boolean
}