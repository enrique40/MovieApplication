package com.example.testpractico.ui.activitys


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.util.PatternsCompat
import com.example.testpractico.R
import com.example.testpractico.databinding.ActivityLoginBinding
import com.example.testpractico.databinding.ActivitysplashBinding
import com.example.testpractico.utils.createDialog
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

private lateinit var mGoogleSignInClient: GoogleSignInClient
private lateinit var mGoogleSignInAcount: GoogleSignInAccount
private val callbackManager = CallbackManager.Factory.create()
val req_Code: Int=123
private var emailError = ""
private var passwordError = ""

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.email.onFocusChangeListener.apply {
            binding.email.isHintEnabled = false
        }
        binding.password.onFocusChangeListener.apply {
            binding.password.isHintEnabled = false
        }
        binding.Button.setOnClickListener {
            validatte()
        }


        binding.idGoogle.setOnClickListener {
            google()
        }

        binding.idFacebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))
            LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {


                override fun onError(error: FacebookException) {
                 
                }

                override fun onSuccess(result: LoginResult) {
                    result?.let {
                        showMenu("probando", "pronbando", ProvierType.FACEBOOK)
                    }
                }

                override fun onCancel() {

                }
            })
        }

        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val usuario = prefs.getString("usuario", null)
        val provider = prefs.getString("provider", null)

        if (email != null && usuario != null && provider != null){

            val bindingSplash: ActivitysplashBinding =
                ActivitysplashBinding.inflate(
                    LayoutInflater.from(
                        this
                    )
                )
            bindingSplash.splash.visibility = View.INVISIBLE


            showMenu(email, usuario, ProvierType.valueOf(provider))
        }

    }

    private fun validatte() {
        passwordError = ""
        emailError = ""
        if (!emailValidate()){
            createDialog(this,emailError,passwordError)
        }else{
            binding.auth.visibility = View.VISIBLE
            Intent(this, MenuActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }


    private fun emailValidate(): Boolean {
        return if (binding.emailEditext.text.toString().isEmpty()&& binding.passwordEditext.text.toString().isEmpty()) {
            emailError = "Campo correo está vacío"
            passwordError = "Campo contraseña está vacío"
            false
        }else if (binding.emailEditext.text.toString().isNotEmpty()&& binding.passwordEditext.text.toString().isEmpty()) {
            emailError = ""
            passwordError = "Campo contraseña está vacío"
            false
        } else if (binding.emailEditext.text.toString().isEmpty()&& binding.passwordEditext.text.toString().isNotEmpty()) {
            emailError = "Campo correo está vacío"
            passwordError = ""
            false
        }
        else  {
            true

        }
    }

    private fun google() {
        // Configure el inicio de sesión de Google dentro del método onCreate
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("854187888812-do4kmfe55qu05jvb68m69nn5b5ejc36n.apps.googleusercontent.com")
            .requestEmail()
            .build()
        // obtener el valor de gso dentro de GoogleSigninClient
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)


        signInGoogle()
    }
    // signInGoogle() la función privada de la diversión
    private fun signInGoogle() {
        mGoogleSignInClient.signOut()
        val signInIntent:Intent= mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, req_Code)
        Log.e("TAG", "signInGoogle: " )
    }
    // onActivityResult() función : este es donde nos proporcionan la tarea y los datos de la Cuenta de Google
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode== req_Code){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }
    private fun handleResult(completedTask: Task<GoogleSignInAccount>){
        try {
            val account: GoogleSignInAccount? =completedTask.getResult(ApiException::class.java)
            Log.e("TAG", "email: ${account?.email}" )
            Log.e("TAG", "familyName: ${account?.familyName}" )
            Log.e("TAG", "givenName: ${account?.givenName}" )
            Log.e("TAG", "id: ${account?.id}" )
            Log.e("TAG", "idToken: ${account?.idToken}" )

            showMenu(account?.email!!, account?.familyName!!, ProvierType.GOOGLE)

        } catch (e: ApiException){
            Log.e("TAG", "catch: ${e.message}" )
            Toast.makeText(this,e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showMenu(email: String, usuario: String, provider: ProvierType) {

        val menuIntent = Intent(this, MenuActivity::class.java).apply {
            putExtra("email", email)
            putExtra("usuario", usuario)
            putExtra("provider", provider.name)

        }

        startActivity(menuIntent)
    }

    override fun onStart() {
        super.onStart()
        val bindingSplash: ActivitysplashBinding =
            ActivitysplashBinding.inflate(
                LayoutInflater.from(
                    this
                )
            )
        bindingSplash.splash.visibility = View.VISIBLE
    }



}