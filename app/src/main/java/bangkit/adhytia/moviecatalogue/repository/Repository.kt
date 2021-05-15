package bangkit.adhytia.moviecatalogue.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import bangkit.adhytia.moviecatalogue.data.DataSource
import bangkit.adhytia.moviecatalogue.data.LocalDataSource
import bangkit.adhytia.moviecatalogue.data.MovieEntity
import bangkit.adhytia.moviecatalogue.data.TvShowEntity

class Repository(private val dataSource: DataSource, private val localDataSource: LocalDataSource) {

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(dataSource: DataSource, localDataSource: LocalDataSource): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(dataSource, localDataSource).apply { instance = this }
            }
    }

    fun getMovieList(): LiveData<List<MovieEntity>> {
        val movieList = dataSource.getMovieList()
        val movieResults = MutableLiveData<List<MovieEntity>>()
        movieResults.value = movieList
        return movieResults
    }

    fun getTvShowList(): LiveData<List<TvShowEntity>> {
        val tvShowList = dataSource.getTvShowList()
        val tvShowResults = MutableLiveData<List<TvShowEntity>>()
        tvShowResults.value = tvShowList
        return tvShowResults
    }

    fun insertMovie(movie: MovieEntity) {
        localDataSource.insertMovie(movie)
    }

    fun deleteMovie(movie: MovieEntity) {
        localDataSource.deleteMovie(movie)
    }

    fun getAllMovies(): LiveData<PagedList<MovieEntity>> {
        return LivePagedListBuilder(localDataSource.getAllMovies(), 10).build()
    }

    fun getMovieById(id: Int): LiveData<MovieEntity> {
        return localDataSource.getMovieById(id)
    }

    fun insertTvShow(tvShow: TvShowEntity) {
        localDataSource.insertTvShow(tvShow)
    }

    fun deleteTvShow(tvShow: TvShowEntity) {
        localDataSource.deleteTvShow(tvShow)
    }

    fun getAllTvShows(): LiveData<PagedList<TvShowEntity>> {
        return LivePagedListBuilder(localDataSource.getAllTvShows(), 10).build()
    }

    fun getTvShowById(id: Int): LiveData<TvShowEntity> {
        return localDataSource.getTvShowById(id)
    }
}