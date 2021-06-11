package com.sanjay.learning.paging

data class Response<T>(
    val result: T,
    val count: Int? = null,
    val previous: String? = null,
    val next: String? = null
)
