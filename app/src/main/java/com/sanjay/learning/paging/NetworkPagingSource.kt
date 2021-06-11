package com.sanjay.learning.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

class NetworkPagingSource<ItemType : Any>(
    private val fetchPageCallback: suspend (Int) -> Response<List<ItemType>>,
    private val initialPageKey: Int,
    private val buildPageKey: (currentPage: Int, currentPageResponse: Response<List<ItemType>>) -> PageKey
) : PagingSource<Int, ItemType>() {

    override fun getRefreshKey(state: PagingState<Int, ItemType>): Int = initialPageKey

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemType> {
        val pageKeyValue = params.key ?: initialPageKey

        val callResult = runCatching { fetchPageCallback(pageKeyValue) }

        val data = callResult.getOrNull()
        val error = callResult.exceptionOrNull()

        val result = when {
            data != null -> {

                // By default, initial load size = 3 * NETWORK PAGE SIZE
                // ensure we're not requesting duplicating items at the 2nd request
//                val next = pageKeyValue + (params.loadSize / 20)

                val pageKey = buildPageKey(pageKeyValue, data)
                LoadResult.Page(
                    data = data.result,
                    prevKey = pageKey.previousPageKey,
                    nextKey = pageKey.nextPageKey
                )
            }
            error != null -> LoadResult.Error(error)
            // This state should never happen but I'm adding it as a form of defensive programming.
            else -> LoadResult.Error(NetworkPagingSourceUnknownState())
        }

        if (result is LoadResult.Error) {
            //Log error here
        }

        return result
    }

    private class NetworkPagingSourceUnknownState : Throwable()
}
