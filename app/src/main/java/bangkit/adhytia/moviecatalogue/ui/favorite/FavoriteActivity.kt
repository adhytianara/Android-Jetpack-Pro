package bangkit.adhytia.moviecatalogue.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import bangkit.adhytia.moviecatalogue.R
import bangkit.adhytia.moviecatalogue.databinding.ActivityFavoriteBinding
import bangkit.adhytia.moviecatalogue.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var movieAdapter: FavoriteMoviePagedListAdapter
    private lateinit var tvShowAdapter: FavoriteTvShowPagedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.my_favorite)

        val factory = ViewModelFactory.getInstance(this)
        viewModel =
            ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        setupRecyclerView()

        viewModel.getAllMovies().observe(this, {
            movieAdapter.submitList(it)
        })

        viewModel.getAllTvShows().observe(this, {
            tvShowAdapter.submitList(it)
        })

        viewModel.getAllMovies()

        setupButton()
    }

    private fun setupRecyclerView() {
        movieAdapter = FavoriteMoviePagedListAdapter(this)
        tvShowAdapter = FavoriteTvShowPagedListAdapter(this)

        binding.rvFavorite.setHasFixedSize(true)
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
    }

    private fun setupButton() {
        binding.apply {
            btnMovie.setOnClickListener {
                btnMovie.isEnabled = false
                btnMovie.alpha = .5f
                btnTvShow.isEnabled = true
                btnTvShow.alpha = 1f
                rvFavorite.adapter = movieAdapter
            }

            btnTvShow.setOnClickListener {
                btnTvShow.isEnabled = false
                btnTvShow.alpha = .5f
                btnMovie.isEnabled = true
                btnMovie.alpha = 1f
                rvFavorite.adapter = tvShowAdapter
            }
        }
    }
}