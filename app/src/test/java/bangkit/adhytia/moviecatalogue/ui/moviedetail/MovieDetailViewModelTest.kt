package bangkit.adhytia.moviecatalogue.ui.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import bangkit.adhytia.moviecatalogue.data.MovieEntity
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
class MovieDetailViewModelTest {
    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var dummyMovie: MovieEntity
    private lateinit var dummyMovieEntities: ArrayList<MovieEntity>

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<MovieEntity>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel(repository)

        dummyMovieEntities = arrayListOf()

        dummyMovie = MovieEntity(
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

        dummyMovieEntities.add(dummyMovie)
    }

    @Test
    fun getMovie() {
        val movies = MutableLiveData<List<MovieEntity>>()
        movies.value = dummyMovieEntities

        `when`(repository.getMovieList()).thenReturn(movies)
        viewModel.setSelectedMovie(dummyMovie.id)
        viewModel.getMovie()
        val movieEntity = viewModel.movie.value!!

        verify(repository).getMovieList()
        assertNotNull(movieEntity)
        assertEquals(dummyMovie.id, movieEntity.id)
        assertEquals(dummyMovie.title, movieEntity.title)
        assertEquals(dummyMovie.releaseDate, movieEntity.releaseDate)
        assertEquals(dummyMovie.popularity, movieEntity.popularity)
        assertEquals(dummyMovie.voteAverage, movieEntity.voteAverage)
        assertEquals(dummyMovie.voteCount, movieEntity.voteCount)
        assertEquals(dummyMovie.overview, movieEntity.overview)
        assertEquals(dummyMovie.backdropURL, movieEntity.backdropURL)
        assertEquals(dummyMovie.posterURL, movieEntity.posterURL)

        viewModel.movie.observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }
}