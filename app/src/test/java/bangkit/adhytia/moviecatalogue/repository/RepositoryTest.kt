package bangkit.adhytia.moviecatalogue.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import bangkit.adhytia.moviecatalogue.data.DataSource
import bangkit.adhytia.moviecatalogue.data.MovieEntity
import bangkit.adhytia.moviecatalogue.data.TvShowEntity
import bangkit.adhytia.moviecatalogue.utils.Constants
import bangkit.adhytia.moviecatalogue.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    private lateinit var dummyMovieEntities: ArrayList<MovieEntity>
    private lateinit var dummyTvShowEntities: ArrayList<TvShowEntity>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        dummyMovieEntities = arrayListOf()
        val movieEntity = MovieEntity(
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
    fun getMovieList() {
        val dataSource = mock(DataSource::class.java)
        val repository = Repository(dataSource)

        `when`(dataSource.getMovieList()).thenReturn(dummyMovieEntities)
        val movieEntities = LiveDataTestUtil.getValue(repository.getMovieList())

        verify(dataSource).getMovieList()

        Assert.assertNotNull(movieEntities)
        assertEquals(dummyMovieEntities.size.toLong(), movieEntities.size.toLong())
    }

    @Test
    fun getTvShowList() {
        val dataSource = mock(DataSource::class.java)
        val repository = Repository(dataSource)

        `when`(dataSource.getTvShowList()).thenReturn(dummyTvShowEntities)
        val tvShowEntities = LiveDataTestUtil.getValue(repository.getTvShowList())

        verify(dataSource).getTvShowList()

        Assert.assertNotNull(tvShowEntities)
        assertEquals(dummyTvShowEntities.size.toLong(), tvShowEntities.size.toLong())
    }
}