package com.example.inovadigitalapp

import android.content.Intent
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

class ListaPedidosActivity : AppCompatActivity() {

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
    }
}
