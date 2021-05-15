package bangkit.adhytia.moviecatalogue.ui.moviedetail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import bangkit.adhytia.moviecatalogue.R
import bangkit.adhytia.moviecatalogue.data.MovieEntity
import bangkit.adhytia.moviecatalogue.databinding.ActivityMovieDetailsBinding
import bangkit.adhytia.moviecatalogue.ui.favorite.FavoriteActivity
import bangkit.adhytia.moviecatalogue.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var movie: MovieEntity
    private var isFavorite = false
    private var isInDatabase = false

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra(EXTRA_MOVIE, 0)

        val factory = ViewModelFactory.getInstance(this)
        viewModel =
            ViewModelProvider(this, factory)[MovieDetailViewModel::class.java]

        viewModel.setSelectedMovie(movieId)

        viewModel.movie.observe(this, { movie ->
            this.movie = movie
            populateMovie(movie)
        })

        viewModel.getMovieInDatabase().observe(this, { movieInDb ->
            isFavorite = movieInDb != null
            isInDatabase = movieInDb != null
            showButtonFavorite()
        })

        viewModel.getMovie()

        binding.btnFavorite.setOnClickListener {
            isFavorite = !isFavorite
            showButtonFavorite()
        }
    }

    private fun showButtonFavorite() {
        if (isFavorite) {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getMovieInDatabase()
    }

    override fun onPause() {
        super.onPause()
        if (isFavorite != (isInDatabase)) {
            if (isInDatabase) {
                viewModel.deleteMovie(movie)
            } else {
                viewModel.insertMovie(movie)
            }
        }
    }

    private fun populateMovie(movie: MovieEntity?) {
        title = movie?.title
        binding.tvTitle.text = movie?.title
        (getString(R.string.release_date) + movie?.releaseDate).also {
            binding.tvReleaseDate.text = it
        }
        (getString(R.string.popularity) + movie?.popularity).also { binding.tvPopularity.text = it }
        (getString(R.string.vote_average) + movie?.voteAverage).also { binding.tvVoteAvg.text = it }
        (getString(R.string.vote_count) + movie?.voteCount).also { binding.tvVoteCount.text = it }
        binding.tvOverview.text = movie?.overview
        Glide.with(this)
            .load(movie?.posterURL)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(binding.imgPoster)
        Glide.with(this)
            .load(movie?.backdropURL)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(binding.imgBackdrop)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.appbar_options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorite -> {
                moveToFavoritePage()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun moveToFavoritePage() {
        val intent = Intent(this@MovieDetailsActivity, FavoriteActivity::class.java)
        startActivity(intent)
    }
}