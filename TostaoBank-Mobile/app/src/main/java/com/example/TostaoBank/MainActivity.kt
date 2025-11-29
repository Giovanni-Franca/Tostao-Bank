package com.example.TostaoBank

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import model.LoginRequest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val senhaEditText = findViewById<EditText>(R.id.txtSenha)
        val btnShowHide = findViewById<ImageButton>(R.id.btnShowHide)
        var senhaVisivel = false

        btnShowHide.setOnClickListener {
            senhaVisivel = !senhaVisivel
            if (senhaVisivel) {
                senhaEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                btnShowHide.setImageResource(R.drawable.baseline_remove_red_eye_24)
            } else {
                senhaEditText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                btnShowHide.setImageResource(R.drawable.baseline_remove_red_eye_24)
            }
            senhaEditText.setSelection(senhaEditText.text.length)
        }

        val txtEmail = findViewById<EditText>(R.id.txtLogin)
        val txtSenha2 = findViewById<EditText>(R.id.txtSenha)
        val btnLogar = findViewById<MaterialButton>(R.id.btnLogar)
        val btnIrCadastro = findViewById<MaterialButton>(R.id.btnIrCadastro)

        btnIrCadastro.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
        val txtTeste = findViewById<TextView>(R.id.txtTeste)

        btnLogar.setOnClickListener {
            val email = txtEmail.text.toString().trim()
            val senha = txtSenha2.text.toString().trim()

            if (email.isEmpty() || senha.isEmpty()) {
                txtTeste.text = "Preencha todos os campos!"
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val req = LoginRequest(emailUsuario = email, senhaUsuario = senha)
                    val resp = RetrofitClient.api.login(req)

                    if (resp.isSuccessful) {
                        val usuario = resp.body()

                        if (usuario != null) {

                            Sessao.usuarioId = usuario.idUsuario
                            Sessao.nomeUsuario = usuario.nomeUsuario
                            Sessao.saldoUsuario = usuario.saldoUsuario

                            txtTeste.text = "Bem vindo, ${usuario.nomeUsuario}!"

                            val intent = Intent(this@MainActivity, Tela_Inicial::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            txtTeste.text = "Erro inesperado: resposta vazia"
                        }

                    } else {
                        txtTeste.text = when (resp.code()) {
                            401 -> "Senha incorreta"
                            404 -> "Usuário não encontrado"
                            else -> "Erro: ${resp.code()}"
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    txtTeste.text = "Erro: ${e.localizedMessage}"
                }
            }
        }
    }
}