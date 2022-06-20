package com.example.trubbi.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trubbi.R
import com.example.trubbi.databinding.ActivityMainBinding
import com.example.trubbi.viewmodel.EventViewModel
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    lateinit var toogle: ActionBarDrawerToggle
    private lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding
    val eventViewModel: EventViewModel by viewModels()
    private lateinit var appBarConfiguration: AppBarConfiguration
    //start Search

    private lateinit var searchView: SearchView
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
        val apiService: APIEventService = ServiceBuilder.buildService(APIEventService::class.java)
        val requestCall: Call<EventResponse> = apiService.getEventByName(query)

        requestCall.enqueue(object: retrofit2.Callback<EventResponse>{
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>){
                if(response.isSuccessful){
                    val eventResponse: EventResponse? = response.body()
                    eventResponse?.let {
                        viewDetails.findViewById<TextView>(R.id.details_title).text = eventResponse.title
                        viewDetails.findViewById<TextView>(R.id.details_description).text = eventResponse.description
                        Picasso.get().load(eventResponse.photo).into(viewDetails.findViewById<ImageView>(R.id.details_image))
                        val startDate : String = java.time.format.DateTimeFormatter.ISO_INSTANT.format(java.time.Instant.ofEpochSecond(eventResponse.start_date))
                        val formattDate = dateFormatt(startDate)
                        viewDetails.findViewById<TextView>(R.id.details_date).text = formattDate
                    }
                }
            }

            override fun onFailure(call: Call<EventResponse>, error: Throwable){
                println("FALLE!!!!!!!!!!!!!!!!!!!!!")

            }
        })
        hideKeyBoard()
        return true;
    }

    private fun hideKeyBoard() {
        TODO("Not yet implemented")
    }

    fun dateFormatt(date:String): String{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssz")
        val parsedDate = formatter.parse(date)
        val formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:MM:SS")
        return formatter2.format(parsedDate)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true;
    }
}