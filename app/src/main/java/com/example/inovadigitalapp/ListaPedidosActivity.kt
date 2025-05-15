package com.example.inovadigitalapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inovadigitalapp.http.HttpHelper
import com.example.inovadigitalapp.model.Pedido
import com.example.inovadigitalapp.resources.PedidoAdapter
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson

class ListaPedidosActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        // ou outra função que atualize os dados da tela
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PedidoAdapter
    private val listaPedidos = mutableListOf<Pedido>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pedidos)

        recyclerView = findViewById(R.id.recyclerPedidos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Pegar o status passado da tela anterior
        val status = intent.getStringExtra("status") ?: ""

        // Fazer a requisição para obter os pedidos filtrados pelo status
        val httpHelper = HttpHelper()

        // Configurar o listener de clique
        // Isso é necessário para que o adapter tenha o listener configurado antes de ser usado
        val onPedidoClickListener: (Pedido) -> Unit = { pedido ->
            val intent = Intent(this, DetalhesPedidoActivity::class.java)
            intent.putExtra("codigo", pedido.codigo)
            intent.putExtra("nome_cliente", pedido.nomeCliente)
            intent.putExtra("status_pedido", pedido.statusPedido)
            intent.putExtra("entrega_pedido", pedido.entregaPedido)
            startActivity(intent)
        }

        // Requisição na thread adequada para buscar os pedidos
        Thread {
            val json = httpHelper.getPedidosPorStatus(status)
            json?.let {
                val gson = Gson()
                val lista = gson.fromJson(it, Array<Pedido>::class.java).toList()

                // Atualizar a UI na thread principal
                runOnUiThread {
                    // Limpa a lista de pedidos e adiciona os pedidos filtrados
                    listaPedidos.clear()
                    listaPedidos.addAll(lista)

                    // Se o adapter ainda não foi inicializado, inicializa ele
                    if (!::adapter.isInitialized) {
                        adapter = PedidoAdapter(listaPedidos)
                        recyclerView.adapter = adapter
                    } else {
                        adapter.updateData(listaPedidos) // Atualiza os dados no adapter
                    }

                    // Configura o listener de clique após inicializar o adapter
                    adapter.onPedidoClickListener = onPedidoClickListener
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
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
}
