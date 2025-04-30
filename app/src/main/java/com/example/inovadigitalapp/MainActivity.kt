package com.example.inovadigitalapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
    }
}
