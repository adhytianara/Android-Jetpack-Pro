package bangkit.adhytia.moviecatalogue.di

import android.content.Context
import bangkit.adhytia.moviecatalogue.data.DataSource
import bangkit.adhytia.moviecatalogue.repository.Repository
import bangkit.adhytia.moviecatalogue.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): Repository {

        val remoteDataSource = DataSource.getInstance(JsonHelper(context))

        return Repository.getInstance(remoteDataSource)
    }
}