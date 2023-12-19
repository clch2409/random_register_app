package pe.com.register.service

import pe.com.register.model.Alumno
import pe.com.register.model.Curso
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AlumnoService {

    @GET("alumno")
    suspend fun listarAlumnos() : Response<List<Alumno>?>?

    @GET("alumno/activos")
    suspend fun listarAlumnosActivos() : Response<List<Alumno>?>?

    @GET("alumno/{id}")
    suspend fun buscarPorIdAlumno(@Path("id") id : Long) : Response<Alumno?>?

    @POST("alumno")
    suspend fun agregarAlumno(@Body alumno : Alumno, @Query("id_curso") idCurso : Long) : Response<Alumno>?

    @PUT("alumno/{id}")
    suspend fun actualizarAlumno(@Path("id") id : Long, @Body alumno : Alumno) : Response<Alumno?>?

    @DELETE("alumno/{id}")
    suspend fun eliminarAlumno(@Path("id") id : Long) : Response<Alumno?>?

}