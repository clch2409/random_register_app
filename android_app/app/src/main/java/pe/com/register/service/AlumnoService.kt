package pe.com.register.service

import pe.com.register.model.Alumno
import pe.com.register.model.Curso
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AlumnoService {

    @GET("alumno")
    suspend fun listarAlumnos() : Call<List<Alumno>?>?

    @GET("alumno/inactivos")
    suspend fun listarAlumnosInactivos() : Call<List<Alumno>?>?

    @GET("alumno/{id}")
    suspend fun buscarPorIdAlumno(@Path("id") id : Long) : Call<Alumno?>?

    @POST("alumno")
    suspend fun agregarCurso(@Body curso : Curso) : Call<Alumno?>?

    @PUT("alumno/{id}")
    suspend fun actualizarCurso(@Path("id") id : Long) : Call<Alumno?>?

    @DELETE("alumno/{id}")
    suspend fun eliminarCurso(@Path("id") id : Long) : Call<Alumno?>?

}