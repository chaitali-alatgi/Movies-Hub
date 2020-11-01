package com.example.movieshub.ui.details

import android.app.Activity
import android.view.View
import android.widget.TextView
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.example.movieshub.R
import com.example.movieshub.ui.rx.StickyAction
import com.example.movieshub.domain.model.Review
import com.example.movieshub.domain.model.movie_list.Result
import com.example.movieshub.domain.usecase.GetReviewsListUseCase
import com.example.movieshub.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.lang.ref.WeakReference
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val activityRef: WeakReference<Activity>,
    private val getReviewsListUseCase: GetReviewsListUseCase
) {
    private val disposables = CompositeDisposable()
    val posterImage = ObservableField<String>()
    val movieName = ObservableField<String>()
    val isAdult = ObservableField<String>()
    val releaseDate = ObservableField<String>()
    val votes = ObservableField<String>()
    val overview = ObservableField<String>()
    val language = ObservableField<String>()
    val reviews = ObservableArrayList<Review>()
    val progressVisibility = ObservableBoolean()
    val reviewsVisibility = ObservableBoolean()

    fun bound(movie: Result) {
        updateUI(movie)
        getGenreAndReviewList(movie.id)
    }

    private fun getGenreAndReviewList(movieId: Int) {
        progressVisibility.set(true)
        disposables.add(getReviewsListUseCase.execute(movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                handleReviewListResponseResult(it)
            })

    }

    private fun handleReviewListResponseResult(result: GetReviewsListUseCase.Result) {
        progressVisibility.set(false)
        when(result) {
            is GetReviewsListUseCase.Result.Success -> {
                reviews.addAll(result.reviewList)
                if(reviews.isNotEmpty()) {
                    reviewsVisibility.set(true)
                }
            }
            is GetReviewsListUseCase.Result.Failure -> {
                StickyAction<Boolean>().trigger(true)
                if (result.throwable is CustomException) {
                    if (reviews.isEmpty()) {
                        result.throwable.showErrorMessage(activityRef.get()!!.baseContext)
                    } else {
                        CustomException(CustomException.ERROR_CODE.SOMETHING_WENT_WRONG)
                            .showErrorMessage(activityRef.get()!!.baseContext)
                    }
                } else if (result.throwable is IOException || result.throwable is HttpException) {
                    CustomException(CustomException.ERROR_CODE.SOMETHING_WENT_WRONG)
                        .showErrorMessage(activityRef.get()!!.baseContext)
                }
            }
        }
    }

    private fun updateUI(movieDetails: Result) {
        posterImage.set(IMAGE_BASE_URL + movieDetails.backdrop_path)
        movieName.set(movieDetails.original_title)
        if(movieDetails.adult) {
            isAdult.set(activityRef.get()!!.getString(R.string.a))
        } else {
            isAdult.set(activityRef.get()!!.getString(R.string.u_a))
        }
        releaseDate.set(formatDate(
            movieDetails.release_date,
            DATE_FORMAT_YYYY_MM_DD, DATE_FORMAT_DD_MMM_YYYY))
        (activityRef.get() as MovieDetailActivity).addRatings(movieDetails.vote_average)
        if((movieDetails.vote_count/1000) <= 0) {
            votes.set("${formatDecimalString(
                (movieDetails.vote_count / 100).toDouble(), "#.#")} ${activityRef.get()!!.getString(R.string.likes)}"
            )
        } else {
            votes.set(
                "${formatDecimalString(
                    (movieDetails.vote_count / 1000).toDouble(), "#.#")}k ${activityRef.get()!!.getString(R.string.likes)}"
            )
        }
        overview.set(movieDetails.overview)
        language.set(movieDetails.original_language)
    }

    fun textClicked(view: View) {
        if((view as TextView).maxLines == 5) {
            view.maxLines = Int.MAX_VALUE
        } else {
            view.maxLines = 5
        }
    }

    fun unbound() {
        disposables.clear()
    }
}