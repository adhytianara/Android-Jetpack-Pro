package bangkit.adhytia.moviecatalogue.ui.tvshow

import androidx.lifecycle.ViewModel
import bangkit.adhytia.moviecatalogue.data.TvShowEntity
import bangkit.adhytia.moviecatalogue.repository.Repository

class TvShowViewModel : ViewModel() {
    lateinit var repository: Repository

    fun getTvShows(): List<TvShowEntity> = repository.listTvShows
}
