package com.example.practica_sensores

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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

        navegar()
        navegar2()
        navegar3()
        navegar4()
        navegar5()
        navegar6()
        navegar7()
    }

    fun navegar() {
        val btn = findViewById<Button>(R.id.btn_gps)
        btn.setOnClickListener {
            val saltar = Intent(this, Localizacion::class.java)

            startActivity(saltar)
        }
    }
    fun navegar2() {
        val btn = findViewById<Button>(R.id.btn_rowColumn)
        btn.setOnClickListener {
            val saltar = Intent(this, ContadorPasosActivity::class.java)

            startActivity(saltar)
        }
    }
    fun navegar3() {
        val btn = findViewById<Button>(R.id.btn_cmra)
        btn.setOnClickListener {
            val saltar = Intent(this, CameraActivity::class.java)

            startActivity(saltar)
        }
    }
    fun navegar4() {
        val btn = findViewById<Button>(R.id.btn_register)
        btn.setOnClickListener {
            val saltar = Intent(this, Register::class.java)

            startActivity(saltar)
        }
    }
    fun navegar5() {
        val btn = findViewById<Button>(R.id.btn_login)
        btn.setOnClickListener {
            val saltar = Intent(this, LoginActivity::class.java)

            startActivity(saltar)
        }
    }

    fun navegar6() {
        val btn = findViewById<Button>(R.id.btn_form)
        btn.setOnClickListener {
            val saltar = Intent(this, Formulario::class.java)

            startActivity(saltar)
        }
    }

    fun navegar7() {
        val btn = findViewById<Button>(R.id.btn_img)
        btn.setOnClickListener {
            val saltar = Intent(this, ImagenActivity::class.java)

            startActivity(saltar)
        }
    }

}