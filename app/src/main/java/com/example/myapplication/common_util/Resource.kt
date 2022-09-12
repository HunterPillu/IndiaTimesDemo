package com.example.myapplication.common_util

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> idle(): Resource<T> {
            return Resource(Status.IDLE, null, null)
        }

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> error(data: T?): Resource<T> {
            return Resource(Status.ERROR, data, null)
        }

        fun <T> error(): Resource<T> {
            return Resource(Status.ERROR, null, null)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

    }
}