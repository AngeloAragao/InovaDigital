package com.example.inovadigitalapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inovadigitalapp.http.HttpHelper
import com.example.inovadigitalapp.model.Pedido
import com.example.inovadigitalapp.resources.StatusAdapter
import com.example.inovadigitalapp.resources.ValorStatusAdapter
import com.google.android.material.navigation.NavigationView
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
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val menuButton = findViewById<ImageButton>(R.id.button_menu)
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)

        menuButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                R.id.nav_cadastro -> {
                    startActivity(Intent(this, CadastroPedidoActivity::class.java))
                }
                R.id.nav_relatorio -> {
                    startActivity(Intent(this, DashboardPedidos::class.java))
                }
                R.id.nav_relatorio_financeiro -> {
                    startActivity(Intent(this, DashboardValoresActivity::class.java))
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Configurar o listener de clique no card
        adapter.onCardClickListener = { status ->
            // Criar a intent para ir para a tela de lista de pedidos com o status como parâmetro
            val intent = Intent(this, ListaPedidosActivity::class.java)
            intent.putExtra("status", status)  // Passa o status clicado
            startActivity(intent)
        }
    }
}