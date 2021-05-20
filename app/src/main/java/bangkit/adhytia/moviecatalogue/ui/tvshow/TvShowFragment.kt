package bangkit.adhytia.moviecatalogue.ui.tvshow

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
import bangkit.adhytia.moviecatalogue.data.TvShowEntity
import bangkit.adhytia.moviecatalogue.databinding.FragmentTvShowBinding
import bangkit.adhytia.moviecatalogue.ui.tvshowdetail.TvShowDetailsActivity
import bangkit.adhytia.moviecatalogue.ui.tvshowdetail.TvShowDetailsActivity.Companion.EXTRA_TVSHOW
import bangkit.adhytia.moviecatalogue.viewmodel.ViewModelFactory

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
            val factory = ViewModelFactory.getInstance(requireContext())
            val viewModel =
                ViewModelProvider(requireActivity(), factory)[TvShowViewModel::class.java]

            tvShowAdapter = TvShowAdapter()
            viewModel.getTvShows().observe(viewLifecycleOwner, { tvShows ->
                populateRecyclerView(tvShows)
            })

            viewModel.getTvShows()

            tvShowAdapter.setOnItemClickCallback(object : TvShowAdapter.OnItemClickCallback {
                override fun onItemClicked(data: TvShowEntity) {
                    val intent = Intent(activity, TvShowDetailsActivity::class.java)
                    intent.putExtra(EXTRA_TVSHOW, data.id)
                    startActivity(intent)
                }
            })
        }
    }

    private fun populateRecyclerView(tvShows: List<TvShowEntity>) {
        val orientation = resources.configuration.orientation
        val spanCount = if (orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 6

        with(binding) {
            tvShowAdapter.setTvShows(tvShows)
            tvShowAdapter.notifyDataSetChanged()
            rvTvshows.layoutManager = GridLayoutManager(context, spanCount)
            rvTvshows.setHasFixedSize(true)
            rvTvshows.adapter = tvShowAdapter
            val dividerItemDecoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            rvTvshows.addItemDecoration(dividerItemDecoration)
        }
    }
}