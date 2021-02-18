package com.rrstudio.movies.ui.movie.adapters.concat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rrstudio.movies.core.BaseConcatHolder
import com.rrstudio.movies.databinding.PopularMoviesRowBinding
import com.rrstudio.movies.ui.movie.adapters.MovieAdapter


//A este adaptador se le pasa el adaptador con la lista

class PopularConcatAdapter(private val moviesAdapter: MovieAdapter):RecyclerView.Adapter<BaseConcatHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val itemBinding = PopularMoviesRowBinding.inflate(LayoutInflater.from(parent.context), parent , false)
        return  ConcatViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        when(holder){
            is ConcatViewHolder -> holder.bind(moviesAdapter)
        }
    }

    override fun getItemCount(): Int = 1

    private inner class ConcatViewHolder(val binding: PopularMoviesRowBinding): BaseConcatHolder<MovieAdapter>(binding.root){
        override fun bind(adapter: MovieAdapter){
            binding.rvPopularMovies.adapter = adapter
        }
    }
}