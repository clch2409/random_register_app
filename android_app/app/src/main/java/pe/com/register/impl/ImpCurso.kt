package pe.com.register.impl

import pe.com.register.interfaces.ICurso

class ImpCurso : ICurso {
    override fun llenarSpinerFrecuencia(): MutableList<String> {
        val frecuencias : MutableList<String> = mutableListOf<String>()
        frecuencias.add("Seleccione la frecuencia del curso:")
        frecuencias.add("Diario")
        frecuencias.add("Interdiario")
        frecuencias.add("Sabados - Domingos")
        return frecuencias
    }

    override fun llenarSpinerProfesores(): MutableList<String> {
        val profesores : MutableList<String> = mutableListOf<String>()
        profesores.add("Seleccione un profesor que impartirá el curso:")
        profesores.add("Michael Bay")
        profesores.add("Sofía Vergara")
        profesores.add("Bruno Díaz")
        return profesores
    }
}