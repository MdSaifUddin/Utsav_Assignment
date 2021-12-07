package com.bb.movieapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bb.movieapi.API.Helper
import com.bb.movieapi.API.Repository
import com.bb.movieapi.API.Service
import com.bb.movieapi.Adapter.DiffAdapter
import com.bb.movieapi.Adapter.DiffUtilOffline
import com.bb.movieapi.Data.MainData
import com.bb.movieapi.Data.MovieItem
import com.bb.movieapi.Database.MovieDB
import com.bb.movieapi.Database.MovieData
import com.bb.movieapi.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var dataBinding:ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        var service=Helper.getInstance().create(Service::class.java)
        var repository=Repository(service,applicationContext)
        viewModel=ViewModelProvider(this,MainViewModelFactory(repository)).get(MainViewModel::class.java)
        dataBinding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        val navView: BottomNavigationView = dataBinding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }
}