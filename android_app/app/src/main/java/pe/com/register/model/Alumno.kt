package pe.com.register.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Alumno {

    @SerializedName("idAlumno")
    @Expose
    var idAlumno = 0L
    @SerializedName("nombres")
    @Expose
    var nombres = ""
    @SerializedName("apellidos")
    @Expose
    var apellidos = ""
    @SerializedName("dni")
    @Expose
    var dni = ""
    @SerializedName("telefono")
    @Expose
    var telefono = ""
    @SerializedName("genero")
    @Expose
    var sexo = ""
    @SerializedName("activo")
    @Expose
    var activo = true

    constructor()
    constructor(
        idAlumno: Long,
        nombres: String,
        apellidos: String,
        dni: String,
        telefono: String,
        sexo: String,
        activo: Boolean
    ) {
        this.idAlumno = idAlumno
        this.nombres = nombres
        this.apellidos = apellidos
        this.dni = dni
        this.telefono = telefono
        this.sexo = sexo
        this.activo = activo
    }


}