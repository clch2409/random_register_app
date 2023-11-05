package pe.com.register.interfaces

interface IAlumno {

    fun validarDNI(dni : String) : Boolean

    fun validarTelefono(telefono : String): Boolean

    fun crearNombreUsuario(dni: String, nombre : String, apellidoPaterno : String, apellidoMaterno : String) : String
}