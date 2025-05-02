package com.example.inovadigitalapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.inovadigitalapp.http.HttpHelper

class DetalhesPedidoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_pedido)

        // Recebe o ID do pedido e os detalhes via Intent
        val pedidoId = intent.getStringExtra("codigo")?.toLongOrNull() ?: -1L
        val nomePedido = intent.getStringExtra("nome_cliente")
        val statusPedido = intent.getStringExtra("status_pedido")

        // Exibe as informações no layout
        val textNomePedido: TextView = findViewById(R.id.textNomeCliente)
        val spinnerStatusPedido: Spinner = findViewById(R.id.spinnerStatusPedido)

        textNomePedido.text = nomePedido

        // Lista de status possíveis
        val statusList = listOf("Recebido", "Em andamento", "Finalizado", "Atrasado")
        val statusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statusList)
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStatusPedido.adapter = statusAdapter

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
    }
}
