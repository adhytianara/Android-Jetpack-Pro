package bangkit.adhytia.moviecatalogue.ui.moviedetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import bangkit.adhytia.moviecatalogue.R
import bangkit.adhytia.moviecatalogue.data.MovieEntity
import bangkit.adhytia.moviecatalogue.databinding.ActivityMovieDetailsBinding
import bangkit.adhytia.moviecatalogue.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra(EXTRA_MOVIE, 0)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel =
            ViewModelProvider(this, factory)[MovieDetailViewModel::class.java]

        viewModel.setSelectedMovie(movieId)

        viewModel.movie.observe(this, { movie ->
            populateMovie(movie)
        })

        viewModel.getMovie()
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
}