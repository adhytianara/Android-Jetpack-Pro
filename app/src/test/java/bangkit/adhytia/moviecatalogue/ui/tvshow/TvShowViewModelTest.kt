package bangkit.adhytia.moviecatalogue.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import bangkit.adhytia.moviecatalogue.data.TvShowEntity
import bangkit.adhytia.moviecatalogue.repository.Repository
import bangkit.adhytia.moviecatalogue.utils.Constants
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {

    private lateinit var viewModel: TvShowViewModel
    private lateinit var dummyTvShowEntities: ArrayList<TvShowEntity>

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<List<TvShowEntity>>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(repository)

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
        val tvShows = MutableLiveData<List<TvShowEntity>>()
        tvShows.value = dummyTvShowEntities

        `when`(repository.getTvShowList()).thenReturn(tvShows)
        val tvShowEntities = viewModel.getTvShows().value
        verify(repository).getTvShowList()
        assertEquals(dummyTvShowEntities, tvShowEntities)
        assertNotNull(tvShowEntities)
        assertEquals(1, tvShowEntities?.size)

        viewModel.getTvShows().observeForever(observer)
        verify(observer).onChanged(dummyTvShowEntities)
    }
}