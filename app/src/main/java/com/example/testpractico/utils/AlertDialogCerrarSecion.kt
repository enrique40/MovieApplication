package com.example.testpractico.utils

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.testpractico.R
import com.example.testpractico.databinding.AlertdialogcerrarseccionBinding
import com.example.testpractico.ui.activitys.LoginActivity
import com.example.testpractico.ui.activitys.MenuActivity
import com.example.testpractico.ui.splash.Splash
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class AlertDialogCerrarSecion {
        private lateinit var bindingCerrarsesionBinding: AlertdialogcerrarseccionBinding
        private lateinit var mGoogleSignInClient: GoogleSignInClient



    fun logoutApp(menuPrincipal: MenuActivity, provider: String) {
        bindingCerrarsesionBinding = AlertdialogcerrarseccionBinding.inflate(LayoutInflater.from(menuPrincipal))
        val alertDialog = AlertDialog.Builder(menuPrincipal).create()
        Log.e("TAG", "logoutApp: ${provider}" )
        bindingCerrarsesionBinding.idAceptar.setOnClickListener {

            if(provider.equals("GOOGLE")){
                Log.e("TAG", "IF logoutApp: GOOGLE" )
                val prefs = menuPrincipal.getSharedPreferences(menuPrincipal.getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
                prefs.clear()
                prefs.apply()

                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("854187888812-do4kmfe55qu05jvb68m69nn5b5ejc36n.apps.googleusercontent.com")
                    .requestEmail()
                    .build()
                // obtener el valor de gso dentro de GoogleSigninClient
                mGoogleSignInClient = GoogleSignIn.getClient(menuPrincipal,gso)
                mGoogleSignInClient.signOut()

                Intent(menuPrincipal, Splash::class.java).also {
                    menuPrincipal.startActivity(it)
                    menuPrincipal.finish()
                }

                alertDialog.dismiss()
            }
            else if (provider.equals("FACEBOOK")){
                Log.e("TAG", "ELSE if logoutApp: FACEBOOK" )
                val prefs = menuPrincipal.getSharedPreferences(menuPrincipal.getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
                prefs.clear()
                prefs.apply()
                FirebaseAuth.getInstance().signOut()
                LoginManager.getInstance().logOut()
                Intent(menuPrincipal, Splash::class.java).also {
                    menuPrincipal.startActivity(it)
                    menuPrincipal.finish()
                }
                alertDialog.dismiss()
            }
            else {
                Log.e("TAG", "ELSE logoutApp: Email" )
                val prefs = menuPrincipal.getSharedPreferences(menuPrincipal.getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
                prefs.clear()
                prefs.apply()
                Intent(menuPrincipal, Splash::class.java).also {
                    menuPrincipal.startActivity(it)
                    menuPrincipal.finish()
                }
                alertDialog.dismiss()
            }

        }

        bindingCerrarsesionBinding.idCancelar.setOnClickListener { alertDialog.dismiss() }
        alertDialog.setView(bindingCerrarsesionBinding.root)
        alertDialog.setCancelable(true)
        alertDialog.show()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)

    }


}