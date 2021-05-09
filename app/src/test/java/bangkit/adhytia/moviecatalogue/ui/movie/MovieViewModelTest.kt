package bangkit.adhytia.moviecatalogue.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import bangkit.adhytia.moviecatalogue.data.MovieEntity
import bangkit.adhytia.moviecatalogue.repository.Repository
import bangkit.adhytia.moviecatalogue.utils.Constants.Companion.BASE_IMAGE_URL
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
class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel
    private lateinit var dummyMovieEntities: ArrayList<MovieEntity>

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<List<MovieEntity>>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = MovieViewModel(repository)
        dummyMovieEntities = arrayListOf()

        val movieEntity = MovieEntity(
            399566,
            "Godzilla vs. Kong",
            "In a time when monsters walk the Earth, humanity’s ....",
            "$BASE_IMAGE_URL/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg",
            "$BASE_IMAGE_URL/inJjDhCjfhh3RtrJWBmmDqeuSYC.jpg",
            "2021-03-24",
            "8.3",
            "4676",
            "6159.271"
        )

        dummyMovieEntities.add(movieEntity)
    }

    @Test
    fun getMovies() {
        val movies = MutableLiveData<List<MovieEntity>>()
        movies.value = dummyMovieEntities

        `when`(repository.getMovieList()).thenReturn(movies)
        val movieEntities = viewModel.getMovies().value
        verify(repository).getMovieList()
        assertEquals(dummyMovieEntities, movieEntities)
        assertNotNull(movieEntities)
        assertEquals(1, movieEntities?.size)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovieEntities)
    }
}