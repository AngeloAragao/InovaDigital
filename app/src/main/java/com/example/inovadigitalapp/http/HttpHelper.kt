package com.example.inovadigitalapp.http

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class HttpHelper {

    // Função POST que você já tem
    fun post(json: String): String? {
        val URL = "http://192.168.0.18:8080/inovadigital/pedidos"
        val headerHttp = "application/json; charset=utf-8".toMediaTypeOrNull()
        val client = OkHttpClient()
        val body = RequestBody.create(headerHttp, json)
        val request = Request.Builder().url(URL).post(body).build()
        val response = client.newCall(request).execute()
        return response.body?.string()
    }

    // Função GET para buscar um pedido
    fun get(url: String): String? {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        return response.body?.string()  // Retorna o corpo da resposta como String
    }
}
