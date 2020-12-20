package com.example.tienda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_registrarse.setOnClickListener { registrarse() }
        btn_sesion.setOnClickListener { iniciarSesion() }
    }


    fun registrarse(){
        val intentRegistro = Intent(
            this,
            Registrar::class.java
        )
        startActivity(intentRegistro)
    }

    fun iniciarSesion(){
        title = "Iniciar Sesión"

        val correo = ed_correo.text.toString()

        if(ed_correo.text.isNotEmpty() && ed_password.text.isNotEmpty()){

            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(ed_correo.text.toString(), ed_password.text.toString())
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        val intent = Intent(
                            this,
                            EleccionProductos::class.java
                        )
                        intent.putExtra("Correo", correo)
                        startActivity(intent)
                    } else {
                        mostrarMensaje()
                    }
                }
        }

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
