package com.example.movieshub.data.mapper

internal interface IMapper<in I, out O> {

    @Throws(MapperException::class)
    fun map(input: I): O
}