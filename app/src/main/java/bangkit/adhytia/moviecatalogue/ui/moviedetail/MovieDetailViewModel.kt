package bangkit.adhytia.moviecatalogue.ui.moviedetail

import androidx.lifecycle.ViewModel
import bangkit.adhytia.moviecatalogue.data.MovieEntity
import bangkit.adhytia.moviecatalogue.repository.Repository

class MovieDetailViewModel : ViewModel() {
    private var movieId: Int = -1
    lateinit var repository: Repository

    fun setSelectedMovie(movieId: Int) {
        this.movieId = movieId
    }

    fun getMovie(): MovieEntity {
        var movie: MovieEntity? = null
        val moviesEntities = getMovies()
        for (movieEntity in moviesEntities) {
            if (movieEntity.id == movieId) {
                movie = movieEntity
            }
        }
        return movie!!
    }

    fun getMovies(): List<MovieEntity> = repository.listMovies
}