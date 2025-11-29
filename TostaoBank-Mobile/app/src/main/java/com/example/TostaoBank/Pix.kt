package com.example.TostaoBank

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import model.PixDTO
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class Pix : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pix)

        val txtChavePix = findViewById<EditText>(R.id.txtChavePix)
        val txtValorPix = findViewById<EditText>(R.id.txtValorPix)
        val btnPagar = findViewById<Button>(R.id.btnPagar)

        btnPagar.setOnClickListener {
            val valor = txtValorPix.text.toString().toDoubleOrNull()
            val emailDestino = txtChavePix.text.toString().trim()

            if (valor == null || valor <= 0) {
                Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (emailDestino.isEmpty()) {
                Toast.makeText(this, "Digite a chave PIX", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verifica se o saldo é suficiente antes de enviar
            if (Sessao.saldoUsuario < valor) {
                Toast.makeText(this, "Saldo insuficiente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val dto = PixDTO(
                        email = Sessao.emailUsuario,
                        valor = valor,
                        emailRecebe = emailDestino
                    )

                    val resp = RetrofitClient.api.enviarPix(dto)

                    if (resp.isSuccessful) {
                        val usuarioAtualizado = resp.body()
                        Log.d("PIX", "Saldo retornado pelo backend: ${usuarioAtualizado?.saldoUsuario}")


                        if (usuarioAtualizado != null) {
                            // Atualiza a Sessão com o saldo retornado pelo backend
                            Sessao.saldoUsuario = usuarioAtualizado.saldoUsuario
                            Toast.makeText(this@Pix, "PIX enviado!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@Pix, "Erro: resposta vazia", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.d("PIX", "Saldo atual: ${Sessao.saldoUsuario}, Valor PIX: $valor")
                        when (resp.code()) {
                            400 -> Toast.makeText(this@Pix, "Saldo insuficiente", Toast.LENGTH_SHORT).show()
                            404 -> Toast.makeText(this@Pix, "Usuário não encontrado", Toast.LENGTH_SHORT).show()
                            else -> Toast.makeText(this@Pix, "Erro: ${resp.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this@Pix, "Erro ao comunicar com a API", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.pix

        bottomNav.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.pix) return@setOnItemSelectedListener true
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, Tela_Inicial::class.java))
                    true
                }
                R.id.extrato ->{
                    startActivity(Intent( this, HistoricoActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}