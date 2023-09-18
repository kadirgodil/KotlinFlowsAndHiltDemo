package com.example.lahagorapracticaltask.model

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?
) {
    companion object {

        //In case of Success,set status as Success and data as the response
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        //In case of failure ,set state to Error ,add the error message,set data to null
        fun <T> error(msg: String): Resource<T> {
            return Resource(Status.ERROR, null, msg)
        }

        //When the call is loading set the state as Loading and rest as null
        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }

    }

}