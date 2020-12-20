package com.example.tienda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_comprar.*

class Comprar : AppCompatActivity() {

    var correo = "primero"
    var nombre = "Prueba"
    val db = FirebaseFirestore.getInstance()
    val estado = "Activo"
    var url = "url"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comprar)

        nombre = intent.getStringExtra("NombrePer")
        url = intent.getStringExtra("imagen")
        correo = intent.getStringExtra("Correo")
        val nombreProducto = intent.getStringExtra("NombreProducto")
        tv_prodCompras.setText(nombreProducto)

        if(nombreProducto.equals("Globos")){
            tv_precio.setText("El precio es: $0.75")
        } else if(nombreProducto.equals("Piñatas")){
            tv_precio.setText("El precio es: $5.25")
        } else if(nombreProducto.equals("Globos grandes")){
            tv_precio.setText("El precio es: $1.80")
        } else if(nombreProducto.equals("Globos pequeños")){
            tv_precio.setText("El precio es: $0.35")
        } else if(nombreProducto.equals("Globos medianos")){
            tv_precio.setText("El precio es: $0.85")
        } else if(nombreProducto.equals("Globos personalizados")){
            tv_precio.setText("El precio es: $1.25")
        }

        btn_registro.setOnClickListener { agregarCarrito( nombreProducto) }
        btn_regresar.setOnClickListener { regresar() }

    }

    fun agregarCarrito(nombreProducto:String){

        var total = 0.0
        val cantidad: Int = tv_cantidad1.text.toString().toInt()
        val direccion = tv_direccion.text.toString()

        if(nombreProducto.equals("Globos")){
            total = (cantidad * 0.75).toDouble()
        } else if(nombreProducto.equals("Piñatas")){
            total = (cantidad * 5.25)
        } else if(nombreProducto.equals("Globos grandes")){
            total = (cantidad * 1.80)
        } else if(nombreProducto.equals("Globos pequeños")){
            total = (cantidad * 0.35)
        } else if(nombreProducto.equals("Globos medianos")){
            total = (cantidad * 0.85)
        } else if(nombreProducto.equals("Globos personalizados")){
            total = (cantidad * 1.25)
        }

        guardarDB(nombreProducto, direccion, cantidad, total)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Costo total")
        builder.setMessage("El costo de la compra es: $ ${total}" )
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
        limpiar()
    }

    fun guardarDB(nombreProducto: String, direccion:String, cantidad:Int, total:Double){
        /*db.collection("Usuarios").document(correo).set(
            hashMapOf(
                "nombreProducto" to nombreProducto,
                "direccion" to direccion,
                "cantidad" to cantidad,
                "total" to total
            )
        )*/

        db.collection("Usuarios").document(correo).update(
            hashMapOf(
                "nombreProducto" to nombreProducto,
                "direccion" to direccion,
                "cantidad" to cantidad,
                "total" to total
            )
        )

        db.collection(correo).add(
            hashMapOf(
                "nombre" to nombre,
                "nombreProducto" to nombreProducto,
                "direccion" to direccion,
                "cantidad" to cantidad,
                "total" to total,
                "estado" to estado,
                "url" to url)
        )

    }

    fun limpiar(){
        tv_direccion.setText("")
        tv_cantidad1.setText("")
    }

    fun regresar() {
        val intent = Intent(
            this,
            EleccionProductos::class.java
        )
        intent.putExtra("Correo", correo)
        startActivity(intent)
    }
}
