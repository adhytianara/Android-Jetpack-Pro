package bangkit.adhytia.moviecatalogue.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bangkit.adhytia.moviecatalogue.data.MovieEntity
import bangkit.adhytia.moviecatalogue.repository.Repository

class MovieDetailViewModel(private val mRepository: Repository) : ViewModel() {
    private var movieId: Int = -1

    private val _movie = MutableLiveData<MovieEntity>()
    val movie: LiveData<MovieEntity> = _movie

    fun setSelectedMovie(movieId: Int) {
        this.movieId = movieId
    }

    fun getMovie() {
        val moviesEntities = mRepository.getMovieList().value
        for (movieEntity in moviesEntities!!) {
            if (movieEntity.id == movieId) {
                _movie.value = movieEntity
            }
        }
    }

    fun getMovieInDatabase(): LiveData<MovieEntity> {
        return mRepository.getMovieById(movieId)
    }

    fun insertMovie(movie: MovieEntity) {
        mRepository.insertMovie(movie)
    }

    fun deleteMovie(movie: MovieEntity) {
        mRepository.deleteMovie(movie)
    }
}