package com.azad.cryptotracker.core.data.networking

import com.azad.cryptotracker.core.domain.util.NetworkError
import com.azad.cryptotracker.core.domain.util.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext


//To execute request call and return/catch network/internet error
suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, NetworkError> {
    val response = try {
        execute()
        //To return NO INTERNET error
    } catch (e: UnresolvedAddressException){
        return Result.Error(NetworkError.NO_INTERNET)
    } catch (e: SerializationException){
        return Result.Error(NetworkError.SERIALIZATION)
        //To return all generic error
    } catch (e: Exception){
        //To avoid the coroutine cancellation exception in this generic exception catch
        coroutineContext.ensureActive()
        return Result.Error(NetworkError.UNKNOWN)
    }

    //This will return a successful network call and will return data or map response related errors
    return responseToResult(response)
}