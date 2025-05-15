package com.example.inovadigitalapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.widget.*
import com.example.inovadigitalapp.http.HttpHelper
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
class TelaCadastro : AppCompatActivity() {

    private lateinit var editNome: EditText
    private lateinit var editEmail: EditText
    private lateinit var editSenha: EditText
    private lateinit var confSenha: EditText
    private lateinit var btnCadastrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_cadastro)

        // Referencia aos campos
        editNome = findViewById(R.id.edit_nome)
        editEmail = findViewById(R.id.edit_email)
        editSenha = findViewById(R.id.edit_senha)
        confSenha = findViewById(R.id.conf_senha)
        btnCadastrar = findViewById(R.id.btn_cadastrar)

        btnCadastrar.setOnClickListener {
            val nome = editNome.text.toString().trim()
            val email = editEmail.text.toString().trim()
            val senha = editSenha.text.toString().trim()
            val confirmar = confSenha.text.toString().trim()

            if (email.isEmpty() || senha.isEmpty() || confirmar.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else if (senha != confirmar) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
            } else {
                // Chama a função para cadastrar
                cadastrarUsuario(nome, email, senha)
            }
        }
    }

    private fun cadastrarUsuario(nome: String, email: String, senha: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val json = JSONObject().apply {
                put("nome", nome)
                put("email", email)
                put("senha", senha)
            }.toString()

            val response = HttpHelper().cadastrarUsuario(json)

            withContext(Dispatchers.Main) {
                if (response != null && response.contains("sucesso", ignoreCase = true)) {
                    Toast.makeText(this@TelaCadastro, "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this@TelaCadastro, "Erro ao cadastrar: $response", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

