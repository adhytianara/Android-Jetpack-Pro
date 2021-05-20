package bangkit.adhytia.moviecatalogue.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import bangkit.adhytia.moviecatalogue.data.DataSource
import bangkit.adhytia.moviecatalogue.data.LocalDataSource
import bangkit.adhytia.moviecatalogue.data.MovieEntity
import bangkit.adhytia.moviecatalogue.data.TvShowEntity
import bangkit.adhytia.moviecatalogue.utils.Constants
import bangkit.adhytia.moviecatalogue.utils.LiveDataTestUtil
import bangkit.adhytia.moviecatalogue.utils.PagedListUtil
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
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
    private lateinit var movieEntity: MovieEntity
    private lateinit var dummyTvShowEntities: ArrayList<TvShowEntity>
    private lateinit var tvShowEntity: TvShowEntity

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
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
    fun getMovieList() {
        val dataSource = mock(DataSource::class.java)
        val localDataSource = mock(LocalDataSource::class.java)
        val repository = Repository(dataSource, localDataSource)

        `when`(dataSource.getMovieList()).thenReturn(dummyMovieEntities)
        val movieEntities = LiveDataTestUtil.getValue(repository.getMovieList())

        verify(dataSource).getMovieList()

        Assert.assertNotNull(movieEntities)
        assertEquals(dummyMovieEntities.size.toLong(), movieEntities.size.toLong())
    }

    @Test
    fun getTvShowList() {
        val dataSource = mock(DataSource::class.java)
        val localDataSource = mock(LocalDataSource::class.java)
        val repository = Repository(dataSource, localDataSource)

        `when`(dataSource.getTvShowList()).thenReturn(dummyTvShowEntities)
        val tvShowEntities = LiveDataTestUtil.getValue(repository.getTvShowList())

        verify(dataSource).getTvShowList()

        Assert.assertNotNull(tvShowEntities)
        assertEquals(dummyTvShowEntities.size.toLong(), tvShowEntities.size.toLong())
    }

    @Test
    fun insertMovie() {
        val dataSource = mock(DataSource::class.java)
        val localDataSource = mock(LocalDataSource::class.java)
        val repository = Repository(dataSource, localDataSource)

        doNothing().`when`(localDataSource).insertMovie(movieEntity)
        val insert = repository.insertMovie(movieEntity)

        verify(localDataSource).insertMovie(movieEntity)

        assertEquals(insert, Unit)
    }

    @Test
    fun insertTvShow() {
        val dataSource = mock(DataSource::class.java)
        val localDataSource = mock(LocalDataSource::class.java)
        val repository = Repository(dataSource, localDataSource)

        doNothing().`when`(localDataSource).insertTvShow(tvShowEntity)
        val insert = repository.insertTvShow(tvShowEntity)

        verify(localDataSource).insertTvShow(tvShowEntity)

        assertEquals(insert, Unit)
    }

    @Test
    fun deleteMovie() {
        val dataSource = mock(DataSource::class.java)
        val localDataSource = mock(LocalDataSource::class.java)
        val repository = Repository(dataSource, localDataSource)

        doNothing().`when`(localDataSource).deleteMovie(movieEntity)
        val delete = repository.deleteMovie(movieEntity)

        verify(localDataSource).deleteMovie(movieEntity)

        assertEquals(delete, Unit)
    }

    @Test
    fun deleteTvShow() {
        val dataSource = mock(DataSource::class.java)
        val localDataSource = mock(LocalDataSource::class.java)
        val repository = Repository(dataSource, localDataSource)

        doNothing().`when`(localDataSource).deleteTvShow(tvShowEntity)
        val delete = repository.deleteTvShow(tvShowEntity)

        verify(localDataSource).deleteTvShow(tvShowEntity)

        assertEquals(delete, Unit)
    }

    @Test
    fun getAllMovies() {
        val dataSource = mock(DataSource::class.java)
        val localDataSource = mock(LocalDataSource::class.java)
        val repository = Repository(dataSource, localDataSource)

        val dataSourceFactory =
            mock(androidx.paging.DataSource.Factory::class.java) as androidx.paging.DataSource.Factory<Int, MovieEntity>
        `when`(localDataSource.getAllMovies()).thenReturn(dataSourceFactory)
        repository.getAllMovies()

        val movies = PagedListUtil.mockPagedList(dummyMovieEntities)

        verify(localDataSource).getAllMovies()
        assertNotNull(movies)
        assertEquals(dummyMovieEntities.size.toLong(), movies.size.toLong())
    }

    @Test
    fun getAllTvShows() {
        val dataSource = mock(DataSource::class.java)
        val localDataSource = mock(LocalDataSource::class.java)
        val repository = Repository(dataSource, localDataSource)

        val dataSourceFactory =
            mock(androidx.paging.DataSource.Factory::class.java) as androidx.paging.DataSource.Factory<Int, TvShowEntity>
        `when`(localDataSource.getAllTvShows()).thenReturn(dataSourceFactory)
        repository.getAllTvShows()

        val tvShows = PagedListUtil.mockPagedList(dummyTvShowEntities)

        verify(localDataSource).getAllTvShows()
        assertNotNull(tvShows)
        assertEquals(dummyTvShowEntities.size.toLong(), tvShows.size.toLong())
    }

    @Test
    fun getMovieById() {
        val dataSource = mock(DataSource::class.java)
        val localDataSource = mock(LocalDataSource::class.java)
        val repository = Repository(dataSource, localDataSource)

        val movie = MutableLiveData<MovieEntity>()
        movie.value = movieEntity

        `when`(localDataSource.getMovieById(movieEntity.id)).thenReturn(movie)
        val movieRes = LiveDataTestUtil.getValue(repository.getMovieById(movieEntity.id))

        verify(localDataSource).getMovieById(movieEntity.id)

        Assert.assertNotNull(movieRes)
        assertEquals(movieEntity, movieRes)
    }

    @Test
    fun getTvShowById() {
        val dataSource = mock(DataSource::class.java)
        val localDataSource = mock(LocalDataSource::class.java)
        val repository = Repository(dataSource, localDataSource)

        val tvShow = MutableLiveData<TvShowEntity>()
        tvShow.value = tvShowEntity

        `when`(localDataSource.getTvShowById(tvShowEntity.id)).thenReturn(tvShow)
        val tvShowRes = LiveDataTestUtil.getValue(repository.getTvShowById(tvShowEntity.id))

        verify(localDataSource).getTvShowById(tvShowEntity.id)

        Assert.assertNotNull(tvShowRes)
        assertEquals(tvShowEntity, tvShowRes)
    }
}