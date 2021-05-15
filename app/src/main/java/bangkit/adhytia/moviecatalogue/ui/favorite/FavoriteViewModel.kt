package bangkit.adhytia.moviecatalogue.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import bangkit.adhytia.moviecatalogue.data.MovieEntity
import bangkit.adhytia.moviecatalogue.data.TvShowEntity
import bangkit.adhytia.moviecatalogue.repository.Repository

class FavoriteViewModel(private val mRepository: Repository) : ViewModel() {

    fun getAllMovies(): LiveData<PagedList<MovieEntity>> = mRepository.getAllMovies()

    fun getAllTvShows(): LiveData<PagedList<TvShowEntity>> = mRepository.getAllTvShows()
}