package com.example.tienda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tienda.modelo.Compras
import com.example.tienda.modelo.Datos
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_carrito.*

class Carrito : AppCompatActivity(), RecyclerAdapterData.OnComprasClickListener {


    val db = FirebaseFirestore.getInstance()
    //var listaDatos = arrayListOf<Datos>()
    var listaCompras = arrayListOf<Compras>()
    var correo = "Correo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        //recuperar()
        correo = intent.getStringExtra("Correo")
        Log.i("Correo",correo)
        setupRecyclerView()
        btn_back.setOnClickListener { regreso() }

    }

    fun setupRecyclerView(){
        recyclerViewCarrito.layoutManager = LinearLayoutManager(this)
        recyclerViewCarrito.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        /*val listaDatos = listOf(
            Datos("Ramos",10,"123456","La Tola","Dayana","Pistolas",15.35,"https://www.lilarie.com/web/image/product.product/82341/image"),
            Datos("Ramos",10,"123456","La Tola","Daniela","Pistolas",15.35,"https://ideasparalasfiestas.com/wp-content/uploads/2019/09/imagenes-de-pinatas-para-ninas-6.jpg")
        )
        recyclerViewCarrito.adapter = RecyclerAdapterData(this, listaDatos)*/

        db.collection("${correo}").get().addOnSuccessListener { resultado ->
            for(document in resultado){
                var id = document.id
                var nombre = document.getString("nombre").toString()
                Log.i("Nombre", nombre)
                var cantidad = document.get("cantidad").toString().toInt()
                var total = document.get("total").toString().toDouble()
                var direccion = document.get("direccion").toString()
                var nombreProducto = document.get("nombreProducto").toString()
                var url = document.getString("url").toString()
                var estado = document.getString("estado").toString()
                listaCompras.add(Compras(id,nombre,direccion,cantidad,total,url,estado,nombreProducto))

                //Log.i("Lista", "La lista es: ${listaCompras}")
                //Log.i("ver", id)
            }
            recyclerViewCarrito.adapter = RecyclerAdapterData(this, listaCompras,this)

        }

        /*db.collection("Productos").get().addOnSuccessListener { resultado ->
            for (document in resultado){
                var nombre = document.getString("nombre").toString()
                var imagen = document.getString("imagen").toString()
                listaProductos.add(Producto(nombre,imagen))

                Log.i("Lista", "La lista es: ${listaProductos}")
            }
            recyclerView.adapter = RecyclerAdapter(this, listaProductos,this)
        }*/

    }

    override fun onItemClick(id: String, nombre: String, cantidad: Int, direccion: String, total: Double, nombreProducto: String, estado: String) {
        if(!estado.equals("Desactivado")){
            val intent = Intent(
                this,
                DetalleCompra::class.java
            )
            intent.putExtra("id", id)
            intent.putExtra("NombrePer", nombre)
            intent.putExtra("Cantidad", cantidad)
            intent.putExtra("Direccion", direccion)
            intent.putExtra("Total", total)
            intent.putExtra("nombreProducto", nombreProducto)
            intent.putExtra("correo", correo)
            startActivity(intent)
        }
    }

    fun regreso(){
        val intent = Intent(
            this,
            EleccionProductos::class.java
        )
        intent.putExtra("Correo",correo)
        startActivity(intent)
    }

}

