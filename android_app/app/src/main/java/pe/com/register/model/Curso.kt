package pe.com.register.model

class Curso {

    var nombre : String = ""
    var precio : Double = 0.0
    var duracion : Byte = 0
    var frecuencia : String = ""
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