package bangkit.adhytia.moviecatalogue.ui.movie

import androidx.lifecycle.ViewModel
import bangkit.adhytia.moviecatalogue.data.MovieEntity
import bangkit.adhytia.moviecatalogue.repository.Repository

class MovieViewModel : ViewModel() {
    lateinit var repository: Repository

    fun getMovies(): List<MovieEntity> = repository.listMovies
}
