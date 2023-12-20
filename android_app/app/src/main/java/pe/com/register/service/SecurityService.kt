package pe.com.register.service

import pe.com.register.model.Curso
import pe.com.register.model.PayloadUsuario
import pe.com.register.model.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SecurityService {

    @POST("sq/iniciar_sesion")
    suspend fun iniciarSesion(@Body usuario : PayloadUsuario) : Response<Usuario>?

    @POST("sq/registro")
    suspend fun registrarUsuario(@Body usuario : Usuario) : Response<Usuario>?

}