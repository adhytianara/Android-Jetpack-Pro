package bangkit.adhytia.moviecatalogue.utils

import android.content.Context
import java.io.IOException
import java.nio.charset.Charset

class JsonHelper(private val context: Context) {
    fun loadJSONFromAsset(filename: String): String {
        val json: String?

        try {
            val inputStream = context.assets.open(filename)
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