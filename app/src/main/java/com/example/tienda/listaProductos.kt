package com.example.tienda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tienda.modelo.Producto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_lista_productos.*
import kotlinx.android.synthetic.main.productos_row.*

class ListaProductos : AppCompatActivity(), RecyclerAdapter.OnProductoClickListener {

    var correo = "primero"
    val db = FirebaseFirestore.getInstance()
    var listaProductos = arrayListOf<Producto>()
    var nombrePer = "quemado"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_productos)
        setupRecyclerView()

        correo = intent.getStringExtra("Correo")
        nombrePer = intent.getStringExtra("NombrePer")
    }

    fun setupRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        /*val listaProductos = listOf(
            Producto("Globos","https://www.lilarie.com/web/image/product.product/82341/image"),
            Producto("Piñatas","https://ideasparalasfiestas.com/wp-content/uploads/2019/09/imagenes-de-pinatas-para-ninas-6.jpg"),
            Producto("Globos grandes","https://aguirrefloreria.com/wp-content/uploads/2020/03/GLOBO-REVELACION-3-PIES.jpg"),
            Producto("Globos pequeños","https://www.bakingideas.ec/wp-content/uploads/2016/12/globos-me.png"),
            Producto("Globos medianos","https://cdn.coordiutil.com/imagen-feliz_cumpleanos_azul_globos_18-1763650-800-600-1-75.jpg"),
            Producto("Globos personalizados","https://ae01.alicdn.com/kf/Hc0fba24e3378427d96a4ac8dd79ede23Y/Soporte-para-globos-de-cumplea-os-soporte-para-arco-de-globos-de-cumplea-os-soporte-para.jpg_q50.jpg"))

        recyclerView.adapter = RecyclerAdapter(this, listaProductos,this)*/


           db.collection("Productos").get().addOnSuccessListener { resultado ->
                for (document in resultado){
                    var nombre = document.getString("nombre").toString()
                    var imagen = document.getString("imagen").toString()
                    listaProductos.add(Producto(nombre,imagen))

                    Log.i("Lista", "La lista es: ${listaProductos}")
                }
               recyclerView.adapter = RecyclerAdapter(this, listaProductos,this)
            }


    }

    override fun onImageClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemClick(nombre: String, imagen: String) {

        val intent = Intent(
            this,
            Comprar::class.java
        )

        intent.putExtra("NombreProducto", nombre )
        intent.putExtra("Correo", correo)
        intent.putExtra("NombrePer", nombrePer)
        intent.putExtra("imagen", imagen)
        startActivity(intent)
    }
}
