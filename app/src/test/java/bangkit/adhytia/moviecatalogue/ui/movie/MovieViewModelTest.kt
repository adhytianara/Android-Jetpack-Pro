package bangkit.adhytia.moviecatalogue.ui.movie

import bangkit.adhytia.moviecatalogue.data.MovieEntity
import bangkit.adhytia.moviecatalogue.repository.Repository
import bangkit.adhytia.moviecatalogue.utils.Constants.Companion.BASE_IMAGE_URL
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mockito.*

class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel
    private lateinit var repository: Repository
    private lateinit var dummyMovieEntities: ArrayList<MovieEntity>

    @Before
    fun setUp() {
        repository = mock(Repository::class.java)

        viewModel = MovieViewModel()
        viewModel.repository = repository

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
        `when`(viewModel.getMovies()).thenReturn(dummyMovieEntities)

        val movieEntities = viewModel.getMovies()

        verify(repository).listMovies
        assertEquals(dummyMovieEntities, movieEntities)
        assertNotNull(movieEntities)
        assertEquals(1, movieEntities.size)
    }
}