package pe.com.register

import android.R
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.com.register.adapter.StudentAdapter
import pe.com.register.databinding.ActivityRegisterStudentBinding
import pe.com.register.impl.ImpAlumno
import pe.com.register.model.Alumno
import pe.com.register.model.Curso
import pe.com.register.remote.Api
import pe.com.register.service.AlumnoService
import pe.com.register.service.CursoService
import pe.com.register.util.Utils
import retrofit2.Response

class RegisterStudentActivity : AppCompatActivity() {

    private val impAlumno = ImpAlumno()
    private lateinit var binding : ActivityRegisterStudentBinding
    private var listado : List<Alumno>? = null
    private var listadoCursos : List<Curso>? = null
    private var listadoCursosNombres : MutableList<String> = mutableListOf()
    private var alumnoService : AlumnoService? = null
    private var cursoService : CursoService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*TODO: AGREGAR UN DIVIDER PARA EL LISTVIEW*/
        binding = ActivityRegisterStudentBinding.inflate(layoutInflater)
        binding.btnRegistrar.setOnClickListener{getStudentData()}
        binding.btnSalir.setOnClickListener { goToMain() }
        binding.btnLimpiar.setOnClickListener { cleanBoxes() }
        alumnoService = Api.alumnoService
        cursoService = Api.cursoService
        getStudents()
        getCourses()
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
            val curso = listadoCursos?.get(binding.spCursos.selectedItemPosition)
            val alumno = Alumno()
            alumno.nombres = nombres; alumno.apellidos = "$ap $am"; alumno.dni = dni; alumno.telefono = telefono;
            alumno.sexo = sexo;
            showMessageAndRegister("Registrar alumno",
                "¿Desea registrar al alumno con estos datos? " +
                        "\n" +
                        "\t-Nombres: ${alumno.nombres}"+
                        "\n" +
                        "\t-Apellidos: ${alumno.apellidos}" +
                        "\n" +
                        "\t-DNI: ${alumno.dni}"+
                        "\n" +
                        "\t-Telefono: ${alumno.telefono}"+
                        "\n" +
                        "\t-Sexo: ${alumno.sexo}"+
                        "\n" +
                        "\t-Nombre de Usuario (Proximamente): ${impAlumno.crearNombreUsuario(alumno.dni, alumno.nombres, ap, am)}",
                alumno,
                curso?.idCurso!!)

        }
    }

    private fun getStudents(){
        var response : Response<List<Alumno>?>? = null
        CoroutineScope(Dispatchers.IO).launch {
            try{
                response =
                alumnoService!!.listarAlumnosActivos()

                Log.i("ALUMNOS", response?.body().toString())

                runOnUiThread{
                    listado = response!!.body()
                    addStudentToList()
                }
            }catch (ex : Exception){
                Log.e("GET_STUDENTS", ex.toString())
            }
        }
    }

    private fun registerStudent(alumno : Alumno, idCurso : Long){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response : Response<Alumno>? =
                    alumnoService!!.agregarAlumno(alumno, idCurso)

                Log.i("REGISTRO", response?.body().toString())

                runOnUiThread{
                    getStudents()
                    cleanBoxes()
                }

            }catch (ex : Exception){
                Log.e("REGISTER_STUDENT", ex.toString())
            }
        }
    }

    private fun showMessageAndRegister(titulo : String, mensaje : String, alumno : Alumno, idCurso : Long){
        val alertDialog : AlertDialog.Builder = Utils.showDialog(titulo, mensaje, this@RegisterStudentActivity)

        alertDialog.apply {
            setPositiveButton("SI") { dialogInterface: DialogInterface, i: Int ->
                registerStudent(alumno, idCurso)
            }
            setNegativeButton("NO"){ dialogInterface: DialogInterface, i: Int ->
            }
        }

        alertDialog.create()
        alertDialog.show()
    }

    private fun addStudentToList(){
        var adapter = StudentAdapter(applicationContext, listado)
        binding.studentList.adapter = adapter
    }


    private fun getCourses(){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response : Response<List<Curso>?>? =
                    cursoService!!.listarActivos()

                Log.i("CURSOS", response?.body().toString())

                runOnUiThread{
                    listadoCursos = response!!.body()
                    response!!.body()!!.forEach {
                        listadoCursosNombres.add(it.nombre)
                    }
                    placeCourses()
                }
            }catch (ex : Exception){
                Log.e("GET_COURSES", ex.toString())
            }
        }
    }

    private fun placeCourses(){
        val coursesAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, listadoCursosNombres)
        binding.spCursos.adapter = coursesAdapter
    }



    /*private fun showMessageSendToMain(titulo: String, mensaje : String, alumno : Alumno){
        val mensajeEmergente = AlertDialog.Builder(this)

        mensajeEmergente.apply {
            setTitle(titulo)
            setMessage(mensaje)
            setPositiveButton("SI") { _: DialogInterface?, _: Int ->
                addStudentToList()
                cleanBoxes()
            }
            setNegativeButton("NO"){ _: DialogInterface?, _: Int ->

            }
        }

        mensajeEmergente.create()
        mensajeEmergente.show()
    }*/

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
        binding.spCursos.setSelection(0)
        binding.rgrpSexo.clearCheck()
    }
}