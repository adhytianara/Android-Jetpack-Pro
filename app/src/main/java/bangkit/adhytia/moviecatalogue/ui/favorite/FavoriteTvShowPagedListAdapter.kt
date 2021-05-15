package bangkit.adhytia.moviecatalogue.ui.favorite

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bangkit.adhytia.moviecatalogue.data.TvShowEntity
import bangkit.adhytia.moviecatalogue.databinding.ItemRowFavoriteBinding
import bangkit.adhytia.moviecatalogue.ui.tvshowdetail.TvShowDetailsActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FavoriteTvShowPagedListAdapter(private val activity: Activity) :
    PagedListAdapter<TvShowEntity, FavoriteTvShowPagedListAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<TvShowEntity> =
            object : DiffUtil.ItemCallback<TvShowEntity>() {
                override fun areItemsTheSame(
                    oldTvShow: TvShowEntity,
                    newTvShow: TvShowEntity
                ): Boolean {
                    return oldTvShow.id == newTvShow.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldTvShow: TvShowEntity,
                    newTvShow: TvShowEntity
                ): Boolean {
                    return oldTvShow == newTvShow
                }
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteTvShowPagedListAdapter.TvShowViewHolder {
        val binding =
            ItemRowFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        holder.bind(getItem(position) as TvShowEntity)
    }

    inner class TvShowViewHolder(private val binding: ItemRowFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShowEntity) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(tvShow.posterURL)
                    .apply(RequestOptions().override(80, 80))
                    .into(ivPicture)
                tvName.text = tvShow.title
                tvOverview.text = tvShow.overview
                itemFavorite.setOnClickListener {
                    val intent = Intent(activity, TvShowDetailsActivity::class.java)
                    intent.putExtra(TvShowDetailsActivity.EXTRA_TVSHOW, tvShow.id)
                    activity.startActivity(intent)
                }
            }
        }
    }
}