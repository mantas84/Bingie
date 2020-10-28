package eu.oncreate.bingie.data

data class PagedResponse<T>(
    val content: T? = null,
    val page: Int? = null,
    val limit: Int? = null,
    val totalPages: Int? = null,
    val totalItems: Int? = null,
    val error: String? = null
) {
    val hasNext = totalPages ?: 0 > page ?: 0
}
