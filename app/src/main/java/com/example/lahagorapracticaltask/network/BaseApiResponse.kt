package com.example.lahagorapracticaltask.network

import com.example.lahagorapracticaltask.model.Resource
import retrofit2.Response

class BaseApiResponse {
    companion object {
        suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let {
                        return Resource.success(body)
                    } ?: return Resource.error("Something went wrong")
                }
                return error("${response.code()} ${response.message()}")
            } catch (e: Exception) {
                return error(e.message ?: e.toString())
            }
        }

        private fun <T> error(errorMessage: String): Resource<T> =
            Resource.error("Api call failed $errorMessage")
    }


}