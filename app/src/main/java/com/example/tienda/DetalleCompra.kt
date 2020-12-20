package com.example.tienda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detalle_compra.*

class DetalleCompra : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    var correo = "prueba"
    var id = "generico"
    val estado = "Desactivado"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_compra)

        val nombrePersona = intent.getStringExtra("NombrePer").toString()
        val cantidadProductos = intent.getIntExtra("Cantidad",0)
        val direccionEntrega = intent.getStringExtra("Direccion").toString()
        val total = intent.getDoubleExtra("Total",0.0)
        val nombreProd = intent.getStringExtra("nombreProducto").toString()
        correo = intent.getStringExtra("correo").toString()
        id = intent.getStringExtra("id")
        tv_nombreProducto.text = "Producto = ${nombreProd}"
        tv_nombreCompra.text = "Comprador = ${nombrePersona}"
        tv_cantidadProductos.text = "Productos = ${cantidadProductos}"
        tv_direccionEntrega.text = "La dirección de entrega es = ${direccionEntrega}"
        tv_totalPrecio.text = "El costo total es: ${total}"

        btn_realizarCompra.setOnClickListener { realizarCompra(id, nombrePersona) }
        btn_cancelar.setOnClickListener { regresar() }

    }

    fun realizarCompra(id: String, nombrePersona: String){

        db.collection(correo).document(id).update("estado",estado)

        val intent = Intent(
            this,
            Carrito::class.java
        )
        intent.putExtra("NombrePer", nombrePersona)
        intent.putExtra("Correo",correo)

        startActivity(intent)
    }

    fun regresar(){
        val intent = Intent(
            this,
            Carrito::class.java
        )
        intent.putExtra("Correo",correo)
        startActivity(intent)
    }
}
