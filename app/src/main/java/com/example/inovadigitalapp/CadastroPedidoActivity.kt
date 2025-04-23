package com.example.inovadigitalapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.inovadigitalapp.http.HttpHelper
import com.example.inovadigitalapp.model.Pedido
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext





class CadastroPedidoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro_pedido)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonGravar = findViewById<Button>(R.id.button_gravar)
        val editTextNomeCliente = findViewById<EditText>(R.id.edit_text_nome_cliente)
        val editTextTipoServico = findViewById<EditText>(R.id.edit_text_tipo_servico)
        val editTextDescricaoPedido = findViewById<EditText>(R.id.edit_text_descricao_pedido)
        val editTextQuantidadePedido = findViewById<EditText>(R.id.edit_text_quantidade_pedido)
        val editTextEntregaPedido = findViewById<EditText>(R.id.edit_text_entrega_pedido)
        val editTextValorPedido = findViewById<EditText>(R.id.edit_text_valor_pedido)

        val editTextStatusPedido: Spinner = findViewById(R.id.edit_spinner_status_pedido)

        // Lista de opções
        val opcoes = listOf("Recebido", "Produzindo", "Atrasado", "Finalizado")

        // Adapter para conectar os dados ao Spinner
        editTextStatusPedido.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opcoes)


        buttonGravar.setOnClickListener {
            // Criar um objeto Pedido
            val pedido = Pedido()
            pedido.nomeCliente = editTextNomeCliente.text.toString()
            pedido.tipoServico = editTextTipoServico.text.toString()
            pedido.descricaoPedido = editTextDescricaoPedido.text.toString()
            pedido.quantidadePedido = editTextQuantidadePedido.text.toString()
            pedido.entregaPedido = editTextEntregaPedido.text.toString()
            pedido.valorPedido = editTextValorPedido.text.toString()
            pedido.statusPedido = editTextStatusPedido.selectedItem.toString()



            // Converter o pedido em texto
            val gson = Gson()
            val pedidoJson = gson.toJson(pedido)

            System.out.println(pedidoJson)


            // Exemplo de como utilizar coroutines para chamada HTTP assíncrona
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    // Chama a função de POST em um thread de fundo
                    val result = withContext(Dispatchers.IO) {
                        val httpHelper = HttpHelper()
                        httpHelper.post(pedidoJson) // Substitua pelo seu JSON
                    }

                    // Exemplo: Mostrando o resultado em um Toast após a operação
                    Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()

                    val abrirDash = Intent(applicationContext, DashboardPedidos::class.java)
                    startActivity(abrirDash)

                } catch (e: Exception) {
                    // Se algo der errado, trate o erro
                    Toast.makeText(applicationContext, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
            //val voltarMain = Intent(this, MainActivity::class.java)
            //startActivity(voltarMain)

        }
    }
}