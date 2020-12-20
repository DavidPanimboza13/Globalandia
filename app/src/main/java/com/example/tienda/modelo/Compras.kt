package com.example.tienda.modelo

class Compras (
    var id: String,
    var nombre: String,
    var direccion: String,
    var cantidad: Int,
    var total: Double,
    var url: String,
    var estado: String,
    var nombreProducto: String
) {

    override fun toString ():String{
        return "${nombre} ${direccion} ${cantidad} ${total} ${url} ${estado} ${nombreProducto}"
    }
}