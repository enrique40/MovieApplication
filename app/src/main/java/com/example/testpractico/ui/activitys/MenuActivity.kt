package com.example.testpractico.ui.activitys


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*

import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.testpractico.R
import com.example.testpractico.databinding.ActivityMenuBinding
import com.example.testpractico.databinding.AlertdialogcerrarseccionBinding
import com.example.testpractico.utils.AlertDialogCerrarSecion
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.navigation.NavigationView

enum class ProvierType {
    GOOGLE,
    FACEBOOK
}

class MenuActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMenuBinding
    lateinit var ttoolbar: TextView
    lateinit var navView: NavigationView
    lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var alertDialog: AlertDialogCerrarSecion
    var provider = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        alertDialog = AlertDialogCerrarSecion()
        ttoolbar = findViewById(R.id.ttoolbar)
        ttoolbar.visibility = View.GONE


        title = ""
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        navView.itemIconTintList = null


        val bundle = intent.extras
        val email = bundle?.getString("email")
        val usuario = bundle?.getString("usuario")
        provider = bundle?.getString("provider").toString()

        val vistaH = binding.navView.getHeaderView(0)
        val name = vistaH.findViewById<TextView>(R.id.usuario)!!
        val emailG = vistaH.findViewById<TextView>(R.id.email)

        if (usuario != null && name != null){
            name.text = usuario
            emailG.text = email

        }else{
            name.text = "usuario prueba"
            emailG.text = "prueba@gmail.com"
        }



        val navController = findNavController(R.id.nav_host_fragment)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_perfil,
                R.id.nav_Misproductos,
                R.id.nav_configuraciones,
            ), drawerLayout
        )
        navView.setCheckedItem(R.id.nav_perfil)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_perfil -> {
                    toolbar("Perfil")
                }
                R.id.nav_Misproductos -> {
                    toolbar("Mis Productos")
                }
                R.id.nav_configuraciones -> {
                    toolbar("Configuraciones")
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

    }



    fun toolbar(text: String?){
        ttoolbar.visibility = View.VISIBLE
        ttoolbar.text = text
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_actionbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_cerrarsesion -> alertDialog.logoutApp(this, provider)
            else -> return false
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}