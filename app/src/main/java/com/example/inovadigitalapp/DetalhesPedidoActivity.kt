package com.example.inovadigitalapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.inovadigitalapp.http.HttpHelper
import com.google.android.material.navigation.NavigationView

class DetalhesPedidoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_pedido)

        // Recebe o ID do pedido e os detalhes via Intent
        val pedidoId = intent.getStringExtra("codigo")?.toLongOrNull() ?: -1L
        val codigo = intent.getStringExtra("codigo")
        val nomePedido = intent.getStringExtra("nome_cliente")
        val entregaPedido = intent.getStringExtra("entrega_pedido")
        val statusPedido = intent.getStringExtra("status_pedido")

        // Exibe as informações no layout
        val textCodigoPedido: TextView = findViewById(R.id.textCodigoPedido)
        val textNomePedido: TextView = findViewById(R.id.textNomeCliente)
        val textDataEntrega: TextView = findViewById(R.id.textDataEntrega)
        val spinnerStatusPedido: Spinner = findViewById(R.id.spinnerStatusPedido)

        textNomePedido.text = nomePedido
        textCodigoPedido.text = codigo
        textDataEntrega.text = entregaPedido

        // Lista de status possíveis
        val statusList = listOf("Recebido", "Em andamento", "Finalizado", "Atrasado")
        val statusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statusList)
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStatusPedido.adapter = statusAdapter

        Log.d("DetalhesPedidoActivity", "Entrega Pedido: $entregaPedido")

        // Configura o Spinner para mostrar o status atual
        val currentStatusIndex = statusList.indexOf(statusPedido)
        if (currentStatusIndex >= 0) {
            spinnerStatusPedido.setSelection(currentStatusIndex)
        }

        // Configura o botão para atualizar o status
        val buttonAtualizarStatus: Button = findViewById(R.id.buttonAtualizarStatus)
        buttonAtualizarStatus.setOnClickListener {
            // Pega o novo status selecionado
            val novoStatus = spinnerStatusPedido.selectedItem.toString()

            // Atualizar o status no backend
            atualizarStatusPedido(pedidoId, novoStatus)

            val intent = Intent(this, DashboardPedidos::class.java)
            startActivity(intent)
        }
    }

    // Método para atualizar o status do pedido no backend
    private fun atualizarStatusPedido(pedidoId: Long, novoStatus: String) {
        // Aqui você faria uma requisição para atualizar o status no backend (Spring Boot)
        val httpHelper = HttpHelper()

        // Requisição na thread para atualizar o status
        Thread {
            val sucesso = httpHelper.atualizarStatusPedido(pedidoId, novoStatus)
            runOnUiThread {
                if (sucesso) {
                    Toast.makeText(this, "Status atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish() // Volta para a tela anterior
                } else {
                    Toast.makeText(this, "Erro ao atualizar status!", Toast.LENGTH_SHORT).show()
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
    }
}
