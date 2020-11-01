package com.example.movieshub.ui.details

import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
import com.example.movieshub.ui.adapter.ObservableRecyclerViewAdapter
import com.example.movieshub.databinding.ItemReviewBinding
import com.example.movieshub.domain.model.Review


class ReviewAdapter(reviewList: ObservableList<Review>)
    : ObservableRecyclerViewAdapter<Review, ReviewAdapter.Holder>(reviewList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class Holder(
        private val binding: ItemReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var review: Review

        fun bind(review: Review) {
            this.review = review
            binding.tvAuthor.text = review.author
            binding.tvReview.text = review.content
            binding.tvLink.setMovementMethod(LinkMovementMethod.getInstance())
            var reviewLink = "<a href='${review.url}'> ${review.url} </a>"
            binding.tvLink.setText(Html.fromHtml(reviewLink))
        }
    }
}