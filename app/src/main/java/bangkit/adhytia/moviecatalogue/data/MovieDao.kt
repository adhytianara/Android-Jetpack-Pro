package bangkit.adhytia.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movie: MovieEntity)

    @Delete
    fun delete(movie: MovieEntity)

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getById(id: Int): LiveData<MovieEntity>

    @Query("SELECT * from movie ORDER BY id ASC")
    fun getAllMovies(): DataSource.Factory<Int, MovieEntity>
}