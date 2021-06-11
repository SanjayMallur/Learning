package com.sanjay.learning.fixture

fun Any.setField(name: String, value: Any?) {
    this::class.java.getDeclaredField(name).apply {
        isAccessible = true
        set(this@setField, value)
    }
}
