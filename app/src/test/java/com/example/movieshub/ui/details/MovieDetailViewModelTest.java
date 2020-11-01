package com.example.movieshub.ui.details

import androidx.databinding.ObservableField
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.example.movieshub.ui.details.MovieDetailViewModel
import org.junit.Assert.assertEquals
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    private val movieName = ObservableField<String>()
    private val isAdult = ObservableField<String>()
    private val releaseDate = ObservableField<String>()
    private val votes = ObservableField<String>()
    private val language = ObservableField<String>()
    private lateinit var viewModel: MovieDetailViewModel

    @Before
    fun setUp() {
        viewModel = mock(MovieDetailViewModel::class.java)
        `when`(viewModel.movieName).thenReturn(movieName)
        `when`(viewModel.isAdult).thenReturn(isAdult)
        `when`(viewModel.releaseDate).thenReturn(releaseDate)
        `when`(viewModel.votes).thenReturn(votes)
        `when`(viewModel.language).thenReturn(language)
    }

    @Test
    fun updateUI() {
        movieName.set("Thor: Ragnarok")
        assertEquals(viewModel.movieName.get(), "Thor: Ragnarok")
        isAdult.set("U/A")
        assertEquals(viewModel.isAdult.get(), "U/A")
        releaseDate.set("2017-10-25")
        assertEquals(viewModel.releaseDate.get(), "2017-10-25")
        votes.set("14546")
        assertEquals(viewModel.votes.get(), "14546")
        language.set("en")
        assertEquals(viewModel.language.get(), "en")
    }
}