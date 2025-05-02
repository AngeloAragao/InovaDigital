package com.example.inovadigitalapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.example.inovadigitalapp.http.HttpHelper
import com.example.inovadigitalapp.model.Pedido

/*class DetalhePedidoActivity : AppCompatActivity() {

    private lateinit var editTextIdPedido: EditText
    private lateinit var buttonBuscarPedido: Button
    private lateinit var textNomeCliente: TextView
    private lateinit var textTipoServico: TextView
    private lateinit var textDescricaoPedido: TextView
    private lateinit var textQuantidadePedido: TextView
    private lateinit var textEntregaPedido: TextView
    private lateinit var textValorPedido: TextView
    private lateinit var textStatus: TextView
    private lateinit var buttonEditarPedido: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_pedido)

        // Inicializando os componentes
        editTextIdPedido = findViewById(R.id.editIdPedido)
        buttonBuscarPedido = findViewById(R.id.btnBuscarPedido)
        textNomeCliente = findViewById(R.id.textNomeCliente)
        textTipoServico = findViewById(R.id.textTipoServico)
        textDescricaoPedido = findViewById(R.id.textDescricaoPedido)
        textQuantidadePedido = findViewById(R.id.textQuantidadePedido)
        textEntregaPedido = findViewById(R.id.textEntregaPedido)
        textValorPedido = findViewById(R.id.textValorPedido)
        textStatus = findViewById(R.id.textStatus)
        buttonEditarPedido = findViewById(R.id.btnEditarPedido)

        // Ação ao clicar no botão de buscar
        buttonBuscarPedido.setOnClickListener {
            val idPedido = editTextIdPedido.text.toString().toLongOrNull()

            if (idPedido != null) {
                buscarPedido(idPedido)
            } else {
                Toast.makeText(this, "ID do pedido inválido", Toast.LENGTH_SHORT).show()
            }
        }

        // Ação ao clicar no botão de editar
        /*
        buttonEditarPedido.setOnClickListener {
            val idPedido = editTextIdPedido.text.toString().toLongOrNull()
            if (idPedido != null) {
                val intent = Intent(this, EditarPedidoActivity::class.java)
                intent.putExtra("idPedido", idPedido)
                startActivity(intent)
            } else {
                Toast.makeText(this, "ID do pedido inválido", Toast.LENGTH_SHORT).show()
            }
        }
    }*/

    // Função para buscar os detalhes do pedido pelo ID
    private fun buscarPedido(idPedido: Long) {
        Thread {
            try {
                // URL da API
                val url = "http://192.168.15.17:8080/api/pedido/$idPedido"  // Certifique-se que o IP está correto
                val response = HttpHelper().get(url)

                runOnUiThread {
                    if (!response.isNullOrEmpty()) {
                        val pedido = Gson().fromJson(response, Pedido::class.java)
                        // Atualiza a interface com os dados do pedido
                        atualizarTela(pedido)
                    } else {
                        Toast.makeText(this, "Pedido não encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Erro ao buscar pedido: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    // Função para atualizar a tela com os dados do pedido
    private fun atualizarTela(pedido: Pedido) {
        textNomeCliente.text = "Cliente: ${pedido.nomeCliente}"
        textTipoServico.text = "Tipo de Serviço: ${pedido.tipoServico}"
        textDescricaoPedido.text = "Descrição: ${pedido.descricaoPedido}"
        textQuantidadePedido.text = "Quantidade: ${pedido.quantidadePedido}"
        textEntregaPedido.text = "Entrega: ${pedido.entregaPedido}"
        textValorPedido.text = "Valor: ${pedido.valorPedido}"
        textStatus.text = "Status: ${pedido.statusPedido}"
    }
}
*/