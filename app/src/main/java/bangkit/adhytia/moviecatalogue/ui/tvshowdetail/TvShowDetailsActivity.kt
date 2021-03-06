package bangkit.adhytia.moviecatalogue.ui.tvshowdetail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import bangkit.adhytia.moviecatalogue.R
import bangkit.adhytia.moviecatalogue.data.TvShowEntity
import bangkit.adhytia.moviecatalogue.databinding.ActivityTvShowDetailsBinding
import bangkit.adhytia.moviecatalogue.ui.favorite.FavoriteActivity
import bangkit.adhytia.moviecatalogue.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TvShowDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTvShowDetailsBinding
    private lateinit var viewModel: TvShowDetailViewModel
    private lateinit var tvShow: TvShowEntity
    private var isFavorite = false
    private var isInDatabase = false

    companion object {
        const val EXTRA_TVSHOW = "extra_tvShow"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvShowDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tvShowId = intent.getIntExtra(EXTRA_TVSHOW, 0)

        val factory = ViewModelFactory.getInstance(this)
        viewModel =
            ViewModelProvider(this, factory)[TvShowDetailViewModel::class.java]

        viewModel.setSelectedTvShow(tvShowId)

        viewModel.tvShow.observe(this, { tvShow ->
            this.tvShow = tvShow
            populateTvShow(tvShow)
        })

        viewModel.getTvShowInDatabase().observe(this, { tvShowInDb ->
            isFavorite = tvShowInDb != null
            isInDatabase = tvShowInDb != null
            showButtonFavorite()
        })

        viewModel.getTvShow()

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
        viewModel.getTvShowInDatabase()
    }

    override fun onPause() {
        super.onPause()
        if (isFavorite != (isInDatabase)) {
            if (isInDatabase) {
                viewModel.deleteTvShow(tvShow)
            } else {
                viewModel.insertTvShow(tvShow)
            }
        }
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
        val intent = Intent(this@TvShowDetailsActivity, FavoriteActivity::class.java)
        startActivity(intent)
    }
}