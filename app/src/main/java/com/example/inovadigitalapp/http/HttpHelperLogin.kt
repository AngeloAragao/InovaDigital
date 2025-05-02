package com.example.inovadigitalapp.http
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class HttpHelperLogin {

    fun postLogin(json: String): String? {
        val URL = "http://192.168.15.17:8080/usuario/login"
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val client = OkHttpClient()
        val body = json.toRequestBody(mediaType)

        val request = Request.Builder()
            .url(URL)
            .post(body)
            .build()

        val response = client.newCall(request).execute()
        return response.body?.string() // <- Aqui é string() e não toString()
    }
}