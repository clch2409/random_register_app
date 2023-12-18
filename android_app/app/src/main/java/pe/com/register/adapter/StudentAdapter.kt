package pe.com.register.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.register.R
import pe.com.register.RegisterStudentActivity
import pe.com.register.databinding.ActivityRegisterStudentBinding
import pe.com.register.databinding.StudentListItemBinding
import pe.com.register.model.Alumno

class StudentAdapter(context : Context?, val listadoAlumnos : List<Alumno>?): BaseAdapter() {

    private var layoutInflater : LayoutInflater
    init{
        layoutInflater = LayoutInflater.from(context)
    }
    override fun getCount(): Int {
        return listadoAlumnos!!.size
    }

    override fun getItem(position: Int): Any {
        return listadoAlumnos!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view = p1
        if (view == null){
            view = layoutInflater.inflate(R.layout.student_list_item, p2, false)
            val student = getItem(p0) as Alumno
            val txtNombre = view!!.findViewById<TextView>(R.id.txtNombreAlumno)
            val txtApellido = view!!.findViewById<TextView>(R.id.txtApellidoAlumno)
            val txtDni = view!!.findViewById<TextView>(R.id.txtDniAlumno)
            val txtSexo = view!!.findViewById<TextView>(R.id.txtSexoAlumno)

            txtNombre.text = student.nombres
            txtApellido.text = student.apellidos
            txtDni.text = student.dni
            txtSexo.text = student.sexo
        }
        return view
    }
}