package bangkit.adhytia.moviecatalogue.ui.tvshowdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bangkit.adhytia.moviecatalogue.R
import bangkit.adhytia.moviecatalogue.data.TvShowEntity
import bangkit.adhytia.moviecatalogue.databinding.ActivityTvShowDetailsBinding
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

        val tvShow = intent.getParcelableExtra<TvShowEntity>(EXTRA_TVSHOW)
        title = tvShow?.title

        populateTvShow(tvShow)
    }

    private fun populateTvShow(tvShow: TvShowEntity?) {
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