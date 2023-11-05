package pe.com.register.interfaces

interface ICurso {
    fun llenarSpinerFrecuencia() : MutableList<String>

    fun llenarSpinerProfesores() : MutableList<String>
}