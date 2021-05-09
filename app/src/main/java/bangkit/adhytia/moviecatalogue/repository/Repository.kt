package bangkit.adhytia.moviecatalogue.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bangkit.adhytia.moviecatalogue.data.DataSource
import bangkit.adhytia.moviecatalogue.data.MovieEntity
import bangkit.adhytia.moviecatalogue.data.TvShowEntity

class Repository(private val dataSource: DataSource) {

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(dataSource: DataSource): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(dataSource).apply { instance = this }
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
}