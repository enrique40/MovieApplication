package com.example.testpractico.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.testpractico.databinding.ActivitysplashBinding
import com.example.testpractico.ui.activitys.LoginActivity
import kotlinx.coroutines.*

class Splash : AppCompatActivity(){

    private lateinit var binding: ActivitysplashBinding
    private val SPLASH_SCREEN_PLAY: Long = 3000

    val job = Job()
    val errorHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("LOG", "ERROR: ${exception}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitysplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        splashScreen()
    }

    fun splashScreen(){
        val corutineScope = CoroutineScope(job + Dispatchers.IO)
        corutineScope.launch(errorHandler) {
            delay(SPLASH_SCREEN_PLAY)
            withContext(Dispatchers.Main){
                startActivity(Intent().setClass(this@Splash, LoginActivity::class.java))
                finish()
            }
        }
    }
}