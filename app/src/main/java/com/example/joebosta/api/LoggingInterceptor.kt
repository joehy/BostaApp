package com.example.joebosta.api

import okhttp3.Interceptor
import okhttp3.Response

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url().toString()
        val method = request.method()
        val start = System.nanoTime()

        // Log the URL and other details
        println("Sending $method request to $url")

        val response = chain.proceed(request)

        val end = System.nanoTime()
        val duration = (end - start) / 1e6
        println("Received response for $url in $duration ms")

        return response
    }
}
