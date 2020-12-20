package com.example.tienda

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tienda.base.BaseViewHolder
import com.example.tienda.modelo.Producto
import kotlinx.android.synthetic.main.productos_row.view.*
import java.lang.IllegalArgumentException

class RecyclerAdapter (
    private val context: Context,
    private val listaProductos:List<Producto>,
    private val itemClickListener:OnProductoClickListener
):RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnProductoClickListener{
        fun onImageClick()
        fun onItemClick(nombre: String, imagen: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return ProductoViewHolder(LayoutInflater.from(context).inflate(R.layout.productos_row,parent,false))
    }

    override fun getItemCount(): Int {
        return listaProductos.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is ProductoViewHolder -> holder.bind(listaProductos[position],position)
            else -> throw IllegalArgumentException("El path es equivocado")
        }
    }

    inner class ProductoViewHolder(itemView: View):BaseViewHolder<Producto>(itemView){
        override fun bind(item: Producto, position: Int) {

            itemView.setOnClickListener{ itemClickListener.onItemClick(item.nombre, item.imagen)}
            Glide.with(context).load(item.imagen).into(itemView.imagen_producto)
            itemView.tv_nombreProducto.text = item.nombre
        }
    }

}