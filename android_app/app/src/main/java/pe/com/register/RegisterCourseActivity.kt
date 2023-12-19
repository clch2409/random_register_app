package pe.com.register

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
import pe.com.register.adapter.CourseAdapter
import pe.com.register.databinding.ActivityRegisterCourseBinding
import pe.com.register.impl.ImpCurso
import pe.com.register.model.Curso
import pe.com.register.remote.Api
import pe.com.register.service.CursoService
import pe.com.register.util.Utils
import retrofit2.Response

class RegisterCourseActivity : AppCompatActivity() {

    private val impCurso = ImpCurso()
    private lateinit var binding : ActivityRegisterCourseBinding
    private var listadoCursos : List<Curso>? = null
    private var cursoService : CursoService? = null
    private var listadoProfesores = mutableListOf<String>()
    private lateinit var listadoFrecuencias : List<String>
    private var posicion = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterCourseBinding.inflate(layoutInflater)
        cursoService = Api.cursoService
        listadoFrecuencias = impCurso.llenarSpinerFrecuencia()
        binding.btnRegistrar.setOnClickListener { getInformation(1) }
        binding.btnActualizar.setOnClickListener { getInformation(2) }
        binding.btnEliminar.setOnClickListener { getInformation(3) }
        binding.btnSalir.setOnClickListener { goToMain() }
        binding.btnLimpiar.setOnClickListener { cleanBoxes() }
        binding.listViewCursos.setOnItemClickListener{ adapterView: AdapterView<*>, view2: View, i: Int, l: Long ->
            posicion = i
            placeCourseInfo(listadoCursos!![i])
        }
        getCourses()

        setContentView(binding.root)
    }



    private fun initUi(){
        fillingFrecuencySpinner(listadoFrecuencias)
        fillingTeachersSpinner(listadoProfesores)
        addCourseToListView()
    }

    private fun fillingFrecuencySpinner(listadoFrecuencias : List<String>){
        val frecuencyAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listadoFrecuencias)
        binding.spFrecuencias.adapter = frecuencyAdapter
    }

    private fun fillingTeachersSpinner(listadoProfesores : List<String>){
        val teacherAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listadoProfesores)
        binding.spProfesores.adapter = teacherAdapter
    }

    private fun getInformation(funcion : Int){
        val courseName = binding.txtNombreCurso.text.toString()
        val courseDuration = binding.txtDuracion.text.toString()
        val coursePrice = binding.txtPrecio.text.toString()
        val courseFrecuency = binding.spFrecuencias.selectedItem
        val courseTeacher = binding.spProfesores.selectedItem
        val courseState = binding.chkEstadoCurso.isChecked
        val frecuencyPosition = binding.spFrecuencias.selectedItemPosition
        val teacherPosition = binding.spProfesores.selectedItemPosition

        if (boxesEmpty(courseName, courseDuration, coursePrice)){
            showSnackbar("Ingrese los datos completos para poder continuar")
        }
        else if (frecuencyPosition == 0 || teacherPosition == 0){
            showSnackbar("Seleccione una frecuencia y un profesor válido para poder continuar")
        }
        else{
            val cursoSeleccionado = if(posicion >= 0) listadoCursos!![posicion] else Curso()
            val curso = Curso(courseName, coursePrice.toDouble(), courseDuration.toByte(), courseFrecuency.toString(), courseTeacher.toString(), courseState)
            when(funcion){
                1 -> validateRegistration(curso, funcion)
                2 -> {
                    if (posicion == -1){
                        showSnackbar("Seleccione un curso para actualizar o eliminar")
                    }else{
                        validateUpdate(curso, funcion, cursoSeleccionado.idCurso)
                    }
                }
                3 ->  {
                    if (posicion == -1){
                        showSnackbar("Seleccione un curso para actualizar o eliminar")
                    }else{
                        validateDelete(funcion, cursoSeleccionado.idCurso)
                    }
                }
            }
        }
    }

    private fun getCourses(){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response : Response<List<Curso>?>? =
                    cursoService?.listarTodos()

                Log.i("CURSOS", response?.body().toString())

                runOnUiThread{
                    listadoCursos = response?.body()!!
                    listadoProfesores.clear()
                    listadoProfesores.add("Seleccione un profesor: ")
                    listadoCursos?.forEach {
                        Log.i("CURSO", it.nombre)
                        if (!listadoProfesores.contains(it.profesor)){
                            listadoProfesores.add(it.profesor)
                        }
                    }
                    initUi()
                }

            }catch(ex : Exception){
                Log.e("GET_COURSES", ex.toString())
            }
        }
    }

    private fun validateRegistration(curso : Curso, funcion : Int){
        showMessageAndDoSomething("Registro de Curso",
            "¿Desea registrar el curso con estos datos?:" +
                    "\n" +
                    "\t-Nombre: ${curso.nombre}" +
                    "\n" +
                    "\t-Precio: S/.${curso.precio}" +
                    "\n" +
                    "\t-Duración: ${curso.duracion} semanas" +
                    "\n" +
                    "\t-Frecuencia: ${curso.frecuencia}" +
                    "\n" +
                    "\t-Docente: ${curso.profesor}",
            curso, 0, funcion)
    }

    private fun registerCourse(curso: Curso) {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response : Response<Curso>? =
                    cursoService!!.agregarCurso(curso)

                Log.i("REGISTRO", response?.body().toString())

                runOnUiThread{
                    getCourses()
                    cleanBoxes()
                }

            }catch(ex : Exception){
                Log.e("REGISTER_COURSE", ex.toString())
            }
        }
    }

    private fun updateCourse(curso: Curso, idCurso : Long) {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response : Response<Curso>? =
                    cursoService!!.actualizarCurso(idCurso, curso)

                Log.i("REGISTRO", response?.body().toString())

                runOnUiThread{
                    getCourses()
                    cleanBoxes()
                }

            }catch(ex : Exception){
                Log.e("REGISTER_COURSE", ex.toString())
            }
        }
    }

    private fun validateUpdate(curso : Curso, funcion : Int, idCurso : Long){
        showMessageAndDoSomething("Actualizar de Curso",
            "¿Dese actualizar el curso con ID -> $idCurso?:" +
                    "\n" +
                    "\t-Nombre: ${curso.nombre}" +
                    "\n" +
                    "\t-Precio: S/.${curso.precio}" +
                    "\n" +
                    "\t-Duración: ${curso.duracion} semanas" +
                    "\n" +
                    "\t-Frecuencia: ${curso.frecuencia}" +
                    "\n" +
                    "\t-Docente: ${curso.profesor}",
            curso, idCurso, funcion)
    }

    private fun deleteCourse( idCurso : Long) {
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response : Response<Curso>? =
                    cursoService!!.eliminarCurso(idCurso)

                Log.i("REGISTRO", response?.body().toString())

                runOnUiThread{
                    getCourses()
                    cleanBoxes()
                }

            }catch(ex : Exception){
                Log.e("REGISTER_COURSE", ex.toString())
            }
        }
    }

    private fun validateDelete(funcion : Int, idCurso : Long){
        showMessageAndDoSomething("Eliminar de Curso",
            "¿Desea eliminar el curso con ID -> $idCurso",
            Curso(), idCurso, funcion)
    }

    private fun addCourseToListView(){
        var adapter = CourseAdapter(this@RegisterCourseActivity, listadoCursos!!)
        binding.listViewCursos.adapter = adapter
    }

    private fun showMessageAndDoSomething(titulo: String, mensaje : String, curso : Curso, idCurso : Long, funcion : Int){
        val mensajeEmergente = Utils.showDialog(titulo, mensaje, this@RegisterCourseActivity)

        mensajeEmergente.apply {
            setPositiveButton("SI") { _: DialogInterface?, _: Int ->
                when(funcion){
                    1 -> registerCourse(curso)
                    2 -> updateCourse(curso, idCurso)
                    3 -> deleteCourse(idCurso)
                }
            }
            setNegativeButton("NO"){ _: DialogInterface?, _: Int ->
            }
        }
        mensajeEmergente.create()
        mensajeEmergente.show()
    }



    private fun placeCourseInfo(curso: Curso) {
        binding.txtNombreCurso.setText(curso.nombre)
        binding.txtDuracion.setText(curso.duracion.toString())
        binding.txtPrecio.setText(curso.precio.toString())
        binding.spFrecuencias.setSelection(listadoFrecuencias.indexOf(curso.frecuencia))
        binding.spProfesores.setSelection(listadoProfesores.indexOf(curso.profesor))
        binding.chkEstadoCurso.isChecked = curso.activo
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

    private fun cleanBoxes(){
        binding.txtNombreCurso.text.clear()
        binding.txtPrecio.text.clear()
        binding.txtDuracion.text.clear()
        binding.spProfesores.setSelection(0)
        binding.spFrecuencias.setSelection(0)
        binding.chkEstadoCurso.isChecked = false
    }
}