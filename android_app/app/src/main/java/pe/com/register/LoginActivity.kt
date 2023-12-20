package pe.com.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.com.register.databinding.ActivityLoginBinding
import pe.com.register.model.PayloadUsuario
import pe.com.register.model.Usuario
import pe.com.register.remote.Api
import pe.com.register.service.SecurityService
import pe.com.register.util.Utils
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var securityService: SecurityService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        securityService = Api.securityService
        binding.btnIniciar.setOnClickListener {
            getUserData()
        }
        binding.forgetPass.setOnClickListener {
            goToRegister()
        }

        setContentView(binding.root)
    }

    private fun getUserData(){
        val username = binding.txtUsuario.text.toString()
        val password = binding.txtContrasena.text.toString()

        if (username == "" || password == ""){
            Utils.showSnackBar("Ingrese todos los datos solicitados", binding.root)
        }
        else{
            val usuario = PayloadUsuario(username, password)
            doLogin(usuario)
        }
    }

    private fun doLogin(payloadUsuario: PayloadUsuario){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response : Response<Usuario>? =
                    securityService?.iniciarSesion(payloadUsuario)

                runOnUiThread{
                    if(response?.code() == 403){
                        Utils.showSnackBar("Los datos ingresados no son coincidentes", binding.root)
                    }
                    else{
                        goToMain()
                    }
                }

            }catch (ex : Exception){
                Log.e("DO_LOGIN", ex.toString())
            }
        }
    }

    private fun goToMain(){
        finish()
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
    }

    private fun goToRegister(){
        finish()
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
    }
}