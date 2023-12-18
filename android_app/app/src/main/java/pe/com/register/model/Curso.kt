package pe.com.register.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Curso {

    @SerializedName("idCurso")
    @Expose
    var idCurso : Long = 0L
    @SerializedName("nombre")
    @Expose
    var nombre : String = ""
    @SerializedName("precio")
    @Expose
    var precio : Double = 0.0
    @SerializedName("duracion")
    @Expose
    var duracion : Byte = 0
    @SerializedName("frecuencia")
    @Expose
    var frecuencia : String = ""
    @SerializedName("profesor")
    @Expose
    var profesor : String = ""

    constructor()
    constructor(nombre: String, precio: Double, duracion: Byte, frecuencia : String, profesor: String) {
        this.nombre = nombre
        this.precio = precio
        this.duracion = duracion
        this.frecuencia = frecuencia
        this.profesor = profesor
    }
}