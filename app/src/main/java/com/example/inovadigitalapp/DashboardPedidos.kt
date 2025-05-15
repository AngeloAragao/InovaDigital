package com.example.inovadigitalapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inovadigitalapp.http.HttpHelper
import com.example.inovadigitalapp.model.Pedido
import com.example.inovadigitalapp.resources.PedidoService
import com.example.inovadigitalapp.resources.StatusAdapter
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.jakewharton.threetenabp.AndroidThreeTen
import java.time.LocalDate

class DashboardPedidos : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
         // ou outra função que atualize os dados da tela
    }

    private lateinit var adapter: StatusAdapter // Tornamos o adapter acessível na classe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_pedidos)
        AndroidThreeTen.init(this)

        // Supondo que você já tenha os pedidos filtrados no seu callback
        PedidoService.buscarPedidosNotificacao { pedidos ->
            // Aqui você tem todos os pedidos, agora pode exibi-los
            pedidos.forEach { pedido ->
                println("Pedido ${pedido.codigo} - Nome Cliente: ${pedido.nomeCliente} - Data: ${pedido.entregaPedido} - Valor: ${pedido.valorPedido} - Status: ${pedido.statusPedido}")
            }
        }


        val contadorNotificacoes = findViewById<TextView>(R.id.contadorNotificacoes)
        val iconeNotificacao = findViewById<ImageView>(R.id.iconeNotificacao)

// Chama o serviço para buscar os pedidos da API
        PedidoService.buscarPedidosNotificacao { pedidos ->
            runOnUiThread {
                val quantidade = pedidos.size // Aqui pega a quantidade de notificações reais da API

                if (quantidade > 0) {
                    contadorNotificacoes.text = quantidade.toString()
                    contadorNotificacoes.visibility = View.VISIBLE
                } else {
                    contadorNotificacoes.visibility = View.GONE
                }


            }

            iconeNotificacao.setOnClickListener {
                PedidoService.buscarPedidosNotificacao { pedidos ->

                    if (pedidos.isEmpty()) {
                        Toast.makeText(this, "Sem notificações", Toast.LENGTH_SHORT).show()
                        return@buscarPedidosNotificacao
                    }

                    // Monta a lista de notificações
                    val mensagens = pedidos.map { pedido ->
                        "Pedido ${pedido.codigo} - ${pedido.nomeCliente}\n" +
                                "Data: ${pedido.entregaPedido} - Valor: ${pedido.valorPedido} - Status: ${pedido.statusPedido}"
                    }.toTypedArray()

                    // Mostra no AlertDialog
                    AlertDialog.Builder(this)
                        .setTitle("Notificações")
                        .setItems(mensagens, null) // lista simples
                        .setPositiveButton("Fechar", null)
                        .show()
                }
            }
        }




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

