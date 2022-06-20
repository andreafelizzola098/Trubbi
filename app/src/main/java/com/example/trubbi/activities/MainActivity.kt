package com.example.trubbi.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.compose.ui.text.toLowerCase
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.example.trubbi.data.EventResponse
import com.example.trubbi.databinding.ActivityMainBinding
import com.example.trubbi.entities.Event
import com.example.trubbi.interfaces.APIEventService
import com.example.trubbi.services.ServiceBuilder
import com.example.trubbi.viewmodel.EventViewModel
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var viewContainer: View
    private lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    lateinit var toogle: ActionBarDrawerToggle
    private lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding
    val eventViewModel: EventViewModel by viewModels()
    private lateinit var appBarConfiguration: AppBarConfiguration
    //start Search
    private val eventsMutable = mutableListOf<Event>()
    private lateinit var searchView: SearchView
    private lateinit var eventListAdapter: Adapter
    //end Search

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        //start Search
        searchView = findViewById(R.id.searchView)
        //end Search

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
        //SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            var query : String = query.toLowerCase()
            searchEventByTitle(query)
        }
        return true;
    }

    private fun searchEventByTitle(query: String) {
        val apiService: APIEventService = ServiceBuilder.buildService(APIEventService::class.java)
        val requestCall: Call<List<EventResponse>> = apiService.getSearchEvent(query)

        requestCall.enqueue(object: retrofit2.Callback<List<EventResponse>>{
            override fun onResponse(call: Call<List<EventResponse>>, response: Response<List<EventResponse>>){
                if(response.isSuccessful){
                    val eventResponse: List<EventResponse>? = response.body()
                    eventResponse?.let {
                        for(i in it.indices){
                            if (this != null) {
                                val event: EventResponse = it[i]
                                val eventCard = buildEvent(event)
                                eventsMutable.add(eventCard)
                                eventListAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<EventResponse>>, error: Throwable){
                Toast.makeText(applicationContext, "No existe el evento buscado", Toast.LENGTH_SHORT).show()
            }
        })
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(viewContainer.windowToken, 0)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true;
    }
}