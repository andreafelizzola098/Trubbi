package com.example.trubbi.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toogle: ActionBarDrawerToggle
    private lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding
    val eventViewModel: EventViewModel by viewModels()
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        navigationView.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

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
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id != R.id.detailsFragment && destination.id != R.id.categoriesFragment) {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
            } else if (destination.id == R.id.detailsFragment) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                toolbar.setNavigationOnClickListener {
                    navController.popBackStack()
                }
            } else if (destination.id == R.id.categoriesFragment) {
                toolbar.setNavigationOnClickListener {
                    val navOptions =
                        NavOptions.Builder().setEnterAnim(R.anim.animation_test_right).build()
                    navController.navigate(R.id.mainFragment, null, navOptions)
                }
            }
        }

        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()
        navigationView.menu.findItem(R.id.logout).setOnMenuItemClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            this.finish()
            true
        }

    }

    //Navegación por ID: en el menu drawer el id es = al nombre del fragmento que figura en el navigation
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

}