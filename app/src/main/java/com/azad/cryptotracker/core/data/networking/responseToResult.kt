package com.azad.cryptotracker.core.data.networking

import com.azad.cryptotracker.core.domain.util.NetworkError
import com.azad.cryptotracker.core.domain.util.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse


//Utility function to return/map proper error type to UI
//It is a suspend function as it will process the success responded data
//inline is used for reified and reified is used to make this function available at runtime not at compile time
suspend inline fun <reified T> responseToResult(response: HttpResponse):
        Result<T, NetworkError> {
    return when(response.status.value){
        //All 200 level response codes are success request
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException){
                Result.Error(NetworkError.SERIALIZATION)
            }
        }
        408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
        429 -> Result.Error(NetworkError.TOO_MANY_REQUEST)
        //All 500 level response codes are server side error
        in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
        else -> Result.Error(NetworkError.UNKNOWN)
    }
}