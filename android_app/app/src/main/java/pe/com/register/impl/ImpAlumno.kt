package pe.com.register.impl

import pe.com.register.interfaces.IAlumno

class ImpAlumno : IAlumno{
    override fun validarDNI(dni: String): Boolean {
        return dni.length == 8 && dni.matches("^\\d{8}$".toRegex())
    }

    override fun validarTelefono(telefono: String): Boolean {
        return telefono.length == 9 && telefono.matches("^9\\d{8}$".toRegex())
    }

    override fun crearNombreUsuario(
        dni: String,
        nombre : String,
        apellidoPaterno: String,
        apellidoMaterno: String
    ): String {
        return "${nombre[0]}${apellidoPaterno[0]}${apellidoMaterno[0]}$dni"
    }


}