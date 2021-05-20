package bangkit.adhytia.moviecatalogue.utils

import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

object PagedListUtil {
    fun <T : Any> mockPagedList(list: List<T>): androidx.paging.PagedList<T> {
        val pagedList = mock(androidx.paging.PagedList::class.java) as androidx.paging.PagedList<T>
        `when`(pagedList.size).thenReturn(list.size)

        return pagedList
    }
}