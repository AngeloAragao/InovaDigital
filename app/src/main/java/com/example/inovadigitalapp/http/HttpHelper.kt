package com.example.inovadigitalapp.http

import com.example.inovadigitalapp.model.Pedido
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class HttpHelper {

    private val client = OkHttpClient()

    fun getPedidos(): String? {
        val url = "http://172.20.10.2:8080/inovadigital/pedidos"

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        val response = client.newCall(request).execute()
        return response.body?.string() // pega o corpo da resposta como string
    }

    // Método para pegar pedidos filtrados por status
    fun getPedidosPorStatus(status: String): String? {
        // Modifique a URL de acordo com a API que filtra por status
        val url = "http://172.20.10.2:8080/inovadigital/pedidos?status=$status"

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        val response = client.newCall(request).execute()
        return response.body?.string() // Retorna a resposta da API como string
    }

    // Método para atualizar o status do pedido
    fun atualizarStatusPedido(pedidoId: Long, novoStatus: String): Boolean {
        val url = "http://172.20.10.2:8080/inovadigital/pedidos/$pedidoId"  // Passando o ID na URL

        // Criar o JSON com o novo status
        val json = JSONObject().apply {
            put("statusPedido", novoStatus)
        }

        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json.toString())

        val request = Request.Builder()
            .url(url)
            .put(body) // Usando PUT para atualizar
            .build()

        return try {
            val response = client.newCall(request).execute()
            response.isSuccessful // Retorna se a requisição foi bem-sucedida
        } catch (e: Exception) {
            false // Em caso de erro, retorna falso
        }
    }



    fun post (json: String) : String? {

        // Definir URL do servidor
        val URL = "http://172.20.10.2:8080/inovadigital/pedidos"

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

    fun cadastrarUsuario(json: String): String? {
        val url = "http://172.20.10.2:8080/usuario/cadastrar" // ajuste se o endpoint for diferente
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val client = OkHttpClient()

        val body = json.toRequestBody(mediaType)

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        val response = client.newCall(request).execute()
        return response.body?.string() // Corrigido: não use `.toString()` aqui
    }

    fun buscarPedidosNotificacao(): String? {
        val url = "http://172.20.10.2:8080/inovadigital/pedidos/notificacoes" // ajuste conforme necessário

        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        val response = client.newCall(request).execute()
        return response.body?.string()
    }



}


