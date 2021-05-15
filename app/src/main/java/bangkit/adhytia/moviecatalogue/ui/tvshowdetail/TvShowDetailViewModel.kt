package bangkit.adhytia.moviecatalogue.ui.tvshowdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bangkit.adhytia.moviecatalogue.data.TvShowEntity
import bangkit.adhytia.moviecatalogue.repository.Repository

class TvShowDetailViewModel(private val mRepository: Repository) : ViewModel() {
    private var tvShowId: Int = -1

    private val _tvShow = MutableLiveData<TvShowEntity>()
    val tvShow: LiveData<TvShowEntity> = _tvShow

    fun setSelectedTvShow(tvShowId: Int) {
        this.tvShowId = tvShowId
    }

    fun getTvShow() {
        val tvShowsEntities = mRepository.getTvShowList().value
        for (tvShowEntity in tvShowsEntities!!) {
            if (tvShowEntity.id == tvShowId) {
                _tvShow.value = tvShowEntity
            }
        }
    }

    fun getTvShowInDatabase(): LiveData<TvShowEntity> {
        return mRepository.getTvShowById(tvShowId)
    }

    fun insertTvShow(tvShow: TvShowEntity) {
        mRepository.insertTvShow(tvShow)
    }

    fun deleteTvShow(tvShow: TvShowEntity) {
        mRepository.deleteTvShow(tvShow)
    }
}