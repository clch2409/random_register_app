package pe.com.register.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pe.com.register.R
import pe.com.register.model.Curso

class CourseAdapter(context : Context?, var listadoCursos : List<Curso>) : BaseAdapter() {

    private var layoutInflater : LayoutInflater
    init{
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return listadoCursos.size
    }

    override fun getItem(p0: Int): Any {
        return listadoCursos[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view = p1
        if (view == null){
            view = layoutInflater.inflate(R.layout.course_list_item, p2, false)
            var curso = getItem(p0) as Curso
            var txtCurso = view!!.findViewById<TextView>(R.id.txtNombreCurso)
            var txtDuracion = view!!.findViewById<TextView>(R.id.txtDuracionCurso)
            var txtProfesor = view!!.findViewById<TextView>(R.id.txtProfesorCurso)
            var txtFrecuencia = view!!.findViewById<TextView>(R.id.txtFrecuenciaCurso)

            txtCurso.text = curso.nombre
            txtDuracion.text = "${curso.duracion} semanas"
            txtProfesor.text = curso.profesor
            txtFrecuencia.text = "${curso.frecuencia}"
        }
        return view
    }
}