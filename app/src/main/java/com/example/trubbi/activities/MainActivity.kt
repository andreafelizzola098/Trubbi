package com.example.trubbi.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.trubbi.R
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar : Toolbar
    private lateinit var toolbar2 : Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toogle: ActionBarDrawerToggle //Boton hamburguesa
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration // SETTINGS HAMBURGUER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar) //Habilita la toolbar
        supportActionBar?.setDisplayShowTitleEnabled(true) // Elimina el Title, de los fragments, en la toolbar
        drawerLayout = findViewById(R.id.drawer_layout) // menu drawer
        navigationView = findViewById(R.id.nav_view) //navegación

        navController = Navigation.findNavController(this,R.id.nav_host_fragment)

        navigationView.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        //Creación del Botón Hamburguesa
        toogle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_nav_drawer,
            R.string.close_nav_drawer
        )

        //APPBAR SETTINGS
        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf()
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        //MANTENGO LA HAMBURGUER EN LA TOOLBAR
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if(destination.id != R.id.detailsFragment){
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                toolbar.setNavigationOnClickListener {
                    navController.navigate(R.id.action_detailsFragment_to_mainFragment)
                    val fm: FragmentManager = this.supportFragmentManager
                    for (i in 0 until fm.backStackEntryCount) {
                        fm.popBackStack()
                    }
                }
            }
        }
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

    }

    //Navegación por ID: en el menu drawer el id es = al nombre del fragmento que figura en el navigation
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

}