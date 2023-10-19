package com.example.data.extensions

import com.example.core.util.Resource
import com.example.core.ResponseModel
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

suspend fun <T : ResponseModel> handleAPICall(
    apiCall: suspend () -> Response<T>
): Resource<T> {
    return try {
        apiCall.invoke().handleAPIResponse()
    } catch (e: Exception) {
        e.printStackTrace()
        return when (e) {
            is UnknownHostException -> Resource.Failure(IOException())
            else -> Resource.Failure(IOException())
        }
    }
}

private fun <T : ResponseModel> Response<T>.handleAPIResponse(): Resource<T> {
    val responseBody = body() as T
    if (isSuccess()) {
        return Resource.Success(responseBody)
    }
    return Resource.Failure(
        IOException()
    )
}

private fun <T : ResponseModel> Response<T>.isSuccess(): Boolean = isSuccessful