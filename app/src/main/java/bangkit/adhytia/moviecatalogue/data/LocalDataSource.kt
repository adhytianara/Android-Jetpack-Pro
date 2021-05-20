package bangkit.adhytia.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LocalDataSource(private val mMovieDao: MovieDao, private val mTvShowDao: TvShowDao) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    companion object {
        @Volatile
        private var instance: LocalDataSource? = null

        fun getInstance(mMovieDao: MovieDao, mTvShowDao: TvShowDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(mMovieDao, mTvShowDao).apply { instance = this }
            }
    }

    fun insertMovie(movie: MovieEntity) {
        executorService.execute { mMovieDao.insert(movie) }
    }

    fun deleteMovie(movie: MovieEntity) {
        executorService.execute { mMovieDao.delete(movie) }
    }

    fun getAllMovies(): DataSource.Factory<Int, MovieEntity> {
        return mMovieDao.getAllMovies()
    }

    fun getMovieById(id: Int): LiveData<MovieEntity> {
        return mMovieDao.getById(id)
    }

    fun insertTvShow(tvShow: TvShowEntity) {
        executorService.execute { mTvShowDao.insert(tvShow) }
    }

    fun deleteTvShow(tvShow: TvShowEntity) {
        executorService.execute { mTvShowDao.delete(tvShow) }
    }

    fun getAllTvShows(): DataSource.Factory<Int, TvShowEntity> {
        return mTvShowDao.getAllTvShows()
    }

    fun getTvShowById(id: Int): LiveData<TvShowEntity> {
        return mTvShowDao.getById(id)
    }
}