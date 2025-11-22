package com.example.TostaoBank

import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
                // Mostrar senha
                senhaEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                btnShowHide.setImageResource(R.drawable.baseline_remove_red_eye_24)
            } else {
                // Esconder senha
                senhaEditText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                btnShowHide.setImageResource(R.drawable.baseline_remove_red_eye_24)
            }
            // Mantém o cursor no final
            senhaEditText.setSelection(senhaEditText.text.length)
        }


    }
}