package pe.com.register

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import pe.com.register.databinding.ActivityRegisterCourseBinding
import pe.com.register.impl.ImpCurso
import pe.com.register.model.Curso

class RegisterCourseActivity : AppCompatActivity() {

    private val impCurso = ImpCurso()
    private lateinit var binding : ActivityRegisterCourseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterCourseBinding.inflate(layoutInflater)
        initUi()
        binding.btnRegistrar.setOnClickListener { getInformation() }
        binding.btnSalir.setOnClickListener { goToMain() }
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
        binding.spProfesores.adapter = teacherAdapter
    }

    private fun getInformation(){
        val courseName = binding.txtNombreCurso.text.toString()
        val courseDuration = binding.txtDuracion.text.toString()
        val coursePrice = binding.txtPrecio.text.toString()
        val courseFrecuency = binding.spFrecuencias.selectedItem
        val courseTeacher = binding.spProfesores.selectedItem
        val frecuencyPosition = binding.spFrecuencias.selectedItemPosition
        val teacherPosition = binding.spProfesores.selectedItemPosition
        if (boxesEmpty(courseName, courseDuration, coursePrice)){
            showSnackbar("Ingrese los datos completos para poder continuar")
        }
        else if (frecuencyPosition == 0 || teacherPosition == 0){
            showSnackbar("Seleccione una frecuencia y un profesor válido para poder continuar")
        }
        else{
            val curso = Curso(courseName, coursePrice.toDouble(), courseDuration.toByte(), courseFrecuency.toString(), courseTeacher.toString())
            showMessageSendToMain("Registro de Curso",
                "¿Desea registrar el curso con estos datos?:" +
                        "\n" +
                        "\t-Nombre: ${curso.nombre}" +
                        "\n" +
                        "\t-Precio: S/.${curso.precio}" +
                        "\n" +
                        "\t-Duración: ${curso.duracion} semanas" +
                        "\n" +
                        "\t-Frecuencia: ${curso.frecuencia} semanas" +
                        "\n" +
                        "\t-Docente: ${curso.profesor}")
        }
    }

    private fun showMessageSendToMain(titulo: String, mensaje : String){
        val mensajeEmergente = AlertDialog.Builder(this)

        mensajeEmergente.apply {
            setTitle(titulo)
            setMessage(mensaje)
            setPositiveButton("SI") { _: DialogInterface?, _: Int ->
                goToMain()
            }
            setNegativeButton("NO"){ _: DialogInterface?, _: Int ->

            }
        }

        mensajeEmergente.create()
        mensajeEmergente.show()
    }

    private fun showSnackbar(mensaje:String){
        Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_LONG).show()
    }

    private fun goToMain(){
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun boxesEmpty(nombre:String, duracion:String, precio:String):Boolean{
        if (nombre.isEmpty() || duracion.isEmpty() || precio.isEmpty()){
            return true
        }
        return false
    }
}