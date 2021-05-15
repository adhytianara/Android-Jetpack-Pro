package bangkit.adhytia.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

@Dao
interface TvShowDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tvshow: TvShowEntity)

    @Delete
    fun delete(tvshow: TvShowEntity)

    @Query("SELECT * FROM tvshow WHERE id = :id")
    fun getById(id: Int): LiveData<TvShowEntity>

    @Query("SELECT * from tvshow ORDER BY id ASC")
    fun getAllTvShows(): DataSource.Factory<Int, TvShowEntity>
}