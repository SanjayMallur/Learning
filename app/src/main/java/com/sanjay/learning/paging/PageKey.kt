package com.sanjay.learning.paging

data class PageKey(
    val value: Int,
    val previousPageKey: Int? = null,
    val nextPageKey: Int? = null,
)
