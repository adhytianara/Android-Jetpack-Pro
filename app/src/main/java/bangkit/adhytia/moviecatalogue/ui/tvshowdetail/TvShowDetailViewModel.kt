package bangkit.adhytia.moviecatalogue.ui.tvshowdetail

import androidx.lifecycle.ViewModel
import bangkit.adhytia.moviecatalogue.data.TvShowEntity
import bangkit.adhytia.moviecatalogue.repository.Repository

class TvShowDetailViewModel : ViewModel() {
    private var tvShowId: Int = -1
    lateinit var repository: Repository

    fun setSelectedTvShow(tvShowId: Int) {
        this.tvShowId = tvShowId
    }

    fun getTvShow(): TvShowEntity {
        lateinit var tvShow: TvShowEntity
        val tvShowsEntities = getTvShows()
        for (tvShowEntity in tvShowsEntities) {
            if (tvShowEntity.id == tvShowId) {
                tvShow = tvShowEntity
            }
        }
        return tvShow
    }

    fun getTvShows(): List<TvShowEntity> = repository.listTvShows
}