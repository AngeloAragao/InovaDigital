package com.example.inovadigitalapp.resources

import android.os.Handler
import android.os.Looper
import com.example.inovadigitalapp.http.HttpHelper
import com.example.inovadigitalapp.model.Pedido
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PedidoService {

    fun buscarPedidosNotificacao(callback: (List<Pedido>) -> Unit) {
        // Inicia um novo thread para a requisição de rede
        Thread {
            try {
                // Cria uma instância de HttpHelper
                val httpHelper = HttpHelper()

                // Chama o método da instância para buscar os pedidos da API
                val json = httpHelper.buscarPedidosNotificacao()

                // Fazendo o parsing da resposta JSON para uma lista de pedidos
                val pedidos = json?.let {
                    val listType = object : TypeToken<List<Pedido>>() {}.type
                    Gson().fromJson<List<Pedido>>(it, listType)
                } ?: emptyList()

                // Chamando o callback com os pedidos recebidos
                Handler(Looper.getMainLooper()).post {
                    callback(pedidos)
                }
            } catch (e: Exception) {
                // Caso ocorra algum erro, chamando o callback com uma lista vazia
                Handler(Looper.getMainLooper()).post {
                    callback(emptyList())
                }
            }
        }.start()
    }
}