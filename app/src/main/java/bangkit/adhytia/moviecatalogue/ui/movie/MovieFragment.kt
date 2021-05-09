package bangkit.adhytia.moviecatalogue.ui.movie

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import bangkit.adhytia.moviecatalogue.data.MovieEntity
import bangkit.adhytia.moviecatalogue.databinding.FragmentMovieBinding
import bangkit.adhytia.moviecatalogue.ui.moviedetail.MovieDetailsActivity
import bangkit.adhytia.moviecatalogue.ui.moviedetail.MovieDetailsActivity.Companion.EXTRA_MOVIE
import bangkit.adhytia.moviecatalogue.viewmodel.ViewModelFactory

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
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel =
                ViewModelProvider(requireActivity(), factory)[MovieViewModel::class.java]

            movieAdapter = MovieAdapter()
            viewModel.getMovies().observe(viewLifecycleOwner, { movies ->
                populateRecyclerView(movies)
            })

            viewModel.getMovies()

            movieAdapter.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
                override fun onItemClicked(data: MovieEntity) {
                    val intent = Intent(activity, MovieDetailsActivity::class.java)
                    intent.putExtra(EXTRA_MOVIE, data.id)
                    startActivity(intent)
                }
            })
        }
    }

    private fun populateRecyclerView(movies: List<MovieEntity>) {
        val orientation = resources.configuration.orientation
        val spanCount = if (orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 6

        with(binding) {
            movieAdapter.setMovies(movies)
            movieAdapter.notifyDataSetChanged()
            rvMovies.layoutManager = GridLayoutManager(context, spanCount)
            rvMovies.setHasFixedSize(true)
            rvMovies.adapter = movieAdapter
            val dividerItemDecoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            rvMovies.addItemDecoration(dividerItemDecoration)
        }
    }
}