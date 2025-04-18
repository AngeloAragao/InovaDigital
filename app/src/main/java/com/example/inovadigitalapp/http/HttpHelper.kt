package com.example.inovadigitalapp.http


// Importação necessária
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class HttpHelper {

    fun post (json: String) : String? {

        // Definir URL do servidor
        val URL = "http://192.168.0.18:8080/inovadigital/pedidos"

        // Definir o cabeçalho
        val headerHttp = "application/json; charset=utf-8".toMediaTypeOrNull()

        // Criar o cliente que vai disparar a requisição
        val client = OkHttpClient()

        // Body da requisição
        val body = RequestBody.create(headerHttp, json)

        // Criar uma requisicao POST
        var request = Request.Builder().url(URL).post(body).build()

        // Utilizar o client para fazer a requisicao e receber a resposta
        val response = client.newCall(request).execute()

        return response.body?.toString()

    }
}