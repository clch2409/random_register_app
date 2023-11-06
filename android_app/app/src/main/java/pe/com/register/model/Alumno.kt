package pe.com.register.model

class Alumno {

    var nombres = ""
    var apellidoPaterno = ""
    var apellidoMaterno = ""
    var dni = ""
    var telefono = ""
    var email = ""
    var sexo = ""

    constructor()

    constructor(
        nombres: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        dni: String,
        email : String,
        telefono: String,
        sexo: String
    ) {
        this.nombres = nombres
        this.apellidoPaterno = apellidoPaterno
        this.apellidoMaterno = apellidoMaterno
        this.dni = dni
        this.email = email
        this.telefono = telefono
        this.sexo = sexo
    }
}