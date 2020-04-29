package com.ascri.koinsample.utils

sealed class NetResponse<out T : Any> {
    class Success<out T : Any>(val data: T) : NetResponse<T>()
    class Error(val exception: Throwable) : NetResponse<Nothing>()
}