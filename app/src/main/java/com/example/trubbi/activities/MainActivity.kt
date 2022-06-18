package com.example.trubbi.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.trubbi.R
import com.example.trubbi.databinding.ActivityMainBinding
import com.example.trubbi.entities.Event
import com.example.trubbi.viewmodel.EventViewModel
import com.google.android.material.navigation.NavigationView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewContainer: View
    private lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    lateinit var toogle: ActionBarDrawerToggle
    private lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding
    val eventViewModel: EventViewModel by viewModels()
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var searchView: SearchView
    private val eventsMutable = mutableListOf<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewContainer = findViewById(R.id.viewContainer)
        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        //search
        //searchView = findViewById(R.id.searchView)
        //searchView.setOnQueryTextListener(this)

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

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://localhost:3060")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /*private fun searchEvent(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getSearchEvent("$query")
            val allEvents = call.body() //el body donde esta la respuesta
            runOnUiThread {
                if(call.isSuccessful){
                    //show recyclerView
                    val eventsInfo = allEvents?.events ?: emptyList()
                    eventsMutable.clear()
                    eventsMutable.addAll(eventsInfo)
                    adapter.notifyDataSetChanged()
                }else{
                    showError();
                }
                hideKeyBoard()
            }
        }
    }*/

    private fun hideKeyBoard() {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(viewContainer.windowToken, 0)
    }

    private fun showError() {
        TODO("Not yet implemented")
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}