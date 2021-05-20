package bangkit.adhytia.moviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import bangkit.adhytia.moviecatalogue.data.MovieEntity
import bangkit.adhytia.moviecatalogue.repository.Repository

class MovieViewModel(private val mRepository: Repository) : ViewModel() {
    fun getMovies(): LiveData<List<MovieEntity>> = mRepository.getMovieList()
}
