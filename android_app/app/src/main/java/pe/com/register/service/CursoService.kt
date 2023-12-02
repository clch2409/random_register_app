package pe.com.register.service

import pe.com.register.model.Curso
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CursoService {
    @GET("curso")
    suspend fun listarTodos() : Call<List<Curso>?>?

    @GET("curso/inactivos")
    suspend fun listarInactivos() : Call<List<Curso>?>?

    @GET("curso/{id}")
    suspend fun buscarPorId(@Path("id") id : Long) : Call<Curso?>?

    @POST("curso")
    suspend fun agregarCurso(@Body curso : Curso) : Call<Curso?>?

    @PUT("curso/{id}")
    suspend fun actualizarCurso(@Path("id") id : Long) : Call<Curso?>?

    @DELETE("curso/{id}")
    suspend fun eliminarCurso(@Path("id") id : Long) : Call<Curso?>?
}