package bangkit.adhytia.moviecatalogue.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import bangkit.adhytia.moviecatalogue.data.MovieEntity
import bangkit.adhytia.moviecatalogue.data.TvShowEntity
import bangkit.adhytia.moviecatalogue.repository.Repository
import bangkit.adhytia.moviecatalogue.utils.Constants
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {
    private lateinit var viewModel: FavoriteViewModel

    private lateinit var dummyMovieEntities: ArrayList<MovieEntity>
    private lateinit var movieEntity: MovieEntity
    private lateinit var dummyTvShowEntities: ArrayList<TvShowEntity>
    private lateinit var tvShowEntity: TvShowEntity

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var movieObserver: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var moviePagedList: PagedList<MovieEntity>

    @Mock
    private lateinit var tvShowObserver: Observer<PagedList<TvShowEntity>>

    @Mock
    private lateinit var tvShowPagedList: PagedList<TvShowEntity>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = FavoriteViewModel(repository)

        dummyMovieEntities = arrayListOf()
        movieEntity = MovieEntity(
            399566,
            "Godzilla vs. Kong",
            "In a time when monsters walk the Earth, humanity’s ....",
            "${Constants.BASE_IMAGE_URL}/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg",
            "${Constants.BASE_IMAGE_URL}/inJjDhCjfhh3RtrJWBmmDqeuSYC.jpg",
            "2021-03-24",
            "8.3",
            "4676",
            "6159.271"
        )
        dummyMovieEntities.add(movieEntity)

        dummyTvShowEntities = arrayListOf()
        tvShowEntity = TvShowEntity(
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
    fun getAllMovies() {
        val dummyMovies = moviePagedList
        `when`(dummyMovies.size).thenReturn(5)
        val movies = MutableLiveData<PagedList<MovieEntity>>()
        movies.value = dummyMovies

        `when`(repository.getAllMovies()).thenReturn(movies)
        val movieEntities = viewModel.getAllMovies().value
        verify(repository).getAllMovies()
        assertNotNull(movieEntities)
        assertEquals(5, movieEntities?.size)

        viewModel.getAllMovies().observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovies)
    }

    @Test
    fun getAllTvShows() {
        val dummyTvShows = tvShowPagedList
        `when`(dummyTvShows.size).thenReturn(5)
        val tvShows = MutableLiveData<PagedList<TvShowEntity>>()
        tvShows.value = dummyTvShows

        `when`(repository.getAllTvShows()).thenReturn(tvShows)
        val tvShowEntities = viewModel.getAllTvShows().value
        verify(repository).getAllTvShows()
        assertNotNull(tvShowEntities)
        assertEquals(5, tvShowEntities?.size)

        viewModel.getAllTvShows().observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dummyTvShows)
    }
}