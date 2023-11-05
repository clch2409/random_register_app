package pe.com.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pe.com.register.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.btnMainAlumno.setOnClickListener { sendToStudent() }
        binding.btnMainCurso.setOnClickListener { sendToCourse() }


        setContentView(binding.root)
    }

    private fun sendToStudent(){
        val intent = Intent(applicationContext, RegisterStudentActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun sendToCourse(){
        val intent = Intent(applicationContext, RegisterCourseActivity::class.java)
        startActivity(intent)
        finish()
    }
}