package pe.com.register.remote

import pe.com.register.service.AlumnoService
import pe.com.register.service.CursoService
import pe.com.register.service.SecurityService

object Api {

    private var BASE_URL = "http://10.0.2.2:8080/api/v1/"

    val alumnoService: AlumnoService? get() =
        RetrofitClient.getConnection(BASE_URL)?.create(AlumnoService::class.java)

    val cursoService:CursoService? get() = RetrofitClient
        .getConnection(BASE_URL)
        ?.create(CursoService::class.java)

    val securityService : SecurityService? get()  =
        RetrofitClient.getConnection(BASE_URL)?.create(SecurityService::class.java)

}