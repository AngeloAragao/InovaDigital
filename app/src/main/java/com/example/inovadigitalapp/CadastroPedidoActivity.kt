package com.example.inovadigitalapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.inovadigitalapp.http.HttpHelper
import com.example.inovadigitalapp.model.Pedido
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext





class CadastroPedidoActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro_pedido)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout)) { v, insets ->
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
                    Toast.makeText(this, "Você já está no Cadastro", Toast.LENGTH_SHORT).show()
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