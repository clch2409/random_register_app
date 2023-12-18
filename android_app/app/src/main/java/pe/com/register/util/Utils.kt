package pe.com.register.util

import android.content.Context
import androidx.appcompat.app.AlertDialog
import android.content.DialogInterface

object Utils {

    fun showDialog(titulo : String, mensaje : String, contexto : Context) : AlertDialog.Builder{
        val mensajeEmergente = AlertDialog.Builder(contexto)

        mensajeEmergente.apply {
            setTitle(titulo)
            setMessage(mensaje)
        }

        return mensajeEmergente
    }

}