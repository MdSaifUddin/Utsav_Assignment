package com.bb.movieapi.ui.upcoming

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bb.movieapi.Adapter.DiffAdapter
import com.bb.movieapi.Adapter.DiffUtilOffline
import com.bb.movieapi.Data.MovieItem
import com.bb.movieapi.Database.MovieData
import com.bb.movieapi.MainActivity
import com.bb.movieapi.MainViewModel
import com.bb.movieapi.R
import com.bb.movieapi.databinding.FragmentUpcomingBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class Upcoming : Fragment() {
    lateinit var binding:FragmentUpcomingBinding
    lateinit var vm:MainViewModel
    var list : ArrayList<MovieItem> = ArrayList()
    var adapter:DiffAdapter?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_upcoming,container,false)
        binding.lifecycleOwner=activity
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm=(requireActivity() as MainActivity).viewModel
        binding.recyclerView.layoutManager=LinearLayoutManager(requireContext())
        initializeLinstener()
    }

    fun initializeLinstener(){
        vm.upcoming.observe(viewLifecycleOwner,{
            binding.loading.visibility=View.GONE
            when(it){
                is com.bb.movieapi.API.Response.Success->{
                    list =it.data?.results as ArrayList<MovieItem>
                    adapter = DiffAdapter()
                    adapter?.submitList(list)
                    binding.recyclerView.setHasFixedSize(true)
                    binding.recyclerView.adapter=adapter
                }

                is com.bb.movieapi.API.Response.ErrMsg->{
                    GlobalScope.launch {
                        var list: ArrayList<MovieData> = vm.repository.getOfflineData(0)
                        withContext(Dispatchers.Main){
                            var adapter= DiffUtilOffline()
                            adapter.submitList(list)
                            binding.recyclerView.setHasFixedSize(true)
                            binding.recyclerView.adapter=adapter
//                            Toast.makeText(applicationContext, "Network Error1! ${list.size}", Toast.LENGTH_SHORT).show()
                        }

                    }
                }

                is com.bb.movieapi.API.Response.Loading->{
                    Toast.makeText(requireContext(), "Loading!", Toast.LENGTH_SHORT).show()
                }

            }

        })

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(p0==""){
                    adapter?.submitList(list)
                    adapter?.notifyDataSetChanged()
                }else{
                    var temp=ArrayList<MovieItem>()
                    list.forEach {
                        if(it.original_title.lowercase(Locale.getDefault()).contains(p0.toString().lowercase(Locale.getDefault())))
                            temp.add(it)
                    }
                    adapter?.submitList(temp)
                    adapter?.notifyDataSetChanged()
                }

                return false
            }
        })
    }
}