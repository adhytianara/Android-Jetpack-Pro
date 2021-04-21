package bangkit.adhytia.moviecatalogue.ui.tvshow

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import bangkit.adhytia.moviecatalogue.data.TvShowEntity
import bangkit.adhytia.moviecatalogue.databinding.FragmentTvShowBinding
import bangkit.adhytia.moviecatalogue.repository.Repository
import bangkit.adhytia.moviecatalogue.ui.tvshowdetail.TvShowDetailsActivity
import bangkit.adhytia.moviecatalogue.ui.tvshowdetail.TvShowDetailsActivity.Companion.EXTRA_TVSHOW

class TvShowFragment : Fragment() {
    private lateinit var binding: FragmentTvShowBinding
    private lateinit var tvShowAdapter: TvShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val repository = Repository(activity!!)

            val viewModel = ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            )[TvShowViewModel::class.java]

            viewModel.repository = repository

            val tvShows = viewModel.getTvShows()
            tvShowAdapter = TvShowAdapter()
            tvShowAdapter.setTvShows(tvShows)
            showRecyclerGrid()
        }
    }

    private fun showRecyclerGrid() {
        val orientation = resources.configuration.orientation
        val spanCount = if (orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 6

        with(binding.rvTvshows) {
            layoutManager = GridLayoutManager(context, spanCount)
            setHasFixedSize(true)
            adapter = tvShowAdapter
        }

        tvShowAdapter.setOnItemClickCallback(object : TvShowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TvShowEntity) {
                Toast.makeText(context, data.title, Toast.LENGTH_LONG).show()
                val intent = Intent(activity, TvShowDetailsActivity::class.java)
                intent.putExtra(EXTRA_TVSHOW, data.id)
                startActivity(intent)
            }
        })
    }
}