package bangkit.adhytia.moviecatalogue.repository

import android.app.Activity
import bangkit.adhytia.moviecatalogue.data.MovieEntity
import bangkit.adhytia.moviecatalogue.data.TvShowEntity
import bangkit.adhytia.moviecatalogue.utils.Constants.Companion.BASE_IMAGE_URL
import bangkit.adhytia.moviecatalogue.utils.Constants.Companion.NO_IMAGE_URL
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

class Repository(private val activity: Activity) {

    val listMovies: ArrayList<MovieEntity>
        get() {
            val list = arrayListOf<MovieEntity>()
            try {
                val obj = JSONObject(loadJSONFromAsset("popularmovies.json"))
                val movieArray = obj.getJSONArray("results")
                for (i in 0 until movieArray.length()) {
                    val movieDetail = movieArray.getJSONObject(i)

                    var posterPath = movieDetail.getString("poster_path")
                    var backdropPath = movieDetail.getString("backdrop_path")

                    posterPath =
                        if (posterPath.isNullOrBlank()) NO_IMAGE_URL else BASE_IMAGE_URL + posterPath
                    backdropPath =
                        if (backdropPath.isNullOrBlank()) NO_IMAGE_URL else BASE_IMAGE_URL + backdropPath

                    val movie = MovieEntity(
                        movieDetail.getInt("id"),
                        movieDetail.getString("title"),
                        movieDetail.getString("overview"),
                        posterPath,
                        backdropPath,
                        movieDetail.getString("release_date"),
                        movieDetail.getString("vote_average"),
                        movieDetail.getString("vote_count"),
                        movieDetail.getString("popularity")
                    )

                    list.add(movie)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return list
        }

    val listTvShows: ArrayList<TvShowEntity>
        get() {
            val list = arrayListOf<TvShowEntity>()
            try {
                val obj = JSONObject(loadJSONFromAsset("populartvshows.json"))
                val tvshowArray = obj.getJSONArray("results")
                for (i in 0 until tvshowArray.length()) {
                    val tvshowDetail = tvshowArray.getJSONObject(i)

                    var posterPath = tvshowDetail.getString("poster_path")
                    var backdropPath = tvshowDetail.getString("backdrop_path")

                    posterPath =
                        if (posterPath.isNullOrBlank()) NO_IMAGE_URL else BASE_IMAGE_URL + posterPath
                    backdropPath =
                        if (backdropPath.isNullOrBlank()) NO_IMAGE_URL else BASE_IMAGE_URL + backdropPath

                    val tvshow = TvShowEntity(
                        tvshowDetail.getInt("id"),
                        tvshowDetail.getString("name"),
                        tvshowDetail.getString("overview"),
                        posterPath,
                        backdropPath,
                        tvshowDetail.getString("first_air_date"),
                        tvshowDetail.getString("vote_average"),
                        tvshowDetail.getString("vote_count"),
                        tvshowDetail.getString("popularity")
                    )

                    list.add(tvshow)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return list
        }

    private fun loadJSONFromAsset(filename: String): String {
        val json: String?

        try {
            val inputStream = activity.assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            val charset: Charset = Charsets.UTF_8

            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }
}