package com.github.flextasker.core.common

inline fun <T, reified R> Array<T>.mapToArray(
    crossinline mapper: T.() -> R
) = Array(size) { i -> get(i).mapper() }