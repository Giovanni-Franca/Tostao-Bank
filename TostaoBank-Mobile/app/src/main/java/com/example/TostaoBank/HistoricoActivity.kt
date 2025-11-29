package com.example.TostaoBank

import androidx.recyclerview.widget.RecyclerView;

import Adapter.HistoricoAdapter;
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import model.Historico



class HistoricoActivity : AppCompatActivity() {
    private val listaHistorico = mutableListOf<Historico>()
    private lateinit var adapter: HistoricoAdapter
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historico)

        recycler = findViewById(R.id.recyclerHistorico)
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = HistoricoAdapter(listaHistorico)
        recycler.adapter = adapter

        carregarHistorico()
        carregarHistorico()

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.extrato

        bottomNav.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.extrato) return@setOnItemSelectedListener true
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, Tela_Inicial::class.java))
                    true
                }
                R.id.pix -> {
                    startActivity(Intent(this, Pix::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun carregarHistorico() {
        val email = Sessao.emailUsuario

        lifecycleScope.launch {
            try {
                val resp = RetrofitClient.api.historico(email)
                if (resp.isSuccessful) {
                    val lista = resp.body() ?: emptyList()
                    adapter.atualizarLista(lista)
                } else {
                    Toast.makeText(this@HistoricoActivity, "Erro ao buscar histórico", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@HistoricoActivity, "Falha: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
