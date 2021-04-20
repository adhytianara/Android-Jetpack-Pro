package bangkit.adhytia.moviecatalogue.ui.tvshow

import bangkit.adhytia.moviecatalogue.data.TvShowEntity
import bangkit.adhytia.moviecatalogue.repository.Repository
import bangkit.adhytia.moviecatalogue.utils.Constants
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class TvShowViewModelTest {

    private lateinit var viewModel: TvShowViewModel
    private lateinit var repository: Repository
    private lateinit var dummyTvShowEntities: ArrayList<TvShowEntity>

    @Before
    fun setUp() {
        repository = Mockito.mock(Repository::class.java)

        viewModel = TvShowViewModel()
        viewModel.repository = repository

        dummyTvShowEntities = arrayListOf()

        val tvShowEntity = TvShowEntity(
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

        dummyTvShowEntities.add(tvShowEntity)
    }

    @Test
    fun getTvShows() {
        Mockito.`when`(viewModel.getTvShows()).thenReturn(dummyTvShowEntities)

        val tvShowEntities = viewModel.getTvShows()

        Mockito.verify(repository).listTvShows
        assertEquals(dummyTvShowEntities, tvShowEntities)
        assertNotNull(tvShowEntities)
        assertEquals(1, tvShowEntities.size)
    }
}