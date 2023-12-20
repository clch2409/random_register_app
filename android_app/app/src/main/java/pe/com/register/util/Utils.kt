package pe.com.register.util

import android.content.Context
import androidx.appcompat.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import com.google.android.material.snackbar.Snackbar

object Utils {

    fun showSnackBar(mensaje : String, view : View){
        Snackbar.make(view, mensaje, Snackbar.LENGTH_LONG).show()
    }

    fun showDialog(titulo : String, mensaje : String, contexto : Context) : AlertDialog.Builder{
        val mensajeEmergente = AlertDialog.Builder(contexto)

        mensajeEmergente.apply {
            setTitle(titulo)
            setMessage(mensaje)
        }

        return mensajeEmergente
    }

}