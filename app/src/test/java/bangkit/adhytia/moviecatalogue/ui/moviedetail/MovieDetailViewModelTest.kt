package bangkit.adhytia.moviecatalogue.ui.moviedetail

import bangkit.adhytia.moviecatalogue.data.MovieEntity
import bangkit.adhytia.moviecatalogue.repository.Repository
import bangkit.adhytia.moviecatalogue.utils.Constants
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class MovieDetailViewModelTest {
    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var repository: Repository
    private lateinit var dummyMovie: MovieEntity
    private lateinit var dummyMovieEntities: ArrayList<MovieEntity>

    @Before
    fun setUp() {
        repository = Mockito.mock(Repository::class.java)

        viewModel = MovieDetailViewModel()
        viewModel.repository = repository

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
    fun getMovies() {
        `when`(viewModel.getMovies()).thenReturn(dummyMovieEntities)
        viewModel.setSelectedMovie(dummyMovie.id)
        val movieEntity = viewModel.getMovie()

        verify(repository).listMovies
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
    }
}