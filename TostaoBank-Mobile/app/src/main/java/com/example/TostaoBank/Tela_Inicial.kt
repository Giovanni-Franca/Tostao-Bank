package com.example.TostaoBank

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class Tela_Inicial : AppCompatActivity() {

    private var saldoVisivel = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_inicial)

        // TextViews e botões
        val txtSaldo = findViewById<TextView>(R.id.txtSaldo)
        val btnOcultar = findViewById<ImageView>(R.id.btnSMostrarSaldo)
        val txtNomeUsuario = findViewById<TextView>(R.id.txtOla)

        // Nome do usuário
        txtNomeUsuario.text = Sessao.nomeUsuario

        // Atualiza saldo inicial
        atualizarSaldo(txtSaldo)

        // Botão de mostrar/ocultar saldo
        btnOcultar.setOnClickListener {
            saldoVisivel = !saldoVisivel
            atualizarSaldo(txtSaldo)
            // Se tiver ícones diferentes para mostrar/ocultar, coloque aqui
            btnOcultar.setImageResource(if (saldoVisivel) R.drawable.olho else R.drawable.olho)
        }

        // Bottom navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.home
        bottomNav.setOnItemSelectedListener { item ->
            if (item.itemId == bottomNav.selectedItemId) return@setOnItemSelectedListener false
            when (item.itemId) {
                R.id.pix -> {
                    startActivity(Intent(this, Pix::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun atualizarSaldo(txt: TextView) {
        txt.text = if (saldoVisivel) {
            "R$ %.2f".format(Sessao.saldoUsuario)
        } else {
            "••••••"
        }
    }
}