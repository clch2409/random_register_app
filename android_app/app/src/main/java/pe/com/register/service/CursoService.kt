package pe.com.register.service

import pe.com.register.model.Curso
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CursoService {
    @GET("curso")
    suspend fun listarTodos() : Response<List<Curso>?>?

    @GET("curso/activos")
    suspend fun listarActivos() : Response<List<Curso>?>?

    @GET("curso/{id}")
    suspend fun buscarPorId(@Path("id") id : Long) : Response<Curso?>?

    @POST("curso")
    suspend fun agregarCurso(@Body curso : Curso) : Response<Curso?>?

    @PUT("curso/{id}")
    suspend fun actualizarCurso(@Path("id") id : Long) : Response<Curso?>?

    @DELETE("curso/{id}")
    suspend fun eliminarCurso(@Path("id") id : Long) : Response<Curso?>?
}