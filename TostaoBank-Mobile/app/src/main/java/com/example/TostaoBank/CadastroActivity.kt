package com.example.TostaoBank

import RetrofitClient
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
import model.CadastroRequest
import model.Usuario

class CadastroActivity : AppCompatActivity() {
    private lateinit var txtNome: EditText
    private lateinit var txtRenda: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtSenha: EditText
    private lateinit var txtCPF: EditText
    private lateinit var btnShowHide: ImageButton
    private lateinit var btnCadastrar: MaterialButton
    private lateinit var txtMensagem: TextView
    private var senhaVisivel = false

    private lateinit var txtTelefone: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.cadastro)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Campos e botões
        txtNome = findViewById(R.id.txtNomeCadastro)
        txtRenda = findViewById(R.id.txtRendaCadastro)
        txtEmail = findViewById(R.id.txtLoginCadastro)
        txtSenha = findViewById(R.id.txtSenhaCadastro)
        txtCPF = findViewById(R.id.txtCPFCadastro)
        btnShowHide = findViewById(R.id.btnShowHide)
        btnCadastrar = findViewById(R.id.btnCadastrar)
        txtMensagem = findViewById(R.id.txtMensagem)

        // Mostrar/esconder senha
        btnShowHide.setOnClickListener {
            senhaVisivel = !senhaVisivel
            if (senhaVisivel) {
                txtSenha.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                txtSenha.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            txtSenha.setSelection(txtSenha.text.length)
        }

        // Ação do botão Cadastrar
        btnCadastrar.setOnClickListener {
            val nome = txtNome.text.toString().trim()
            val email = txtEmail.text.toString().trim()
            val senha = txtSenha.text.toString().trim()
            val cpf = txtCPF.text.toString().trim()
            val renda = txtRenda.text.toString().trim()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cpf.isEmpty() || renda.isEmpty()) {
                txtMensagem.text = "Preencha todos os campos!"
                return@setOnClickListener
            }

            val usuario = Usuario(
                idUsuario = 0L,          // backend gera o ID
                nomeUsuario = nome,
                emailUsuario = email,
                senhaUsuario = senha,
                saldoUsuario = 0.0,       // valor inicial
                cartaoUsuario = "",       // backend gera o cartão
                telefoneUsuario = ""      // opcional
            )

            lifecycleScope.launch {
                try {
                    val resp = RetrofitClient.api.cadastrar(usuario)

                    if (resp.isSuccessful) {
                        val novoUsuario: Usuario? = resp.body()
                        if (novoUsuario != null) {
                            txtMensagem.text = "Cadastro realizado com sucesso!"
                            limparCampos()
                            // redireciona para login
                            startActivity(Intent(this@CadastroActivity, MainActivity::class.java))
                            finish()
                        }
                    } else {
                        txtMensagem.text = "Erro: ${resp.code()} - ${resp.errorBody()?.string()}"
                    }

                } catch (e: Exception) {
                    txtMensagem.text = "Erro ao comunicar com a API"
                }
            }
        }
    }

    private fun limparCampos() {
        txtEmail.text.clear()
        txtSenha.text.clear()
        txtCPF.text.clear()
        txtNome.text.clear()
        txtRenda.text.clear()
    }
}