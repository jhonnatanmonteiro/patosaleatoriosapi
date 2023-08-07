package com.natansin.randomduckapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.natansin.randomduckapi.databinding.ActivityMainBinding
import com.natansin.randomduckapi.network.ApiService
import com.natansin.randomduckapi.network.Retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var imageDuck: String? = null
    val randDuck = Retrofit.getInstance().create(ApiService::class.java)
    var isCrash = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        getTheDuck()
        binding.newBtn.setOnClickListener(){
            getTheDuck()
        }

        setContentView(binding.root)
    }

    private fun getTheDuck(){
        val duration = Toast.LENGTH_SHORT
        lifecycleScope.launch{
            try{
                var result = randDuck.getDuckRandom()
                if(result != null){
                    if(isCrash){
                        Toast.makeText(this@MainActivity, "reconectando.", duration).show()
                    }
                    imageDuck = result.body()?.url
                    println(imageDuck)
                    withContext(Dispatchers.Main){
                        Glide.with(this@MainActivity).load(imageDuck).into(binding.duckImg)
                        isCrash = false
                    }
                }
                else{
                    isCrash = true
                    Toast.makeText(this@MainActivity, "falha a encontrar um novo pato, verifique sua coneccao.", duration).show()
                }
            }catch(e : Exception){
                isCrash = true
                Toast.makeText(this@MainActivity, "falha a encontrar um novo pato, verifique sua coneccao..", duration).show()
            }
        }
    }
}