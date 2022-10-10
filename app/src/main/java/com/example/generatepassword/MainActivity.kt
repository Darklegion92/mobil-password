package com.example.generatepassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.generatepassword.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root )
        binding.btnSubmit.setOnClickListener(this)
    }

    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.108:8081/changepassword/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun generatePassword(){
        val apiService: APIService = getRetrofit().create(APIService::class.java)

        val result: Call<ResponseApi> = apiService.getPassword()

        result.enqueue(object:  Callback<ResponseApi>{
            override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {

                binding.txtPassword.text = response.body()?.result ?:"ERROR"
            }

            override fun onFailure(call: Call<ResponseApi>, t: Throwable) {
                showError(t?.message ?: "")
            }

        })



    }

    private fun showError(message:String){
        Toast.makeText(this, "Se ha presentado un error intente nuevamente"+message, Toast.LENGTH_LONG).show()
    }

    override fun onClick(p0: View?) {
        generatePassword();
    }
}