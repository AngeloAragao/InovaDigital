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
import com.example.inovadigitalapp.resources.StatusAdapter
import com.example.inovadigitalapp.resources.ValorStatusAdapter
import com.google.gson.Gson

class DashboardValoresActivity : AppCompatActivity() {
    private lateinit var adapter: ValorStatusAdapter // Tornamos o adapter acessível na classe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_valores)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerCardsFinance)
        val adapter = ValorStatusAdapter(emptyList())// Cria com lista vazia
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val httpHelper = HttpHelper()

// Requisição na thread adequada para buscar os pedidos
        Thread {
            val json = httpHelper.getPedidos()
            json?.let {
                val gson = Gson()
                val listaPedidos = gson.fromJson(it, Array<Pedido>::class.java).toList()

                // Agrupando por status e somando os valores dos pedidos
                val somaPorStatus = listaPedidos
                    .groupBy { it.statusPedido }
                    .mapValues { entry ->
                        entry.value.sumOf {
                            it.valorPedido.replace(",", ".").toDoubleOrNull() ?: 0.0
                        }
                    }

                // Atualizar a UI na thread principal
                runOnUiThread {
                    val listaCards = somaPorStatus.toList()
                    adapter.updateData(somaPorStatus.toList()) // Atualiza os dados no adapter
                }
            }
        }.start()

    }
}