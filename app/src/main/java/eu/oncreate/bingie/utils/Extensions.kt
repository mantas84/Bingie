package eu.oncreate.bingie.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyRecyclerView
import eu.oncreate.bingie.data.PagedResponse
import retrofit2.Response

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun EpoxyRecyclerView.addOnEndReachListener(lastVisiblePlus: Int = 1, listener: () -> Unit) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val layoutManager = LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
            val totalItemCount = layoutManager!!.itemCount
            val lastVisible = layoutManager.findLastVisibleItemPosition()

            val endHasBeenReached = lastVisible + lastVisiblePlus >= totalItemCount
            if (totalItemCount > 0 && endHasBeenReached) {
                listener()
            }
        }
    })
}

fun <T> Response<T>.toPaged(): PagedResponse<T> {
    return if (this.isSuccessful.not()) {
        PagedResponse(error = this.errorBody()?.string() ?: "ERROR")
    } else {
        val headers = this.headers()
        PagedResponse(
            content = body(),
            page = headers.get("X-Pagination-Page")?.toIntOrNull() ?: 1,
            limit = headers.get("X-Pagination-Limit")?.toIntOrNull() ?: Constants.itemsPerPage,
            totalPages = headers.get("X-Pagination-Page-Count")?.toIntOrNull() ?: 0,
            totalItems = headers.get("X-Pagination-Item-Count")?.toIntOrNull() ?: 0
        )
    }
}
