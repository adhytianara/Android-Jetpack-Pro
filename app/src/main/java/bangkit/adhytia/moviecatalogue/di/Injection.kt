package bangkit.adhytia.moviecatalogue.di

import android.content.Context
import bangkit.adhytia.moviecatalogue.data.DataSource
import bangkit.adhytia.moviecatalogue.data.LocalDataSource
import bangkit.adhytia.moviecatalogue.data.MovieCatalogueDatabase
import bangkit.adhytia.moviecatalogue.repository.Repository
import bangkit.adhytia.moviecatalogue.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): Repository {
        val db = MovieCatalogueDatabase.getInstance(context)
        val mMovieDao = db.movieDao()
        val mTvShowDao = db.tvShowDao()

        val remoteDataSource = DataSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(mMovieDao, mTvShowDao)

        return Repository.getInstance(remoteDataSource, localDataSource)
    }
}