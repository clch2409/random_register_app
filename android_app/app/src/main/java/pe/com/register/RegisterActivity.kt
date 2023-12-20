package pe.com.register

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.com.register.databinding.ActivityRegisterBinding
import pe.com.register.impl.ImpAlumno
import pe.com.register.model.Usuario
import pe.com.register.remote.Api
import pe.com.register.service.SecurityService
import pe.com.register.util.Utils
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private val securityService : SecurityService? = Api.securityService
    private var impAlumno = ImpAlumno()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        binding.backRegister.setOnClickListener{
            showMesssageAndDoSomething("Regresar a Inicio de Sesión",
                "¿Desea regresar a la pantalla de Inicio de Sesión?" +
                        "\n" +
                        "Perderá todo los datos ingresados en las cajas de texto",
                Usuario(), 2)
        }
        binding.buttonRegister.setOnClickListener {
            getRegisterData(1)
        }


        setContentView(binding.root)
    }

    private fun getRegisterData(funcion : Int){
        val names = binding.txtNombres.text.toString()
        val lastNames = binding.txtApellidos.text.toString()
        val dni = binding.txtDni.text.toString()
        val email = binding.txtEmail.text.toString()
        val pass = binding.txtPassword.text.toString()
        val repeat = binding.txtPassRepeat.text.toString()

        if (checkEmpty(names, lastNames, dni, email, pass, repeat)){
            Utils.showSnackBar("Ingrese todos los datos solicitados", binding.root)
        }
        else if (!impAlumno.validarDNI(dni)){
            Utils.showSnackBar("El DNI ingresado es incorrecto", binding.root)
        }
        else if (pass != repeat){
            Utils.showSnackBar("Sus contraseñas no coinciden", binding.root)
        }
        else{
            val username = impAlumno.crearNombreUsuario(dni, names, lastNames)
            val usuario = Usuario(0, username, "$names $lastNames", pass, email, true)
            validateRegister(usuario, funcion)
        }
    }

    private fun validateRegister(usuario : Usuario, funcion : Int){
        showMesssageAndDoSomething("Registro de Usuario",
            "¿Desea registrarse con esta información?" +
                    "\n" +
                    "\t-Nombre Completo: ${usuario.nombreCompleto}" +
                    "\n" +
                    "\t-Email: ${usuario.email}" +
                    "\n" +
                    "\t-Nombre de Usuario (tomar nota): ${usuario.nombreUsuario}",
            usuario, funcion)
    }

    private fun doRegister(usuario : Usuario){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response : Response<Usuario>? =
                    securityService?.registrarUsuario(usuario)

                if (response!!.isSuccessful){
                    runOnUiThread{
                        val usuarioRegistrado = response?.body()
                        showMesssageAndDoSomething("Usuario Registrado",
                            "Usted se ha registrado Exitosamente 🥳." +
                                    "\n" +
                                    "-Nombre de usuario: ${usuarioRegistrado?.nombreUsuario}" +
                                    "\n\n" +
                                    "Ahora será redirigido a la pantalla de Inicio de Sesión.",
                            Usuario(), 2)
                    }
                }else{
                    Log.i("REGISTRO", response.toString())
                }

            }catch(ex : Exception){
                Log.e("DO_REGISTER", ex.toString())
            }
        }
    }

    private fun showMesssageAndDoSomething(titulo : String, mensaje : String, usuario : Usuario, funcion : Int){
        val dialog = Utils.showDialog(titulo, mensaje, this@RegisterActivity)

        dialog.apply {
            setPositiveButton("SI"){ dialogInterface: DialogInterface, i: Int ->
                when(funcion){
                    1 -> doRegister(usuario)
                    2 -> goToLogin()
                }
            }
            setNegativeButton("NO"){ dialogInterface: DialogInterface, i: Int ->
            }
        }

        dialog.create()
        dialog.show()
    }

    private fun checkEmpty(names : String, last : String, dni : String, email : String, pass : String, repeat : String) : Boolean{
        return names.isEmpty() || last.isEmpty() || dni.isEmpty() || email.isEmpty() || pass.isEmpty() || repeat.isEmpty()
    }

    private fun goToLogin(){
        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
    }
}