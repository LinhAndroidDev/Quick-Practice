package com.example.quickpractice.data.response

abstract class BaseResponse<T> {
    abstract val data: T?
    val message: String? = null
    val status: Int? = null
}