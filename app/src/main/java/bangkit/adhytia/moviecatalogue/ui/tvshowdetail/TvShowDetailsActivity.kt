package bangkit.adhytia.moviecatalogue.ui.tvshowdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import bangkit.adhytia.moviecatalogue.R
import bangkit.adhytia.moviecatalogue.data.TvShowEntity
import bangkit.adhytia.moviecatalogue.databinding.ActivityTvShowDetailsBinding
import bangkit.adhytia.moviecatalogue.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TvShowDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTvShowDetailsBinding

    companion object {
        const val EXTRA_TVSHOW = "extra_tvShow"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvShowDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tvShowId = intent.getIntExtra(EXTRA_TVSHOW, 0)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel =
            ViewModelProvider(this, factory)[TvShowDetailViewModel::class.java]

        viewModel.setSelectedTvShow(tvShowId)

        viewModel.tvShow.observe(this, { tvShow ->
            populateTvShow(tvShow)
        })

        viewModel.getTvShow()
    }

    private fun populateTvShow(tvShow: TvShowEntity?) {
        title = tvShow?.title
        binding.tvTitle.text = tvShow?.title
        (getString(R.string.first_air_date) + tvShow?.firstAirDate).also {
            binding.tvFirstAirDate.text = it
        }
        (getString(R.string.popularity) + tvShow?.popularity).also {
            binding.tvPopularity.text = it
        }
        (getString(R.string.vote_average) + tvShow?.voteAverage).also {
            binding.tvVoteAvg.text = it
        }
        (getString(R.string.vote_count) + tvShow?.voteCount).also { binding.tvVoteCount.text = it }
        binding.tvOverview.text = tvShow?.overview
        Glide.with(this)
            .load(tvShow?.posterURL)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(binding.imgPoster)
        Glide.with(this)
            .load(tvShow?.backdropURL)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(binding.imgBackdrop)
    }
}