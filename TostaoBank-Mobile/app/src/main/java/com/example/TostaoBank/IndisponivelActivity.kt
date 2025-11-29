package com.example.TostaoBank

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class IndisponivelActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.indisponivel)

        val btnVoltar = findViewById<MaterialButton>(R.id.btnVoltar)

        btnVoltar.setOnClickListener {
            val intent = Intent(this, Tela_Inicial::class.java)
            startActivity(intent)
        }
    }
}