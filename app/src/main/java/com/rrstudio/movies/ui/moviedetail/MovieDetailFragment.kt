package com.rrstudio.movies.ui.moviedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.rrstudio.movies.R
import com.rrstudio.movies.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private lateinit var binding:FragmentMovieDetailBinding
    private val args by navArgs<MovieDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieDetailBinding.bind(view)
        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w500/${args.posterImageUrl}")
            .centerCrop()
            .into(binding.imgMovie)
        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w500/${args.backgrounImageUrl}")
            .centerCrop()
            .into(binding.imgBackground)
        binding.txtDescription.text = args.overview
        binding.txtMoveTitle.text = args.title
        binding.textLanguage.text = "Language ${args.language}"
        binding.textRating.text = "${args.voteAvarage} (${args.voteCount} Reviews)"
        binding.textReleased.text = "Released ${args.releaseDate}"
    }
}