package bangkit.adhytia.moviecatalogue.ui.favorite

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bangkit.adhytia.moviecatalogue.data.MovieEntity
import bangkit.adhytia.moviecatalogue.databinding.ItemRowFavoriteBinding
import bangkit.adhytia.moviecatalogue.ui.moviedetail.MovieDetailsActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FavoriteMoviePagedListAdapter(private val activity: Activity) :
    PagedListAdapter<MovieEntity, FavoriteMoviePagedListAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<MovieEntity> =
            object : DiffUtil.ItemCallback<MovieEntity>() {
                override fun areItemsTheSame(
                    oldMovie: MovieEntity,
                    newMovie: MovieEntity
                ): Boolean {
                    return oldMovie.id == newMovie.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldMovie: MovieEntity,
                    newMovie: MovieEntity
                ): Boolean {
                    return oldMovie == newMovie
                }
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteMoviePagedListAdapter.MovieViewHolder {
        val binding =
            ItemRowFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position) as MovieEntity)
    }

    inner class MovieViewHolder(private val binding: ItemRowFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(movie.posterURL)
                    .apply(RequestOptions().override(80, 80))
                    .into(ivPicture)
                tvName.text = movie.title
                tvOverview.text = movie.overview
                itemFavorite.setOnClickListener {
                    val intent = Intent(activity, MovieDetailsActivity::class.java)
                    intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE, movie.id)
                    activity.startActivity(intent)
                }
            }
        }
    }
}