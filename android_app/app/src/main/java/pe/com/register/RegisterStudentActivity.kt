package pe.com.register

import android.R
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
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
    private var posicion = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*TODO: AGREGAR UN DIVIDER PARA EL LISTVIEW*/
        binding = ActivityRegisterStudentBinding.inflate(layoutInflater)
        binding.btnRegistrar.setOnClickListener{getStudentData(1)}
        binding.btnActualizar.setOnClickListener { getStudentData(2) }
        binding.btnEliminar.setOnClickListener { getStudentData(3) }
        binding.btnSalir.setOnClickListener { goToMain() }
        binding.btnLimpiar.setOnClickListener { cleanBoxes() }
        binding.studentList.setOnItemClickListener{ adapterView: AdapterView<*>, view2: View, i: Int, l: Long ->
            posicion = i
            placeStudenInfo(listado!![i])
        }
        alumnoService = Api.alumnoService
        cursoService = Api.cursoService
        getStudents()
        getCourses()
        setContentView(binding.root)
    }

    private fun getStudentData(funcion : Int){
        val nombres = binding.txtNombres.text.toString()
        val ap = binding.txtApellidos.text.toString()
        val dni = binding.txtDni.text.toString()
        val telefono = binding.txtTelefono.text.toString()
        val sexo =
            if (binding.rbMasc.isChecked) "Masculino"
            else if (binding.rbFem.isChecked) "Femenino"
            else if (binding.rbOtro.isChecked) "Otro"
            else ""
        val activo = binding.chkActivo.isChecked

        if (boxesEmpty(nombres, ap, dni, telefono)){
            showSnackbar("Ingrese TODOS los datos en las cajas de texto")
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
            alumno.nombres = nombres; alumno.apellidos = ap; alumno.dni = dni; alumno.telefono = telefono
            alumno.sexo = sexo; alumno.activo = activo

            when(funcion){
                1 -> validateRegistraition(alumno, curso!!)
                2 -> {
                    if (posicion != -1){
                        val alumnoSeleccionado = listado?.get(posicion)
                        validateUpdate(alumno!!, alumnoSeleccionado!!.idAlumno)
                    }
                    else{
                        showSnackbar("Seleccione un alumno para poder actualizar o eliminar")

                    }
                }
                3 -> {
                    if (posicion != -1){
                        val alumnoSeleccionado = listado?.get(posicion)
                        validateDelete(alumnoSeleccionado!!.idAlumno)
                    }
                    else{
                        showSnackbar("Seleccione un alumno para poder actualizar o eliminar")

                    }
                }
            }
        }
    }

    private fun getStudents(){
        var response : Response<List<Alumno>?>?
        CoroutineScope(Dispatchers.IO).launch {
            try{
                response =
                alumnoService!!.listarAlumnos()

                Log.i("ALUMNOS", response?.body().toString())

                runOnUiThread{
                    listado = emptyList()
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

    private fun validateRegistraition(alumno : Alumno, curso : Curso){
        showMessageAndDoSomething("Registrar alumno",
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
                    "\t-Nombre de Usuario (Proximamente): ${impAlumno.crearNombreUsuario(alumno.dni, alumno.nombres, alumno.apellidos)}",
            alumno,
            0, curso?.idCurso!!, 1)
    }
    private fun updateStudent(alumno: Alumno, idAlumno : Long) {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response : Response<Alumno?>? =
                    alumnoService!!.actualizarAlumno(idAlumno, alumno)

                Log.i("ACTUALIZAR", response?.body().toString())

                runOnUiThread{
                    getStudents()
                    cleanBoxes()
                }

            }catch (ex : Exception){
                Log.e("UPDATE_STUDENT", ex.toString())
            }
        }
    }

    private fun validateUpdate(alumno : Alumno, idAlumno: Long){
        showMessageAndDoSomething("Actualizar alumno",
            "EL alumno: -> ${alumno.nombres} ${alumno.apellidos}. Tendrá los siguientes datos:" +
                    "\n" +
                    "\t-Nombres: ${alumno.nombres}"+
                    "\n" +
                    "\t-Apellidos: ${alumno.apellidos}" +
                    "\n" +
                    "\t-DNI: ${alumno.dni}"+
                    "\n" +
                    "\t-Telefono: ${alumno.telefono}"+
                    "\n" +
                    "\t-Sexo: ${alumno.sexo}",
            alumno, idAlumno, 0, 2)
    }

    private fun deleteStudent(idAlumno : Long){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response : Response<Alumno?>? =
                    alumnoService!!.eliminarAlumno(idAlumno)

                Log.i("ELIMINAR", response!!.toString())

                runOnUiThread{
                    getStudents()
                    cleanBoxes()
                }

            }catch (ex : Exception){
                Log.e("DELETE_STUDENT", ex.toString())
            }
        }
    }

    private fun validateDelete(codAlumno : Long){
        showMessageAndDoSomething("Eliminar alumno",
            "¿Dese eliminar al Alumno con ID -> $codAlumno?",
            Alumno(), codAlumno, 0, 3)
    }

    private fun showMessageAndDoSomething(titulo : String, mensaje : String, alumno : Alumno, idAlumno : Long, idCurso : Long = 0L, funcion : Int){
        val alertDialog : AlertDialog.Builder = Utils.showDialog(titulo, mensaje, this@RegisterStudentActivity)

        alertDialog.apply {
            setPositiveButton("SI") { dialogInterface: DialogInterface, i: Int ->
                when(funcion){
                    1 -> registerStudent(alumno, idCurso)
                    2 -> updateStudent(alumno, idAlumno)
                    3 -> deleteStudent(idAlumno)
                }
            }
            setNegativeButton("NO"){ dialogInterface: DialogInterface, i: Int ->
            }
        }

        alertDialog.create()
        alertDialog.show()
    }

    private fun placeStudenInfo(alumnoSeleccionado : Alumno){
        binding.txtNombres.setText(alumnoSeleccionado?.nombres)
        binding.txtApellidos.setText(alumnoSeleccionado?.apellidos)
        binding.txtDni.setText(alumnoSeleccionado?.dni)
        binding.txtTelefono.setText(alumnoSeleccionado?.telefono)
        binding.chkActivo.isChecked = alumnoSeleccionado.activo
        when(alumnoSeleccionado.sexo){
            "Masculino" -> binding.rbMasc.isChecked = true
            "Femenino" -> binding.rbFem.isChecked = true
            "Otro" -> binding.rbOtro.isChecked = true
        }
    }

    private fun addStudentToList(){
        val adapter = StudentAdapter(applicationContext, listado)
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
                    response.body()!!.forEach {
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

    private fun boxesEmpty(nombre:String, ap:String, dni:String, telefono:String):Boolean{
        if (nombre.isEmpty() || ap.isEmpty() || dni.isEmpty() || telefono.isEmpty()){
            return true
        }
        return false
    }

    private fun cleanBoxes(){
        binding.txtNombres.text.clear()
        binding.txtApellidos.text.clear()
        binding.txtDni.text.clear()
        binding.txtTelefono.text.clear()
        binding.spCursos.setSelection(0)
        binding.chkActivo.isChecked = false
        binding.rgrpSexo.clearCheck()
        posicion = -1
    }
}