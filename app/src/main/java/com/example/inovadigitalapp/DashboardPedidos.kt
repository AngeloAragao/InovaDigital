package com.example.inovadigitalapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inovadigitalapp.http.HttpHelper
import com.example.inovadigitalapp.model.Pedido
import com.google.gson.Gson

class DashboardPedidos : AppCompatActivity() {

    private lateinit var adapter: StatusAdapter // Tornamos o adapter acessível na classe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_pedidos)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerCards)
        adapter = StatusAdapter(emptyList()) // Cria com lista vazia
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val httpHelper = HttpHelper()

        // Requisição na thread adequada para buscar os pedidos
        Thread {
            val json = httpHelper.getPedidos()
            json?.let {
                val gson = Gson()
                val listaPedidos = gson.fromJson(it, Array<Pedido>::class.java).toList()

                // Contagem dos pedidos por status
                val statusContagem = listaPedidos.groupingBy { it.statusPedido }.eachCount()

                // Atualizar a UI na thread principal
                runOnUiThread {
                    val listaCards = statusContagem.toList()
                    adapter.updateData(listaCards) // Atualiza os dados no adapter
                }
            }
        }.start()
    }
}
