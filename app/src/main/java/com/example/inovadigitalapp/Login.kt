package com.example.inovadigitalapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.inovadigitalapp.http.HttpHelperLogin

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 👇 Aqui está o local certo
        val usuarioEditText = findViewById<EditText>(R.id.edit_email)
        val senhaEditText = findViewById<EditText>(R.id.edit_senha)
        val botaoEntrar = findViewById<Button>(R.id.entrar)

        botaoEntrar.setOnClickListener {
            val email = usuarioEditText.text.toString()
            val senha = senhaEditText.text.toString()

            val json = """
                {
                    "email": "$email",
                    "senha": "$senha"
                }
            """.trimIndent()

            Thread {
                val response = HttpHelperLogin().postLogin(json)
                runOnUiThread {
                    if (response != null && response.contains("email")) {
                        Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()
                        val abrirCadastroPedido = Intent(this, CadastroPedidoActivity::class.java)

                        startActivity(abrirCadastroPedido)
                    } else {
                        Toast.makeText(this, "Falha no login!", Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
        }
    }
}
