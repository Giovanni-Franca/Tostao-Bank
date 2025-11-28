package com.example.TostaoBank

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Tela_Inicial : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_inicial)

        val nome = intent.getStringExtra("usuarioNome")
        val id = intent.getLongExtra("usuarioId", -1)

        // opcional: mostrar nome na tela
    }
}