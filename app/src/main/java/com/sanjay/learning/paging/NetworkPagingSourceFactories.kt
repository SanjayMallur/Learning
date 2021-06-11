package com.sanjay.learning.paging

import androidx.paging.PagingSource

private const val OFFSET_PAGING_INITIAL_PAGE = 0

fun <ItemType : Any> offsetPagingSource(
    fetchPageCallback: suspend (Int) -> Response<List<ItemType>>
): PagingSource<Int, ItemType> = NetworkPagingSource(
    fetchPageCallback = fetchPageCallback,
    initialPageKey = OFFSET_PAGING_INITIAL_PAGE,
    buildPageKey = { currentPage, extra ->
        PageKey(
            value = currentPage,
            previousPageKey = null,
            nextPageKey = if (extra.next != null) currentPage+1 else null
        )
    }
)
