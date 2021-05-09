package bangkit.adhytia.moviecatalogue.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bangkit.adhytia.moviecatalogue.data.MovieEntity
import bangkit.adhytia.moviecatalogue.data.TvShowEntity
import bangkit.adhytia.moviecatalogue.repository.Repository

class TvShowViewModel(private val mRepository: Repository) : ViewModel() {
    fun getTvShows(): LiveData<List<TvShowEntity>> = mRepository.getTvShowList()
}
