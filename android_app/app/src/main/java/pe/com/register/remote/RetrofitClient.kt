package pe.com.register.remote

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var retrofit : Retrofit? = null

    fun getConnection(url : String) : Retrofit?{
        if(retrofit == null){
            retrofit = Retrofit.Builder().baseUrl(url).addConverterFactory(
                GsonConverterFactory.create(
                GsonBuilder().create()
            )).build()
        }
        return retrofit
    }

}