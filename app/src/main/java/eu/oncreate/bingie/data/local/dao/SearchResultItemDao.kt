package eu.oncreate.bingie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.oncreate.bingie.data.local.model.trakt.SearchResultItem

@Dao
interface SearchResultItemDao {

    @Query("SELECT * FROM searchresultitem WHERE traktId = :id")
    suspend fun getSearchResultItem(id: Int): List<SearchResultItem>

    @Query("SELECT * FROM searchresultitem WHERE title LIKE '%' || :query || '%'")
    suspend fun searchSearchResultItem(query: String): List<SearchResultItem>

    @Query("SELECT * FROM searchresultitem order by updateTime")
    suspend fun searchSearchResultItem(): List<SearchResultItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchResultItem(item: SearchResultItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSearchResultItem(items: List<SearchResultItem>)

    @Query("DELETE FROM searchresultitem  WHERE traktId = :id")
    suspend fun deleteSearchResultItem(id: Int)

    @Query("DELETE FROM searchresultitem")
    suspend fun deleteAllSearchResultItems()
}
