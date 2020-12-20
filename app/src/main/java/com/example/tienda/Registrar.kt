package com.example.tienda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_registrar.*

class Registrar : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)

        btn_registro.setOnClickListener { registrarseUsuario() }
    }

    fun registrarseUsuario(){
        title = "Registro de datos"

        val nombre = tv_nombre.text.toString()
        val apellido = tv_apellido.text.toString()
        val celular = tv_celular.text.toString()
        val correo = tv_correo.text.toString()

        if(tv_correo.text.isNotEmpty() && tv_contra.text.isNotEmpty()){

            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(tv_correo.text.toString(), tv_contra.text.toString())
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        mostrarMensajeRegistro()
                        guardarBD(correo,nombre,apellido,celular)
                        val intentInicio = Intent(
                            this,
                            MainActivity::class.java
                        )
                        startActivity(intentInicio)
                    } else {
                        mostrarMensaje()
                    }
                }
        }


    }

    fun guardarBD(correo:String, nombre:String, apellido:String, celular:String){
        db.collection("Usuarios").document(correo).set(
            hashMapOf(
                "nombre" to nombre,
                "apellido" to apellido,
                "celular" to celular
            )
        )
    }

    fun mostrarMensajeRegistro(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Registro")
        builder.setMessage("Usuario registrado exitosamente")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    fun mostrarMensaje(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Usuario o contraseña incorrectos")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}
