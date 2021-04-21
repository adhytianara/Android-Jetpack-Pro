package bangkit.adhytia.moviecatalogue.ui.tvshowdetail

import bangkit.adhytia.moviecatalogue.data.TvShowEntity
import bangkit.adhytia.moviecatalogue.repository.Repository
import bangkit.adhytia.moviecatalogue.utils.Constants
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class TvShowDetailViewModelTest {
    private lateinit var viewModel: TvShowDetailViewModel
    private lateinit var repository: Repository
    private lateinit var dummyTvShow: TvShowEntity
    private lateinit var dummyTvShowEntities: ArrayList<TvShowEntity>

    @Before
    fun setUp() {
        repository = mock(Repository::class.java)

        viewModel = TvShowDetailViewModel()
        viewModel.repository = repository

        dummyTvShowEntities = arrayListOf()

        dummyTvShow = TvShowEntity(
            88396,
            "The Falcon and the Winter Soldier",
            "Following the events of “Avengers: Endgame”, the Falcon, ....",
            "${Constants.BASE_IMAGE_URL}/6kbAMLteGO8yyewYau6bJ683sw7.jpg",
            "${Constants.BASE_IMAGE_URL}/b0WmHGc8LHTdGCVzxRb3IBMur57.jpg",
            "2021-03-19",
            "7.9",
            "4281",
            "4207.4"
        )

        dummyTvShowEntities.add(dummyTvShow)
    }

    @Test
    fun getTvShows() {
        Mockito.`when`(viewModel.getTvShows()).thenReturn(dummyTvShowEntities)
        viewModel.setSelectedTvShow(dummyTvShow.id)
        val tvShowEntity = viewModel.getTvShow()

        Mockito.verify(repository).listTvShows
        assertNotNull(tvShowEntity)
        assertEquals(dummyTvShow.id, tvShowEntity.id)
        assertEquals(dummyTvShow.title, tvShowEntity.title)
        assertEquals(dummyTvShow.firstAirDate, tvShowEntity.firstAirDate)
        assertEquals(dummyTvShow.popularity, tvShowEntity.popularity)
        assertEquals(dummyTvShow.voteAverage, tvShowEntity.voteAverage)
        assertEquals(dummyTvShow.voteCount, tvShowEntity.voteCount)
        assertEquals(dummyTvShow.overview, tvShowEntity.overview)
        assertEquals(dummyTvShow.backdropURL, tvShowEntity.backdropURL)
        assertEquals(dummyTvShow.posterURL, tvShowEntity.posterURL)
    }
}