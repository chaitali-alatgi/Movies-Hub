package com.example.movieshub.app.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
import com.example.movieshub.R
import com.example.movieshub.app.presentation.adapter.ObservableRecyclerViewAdapter
import com.example.movieshub.databinding.ItemMovieBinding
import com.example.movieshub.domain.model.movie_list.Result
import com.example.movieshub.utils.IMAGE_BASE_URL
import com.example.movieshub.utils.ratingsView
import com.squareup.picasso.Picasso


class MovieAdapter(moviesList: ObservableList<Result>)
    : ObservableRecyclerViewAdapter<Result, MovieAdapter.Holder>(moviesList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class Holder(
        private val binding: ItemMovieBinding,
        private val onItemClickListener: ((item: Any) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var movie: Result

        fun bind(movie: Result) {
            this.movie = movie
            Picasso.get().load(IMAGE_BASE_URL + movie.poster_path).into(binding.ivPoster)
            binding.tvTitle.text = movie.original_title
            if(movie.adult) {
                binding.tvIsAdult.setText(R.string.a)
            } else {
                binding.tvIsAdult.setText(R.string.u_a)
            }
            ratingsView(movie.vote_average, binding.llRating, binding.root.context)

            binding.root.setOnClickListener {
                onItemClickListener?.invoke(movie)
            }
        }
    }
}