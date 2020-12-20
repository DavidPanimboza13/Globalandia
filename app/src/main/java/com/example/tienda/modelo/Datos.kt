package com.example.tienda.modelo

class Datos (
    var apellido: String,
    var celular: String,
    var nombre: String

) {

    override fun toString ():String{
        return "${apellido} ${celular} ${nombre}"
    }

}