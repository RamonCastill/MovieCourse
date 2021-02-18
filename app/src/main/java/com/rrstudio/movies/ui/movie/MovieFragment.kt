package com.rrstudio.movies.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.rrstudio.movies.R
import com.rrstudio.movies.core.Resource
import com.rrstudio.movies.data.local.AppDatabase
import com.rrstudio.movies.data.local.LocalMovieDataSource
import com.rrstudio.movies.data.model.Movie
import com.rrstudio.movies.data.remote.RemoteMovieDataSource
import com.rrstudio.movies.databinding.FragmentMovieBinding
import com.rrstudio.movies.presentation.MovieViewModel
import com.rrstudio.movies.repository.MovieRepositoryImpl
import com.rrstudio.movies.repository.RetrofitClient
import com.rrstudio.movies.ui.movie.adapters.MovieAdapter
import com.rrstudio.movies.ui.movie.adapters.concat.PopularConcatAdapter
import com.rrstudio.movies.ui.movie.adapters.concat.TopRatedConcatAdapter
import com.rrstudio.movies.ui.movie.adapters.concat.UpcomingConcatAdapter

class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.OnMovieClickListener {

    private lateinit var binding: FragmentMovieBinding

    //Inyeccion de dependencias manual
    private val viewModel by viewModels<MovieViewModel> {
        MovieViewModel.MovieViewModelFactory(
            MovieRepositoryImpl(
                RemoteMovieDataSource(RetrofitClient.webservice),
                LocalMovieDataSource(AppDatabase.getDatabase(requireContext()).movieDao())
            )
        )
    }

    private lateinit var concatAdapter: ConcatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        concatAdapter = ConcatAdapter()

        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(
                            0,
                            UpcomingConcatAdapter(
                                MovieAdapter(
                                    result.data.first.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            1,
                            TopRatedConcatAdapter(
                                MovieAdapter(
                                    result.data.second.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            2,
                            PopularConcatAdapter(
                                MovieAdapter(
                                    result.data.third.results,
                                    this@MovieFragment
                                )
                            )
                        )
                    }

                    binding.rvMovies.adapter = concatAdapter
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("Error", "${result.exception}")
                }
            }
        })


    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movie.poster_path,
            movie.backdrop_path,
            movie.vote_average.toFloat(),
            movie.vote_count,
            movie.overview, movie.title,
            movie.original_language,
            movie.released_date,
        )
        findNavController().navigate(action)
    }
}