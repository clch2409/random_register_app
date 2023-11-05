package pe.com.register

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import pe.com.register.databinding.ActivityRegisterCourseBinding
import pe.com.register.impl.ImpCurso

class RegisterCourseActivity : AppCompatActivity() {

    private val impCurso = ImpCurso()
    private lateinit var binding : ActivityRegisterCourseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterCourseBinding.inflate(layoutInflater)
        initUi()
        setContentView(binding.root)
    }

    private fun initUi(){
        fillingFrecuencySpinner()
        fillingTeachersSpinner()
    }

    private fun fillingFrecuencySpinner(){
        val frecuencyAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, impCurso.llenarSpinerFrecuencia())
        binding.spFrecuencias.adapter = frecuencyAdapter
    }

    private fun fillingTeachersSpinner(){
        val teacherAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, impCurso.llenarSpinerProfesores())
        binding.spFrecuencias.adapter = teacherAdapter
    }

    private fun showingMessageAndSending(titulo: String, mensaje : String){
        val mensajeEmergente = AlertDialog.Builder(this)

        mensajeEmergente.apply {
            setTitle(titulo)
            setMessage(mensaje)
            setPositiveButton("SI") { _: DialogInterface?, _: Int ->
            }
            setNegativeButton("NO"){ _: DialogInterface?, _: Int ->

            }
        }

        mensajeEmergente.create()
        mensajeEmergente.show()
    }
}