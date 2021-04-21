package bangkit.adhytia.moviecatalogue.ui.movie

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import bangkit.adhytia.moviecatalogue.data.MovieEntity
import bangkit.adhytia.moviecatalogue.databinding.FragmentMovieBinding
import bangkit.adhytia.moviecatalogue.repository.Repository
import bangkit.adhytia.moviecatalogue.ui.moviedetail.MovieDetailsActivity
import bangkit.adhytia.moviecatalogue.ui.moviedetail.MovieDetailsActivity.Companion.EXTRA_MOVIE

class MovieFragment : Fragment() {
    private lateinit var binding: FragmentMovieBinding
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val repository = Repository(activity!!)

            val viewModel = ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            )[MovieViewModel::class.java]

            viewModel.repository = repository

            val movies = viewModel.getMovies()
            movieAdapter = MovieAdapter()
            movieAdapter.setMovies(movies)
            showRecyclerGrid()
        }
    }

    private fun showRecyclerGrid() {
        val orientation = resources.configuration.orientation
        val spanCount = if (orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 6

        with(binding.rvMovies) {
            layoutManager = GridLayoutManager(context, spanCount)
            setHasFixedSize(true)
            adapter = movieAdapter
        }

        movieAdapter.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
            override fun onItemClicked(data: MovieEntity) {
                val intent = Intent(activity, MovieDetailsActivity::class.java)
                intent.putExtra(EXTRA_MOVIE, data.id)
                startActivity(intent)
            }
        })
    }
}