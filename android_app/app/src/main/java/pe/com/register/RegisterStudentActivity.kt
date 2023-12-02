package pe.com.register

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.BaseAdapter
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import pe.com.register.adapter.StudentAdapter
import pe.com.register.databinding.ActivityRegisterStudentBinding
import pe.com.register.impl.ImpAlumno
import pe.com.register.model.Alumno

class RegisterStudentActivity : AppCompatActivity() {

    private val impAlumno = ImpAlumno()
    private lateinit var binding : ActivityRegisterStudentBinding
    private var listado : MutableList<Alumno> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*TODO: AGREGAR UN DIVIDER PARA EL LISTVIEW*/
        binding = ActivityRegisterStudentBinding.inflate(layoutInflater)
        binding.btnRegistrar.setOnClickListener{getStudentData()}
        binding.btnSalir.setOnClickListener { goToMain() }
        binding.btnLimpiar.setOnClickListener { cleanBoxes() }

        setContentView(binding.root)
    }

    private fun getStudentData(){
        val nombres = binding.txtNombres.text.toString()
        val ap = binding.txtApellidoP.text.toString()
        val am = binding.txtApellidoM.text.toString()
        val dni = binding.txtDni.text.toString()
        val email = binding.txtEmail.text.toString()
        val telefono = binding.txtTelefono.text.toString()
        val sexo =
            if (binding.rbMasc.isChecked) "Masculino"
            else if (binding.rbFem.isChecked) "Femenino"
            else if (binding.rbOtro.isChecked) "Otro"
            else ""

        if (boxesEmpty(nombres, ap, am, dni, email, telefono)){
            showSnackbar("Ingrese TODOS los datos en las cajas de text")
        }
        else if(sexo == ""){
            showSnackbar("Seleccione algún sexo")
        }
        else if(!impAlumno.validarDNI(dni)){
            showSnackbar("El DNI ingresado no es correcto")
        }
        else if(!impAlumno.validarTelefono(telefono)){
            showSnackbar("El telefono ingresado no es correcto")
        }
        else{
            val alumno = Alumno(nombres, ap, am, dni, email, telefono, sexo)
            showMessageSendToMain("Registrar alumno",
                "¿Desea registrar al alumno con estos datos? " +
                        "\n" +
                        "\t-Nombres: ${alumno.nombres}"+
                        "\n" +
                        "\t-Apellido Paterno: ${alumno.apellidoPaterno}"+
                        "\n" +
                        "\t-Apellido Materno: ${alumno.apellidoMaterno}"+
                        "\n" +
                        "\t-DNI: ${alumno.dni}"+
                        "\n" +
                        "\t-Email: ${alumno.email}"+
                        "\n" +
                        "\t-Telefono: ${alumno.telefono}"+
                        "\n" +
                        "\t-Sexo: ${alumno.sexo}"+
                        "\n" +
                        "\t-Nombre de Usuario (Proximamente): ${impAlumno.crearNombreUsuario(alumno.dni, alumno.nombres, alumno.apellidoPaterno, alumno.apellidoMaterno)}",
                alumno)


        }
    }

    private fun addStudent(alumno : Alumno){
        listado.add(alumno)

        var adapter = StudentAdapter(applicationContext, listado)
        binding.studentList.adapter = adapter
    }

    private fun showMessageSendToMain(titulo: String, mensaje : String, alumno : Alumno){
        val mensajeEmergente = AlertDialog.Builder(this)

        mensajeEmergente.apply {
            setTitle(titulo)
            setMessage(mensaje)
            setPositiveButton("SI") { _: DialogInterface?, _: Int ->
                addStudent(alumno)
                cleanBoxes()
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

    private fun boxesEmpty(nombre:String, ap:String, am:String, dni:String, email:String, telefono:String):Boolean{
        if (nombre.isEmpty() || ap.isEmpty() || am.isEmpty() || dni.isEmpty() || email.isEmpty() || telefono.isEmpty()){
            return true
        }
        return false
    }

    private fun cleanBoxes(){
        binding.txtNombres.text.clear()
        binding.txtApellidoP.text.clear()
        binding.txtApellidoM.text.clear()
        binding.txtDni.text.clear()
        binding.txtTelefono.text.clear()
        binding.txtEmail.text.clear()
        binding.rgrpSexo.clearCheck()
    }
}