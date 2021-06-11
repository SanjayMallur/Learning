package com.sanjay.learning.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig

class PagerFactory {
    fun <ItemType : Any> createOffsetPager(
        fetchPageCallback: suspend (Int) -> Response<List<ItemType>>,
        pageSize: Int = DEFAULT_PAGE_SIZE,
    ) = Pager(
        config = PagingConfig(pageSize = pageSize),
        pagingSourceFactory = { offsetPagingSource(fetchPageCallback) }
    )

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }
}
