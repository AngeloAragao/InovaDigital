package com.example.inovadigitalapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.inovadigitalapp.http.HttpHelper
import com.example.inovadigitalapp.model.Pedido
import com.example.inovadigitalapp.resources.PedidoService
import com.example.inovadigitalapp.resources.atualizarBadge
import com.google.gson.Gson
import java.time.LocalDate


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)





        // Certifique-se de que a view com ID "main" existe no seu layout

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonTelaCadastro = findViewById<Button>(R.id.btn_cadastrar)
        val buttonAbrirLogin = findViewById<Button>(R.id.button_login)

        buttonTelaCadastro.setOnClickListener {
            // Apenas um log para depuração
            println("Botão Cadastro clicado")
            val intentCadastro = Intent(this, TelaCadastro::class.java)
            startActivity(intentCadastro)
        }

        buttonAbrirLogin.setOnClickListener {
            val intentLogin = Intent(this, Login::class.java)
            startActivity(intentLogin)
        }
        /*val buttonAbrirCadastroPedido = findViewById<Button>(R.id.button_abrir_cadastro_pedido)

        buttonAbrirCadastroPedido.setOnClickListener {
            System.out.println("Pedido.toString")
            val abrirCadastroPedido = Intent(this, CadastroPedidoActivity::class.java)
            startActivity(abrirCadastroPedido)
        }*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Pedidos"
            val descriptionText = "Notificações de pedidos próximos da entrega"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("canal_pedidos", name, importance).apply {
                description = descriptionText
                enableLights(true)
                lightColor = Color.YELLOW
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}




