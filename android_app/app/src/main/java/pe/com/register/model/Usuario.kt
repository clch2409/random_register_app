package pe.com.register.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Usuario {

    @SerializedName("idUsuario")
    @Expose
    var idUsuario = 0L
    @SerializedName("nombreUsuario")
    @Expose
    var nombreUsuario = ""
    @SerializedName("nombreCompleto")
    @Expose
    var nombreCompleto = ""
    @SerializedName("password")
    @Expose
    var password = ""
    @SerializedName("email")
    @Expose
    var email = ""
    @SerializedName("estado")
    @Expose
    var estado = false

    constructor()
    constructor(
        idUsuario: Long,
        nombreUsuario: String,
        nombreCompleto: String,
        password: String,
        email: String,
        estado: Boolean
    ) {
        this.idUsuario = idUsuario
        this.nombreUsuario = nombreUsuario
        this.nombreCompleto = nombreCompleto
        this.password = password
        this.email = email
        this.estado = estado
    }
}