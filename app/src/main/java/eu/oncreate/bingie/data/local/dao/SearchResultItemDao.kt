package eu.oncreate.bingie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.oncreate.bingie.data.local.model.trakt.SearchResultItem
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface SearchResultItemDao {

    @Query("SELECT * FROM searchresultitem WHERE traktId = :id")
    fun getSearchResultItem(id: Int): Single<List<SearchResultItem>>

    @Query("SELECT * FROM searchresultitem WHERE title LIKE :query")
    fun searchSearchResultItem(query: String): Single<List<SearchResultItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchResultItem(item: SearchResultItem): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSearchResultItem(items: List<SearchResultItem>): Completable

    @Query("DELETE FROM searchresultitem")
    fun deleteAllSearchResultItems()
}
