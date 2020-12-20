package com.example.tienda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.tienda.modelo.Datos
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_eleccion_productos.*

class EleccionProductos : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    var correo = "primero"
    var nombre = "quemado"
    //var listaDatos = arrayListOf<Datos>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eleccion_productos)

        correo = intent.getStringExtra("Correo")


        db.collection("Usuarios").document(correo).get().addOnSuccessListener { datos ->
            nombre = datos.getString("nombre").toString()
            Log.i("Nombrecorreo", nombre)
        }

        btn_elegir.setOnClickListener { abrir() }
        btn_cerarSesion.setOnClickListener { salir() }
        btn_compras.setOnClickListener { verCompras() }
    }

    fun verCompras(){
        val intent = Intent(
            this,
            Carrito::class.java
        )
        intent.putExtra("Correo", correo)
        intent.putExtra("NombrePer", nombre)
        startActivity(intent)
    }

    fun abrir(){
        val intent = Intent(
            this,
            ListaProductos::class.java
        )
        intent.putExtra("Correo", correo)
        intent.putExtra("NombrePer", nombre)
        startActivity(intent)
    }

    fun salir(){
        val intent = Intent(
            this,
            MainActivity::class.java
        )
        startActivity(intent)
    }
}
