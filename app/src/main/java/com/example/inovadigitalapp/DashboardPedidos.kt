package com.example.inovadigitalapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
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
import com.google.android.material.navigation.NavigationView
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

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val menuButton = findViewById<ImageButton>(R.id.button_menu)
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)

        menuButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // ação
                }
                R.id.nav_cadastro -> {
                    // ação
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

