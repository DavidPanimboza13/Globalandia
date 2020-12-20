package com.example.tienda

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tienda.base.BaseViewHolder
import com.example.tienda.modelo.Compras
import com.example.tienda.modelo.Datos
import kotlinx.android.synthetic.main.carrito_row.view.*

class RecyclerAdapterData (

    private val context: Context,
    private val listaDatos:List<Compras>,
    private val itemClickListener: OnComprasClickListener


) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnComprasClickListener{
        fun onItemClick(id: String, nombre: String, cantidad: Int, direccion: String, total: Double, nombreProd: String, estado: String)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        //return ProductoViewHolder(LayoutInflater.from(context).inflate(R.layout.productos_row,parent,false))
        return DatosViewHolder(LayoutInflater.from(context).inflate(R.layout.carrito_row,parent,false))
    }

    override fun getItemCount(): Int {
        return listaDatos.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is DatosViewHolder -> holder.bind(listaDatos[position],position)
            else -> throw IllegalArgumentException("El path es equivocado")
        }

    }

    inner class DatosViewHolder(itemView: View): BaseViewHolder<Compras>(itemView){
        override fun bind(item: Compras, position: Int) {
            itemView.setOnClickListener{itemClickListener.onItemClick(item.id , item.nombre, item.cantidad, item.direccion, item.total, item.nombreProducto, item.estado)}
            Glide.with(context).load(item.url).into(itemView.imagen_producto)
            itemView.tv_nombreCarrito.text = "Cliente: ${item.nombre}"
            itemView.tv_cantidadCarrito.text = "Cantidad Productos: ${item.cantidad.toString()}"
            itemView.tv_precioCarrito.text = "Precio total: ${item.total.toString()}"
            if(item.estado.equals("Activo")){
                itemView.tv_estado.text = "Articulo No Comprado"
                itemView.tv_estado.setTextColor(Color.parseColor("#55cd10"))
            } else{
                itemView.tv_estado.text = "Articulo Comprado"
                itemView.tv_estado.setTextColor(Color.parseColor("#FF0000"))
            }
        }

    }


}


/*
inner class ProductoViewHolder(itemView: View):BaseViewHolder<Producto>(itemView){
        override fun bind(item: Producto, position: Int) {

            itemView.setOnClickListener{ itemClickListener.onItemClick(item.nombre)}
            Glide.with(context).load(item.imagen).into(itemView.imagen_producto)
            itemView.tv_nombreProducto.text = item.nombre
        }
    }
 */