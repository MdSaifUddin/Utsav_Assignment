package com.bb.movieapi.ui.Description

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bb.movieapi.API.Helper
import com.bb.movieapi.API.Repository
import com.bb.movieapi.API.Service
import com.bb.movieapi.API.poster_baseUrl
import com.bb.movieapi.Data.FilePath
import com.bb.movieapi.R
import com.bb.movieapi.databinding.ActivityDescriptionBinding
import com.ouattararomuald.slider.ImageSlider
import com.ouattararomuald.slider.SliderAdapter
import com.ouattararomuald.slider.loaders.glide.GlideImageLoaderFactory

class Description : AppCompatActivity() {
    lateinit var id: String
    lateinit var viewModel: DescViewModel
    lateinit var binding: ActivityDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        binding=DataBindingUtil.setContentView(this,R.layout.activity_description)
//        setContentView(R.layout.activity_description)

        id = intent.extras!!.getString("id").toString()
        var service = Helper.getInstance().create(Service::class.java)
        var repository = Repository(service, applicationContext)
        viewModel = ViewModelProvider(
            this,
            DescViewModelFactory(repository, id)
        ).get(DescViewModel::class.java)

        viewModel.imageList.observe(this, {

            var pathList: List<FilePath> = ArrayList()
            pathList = it.backdrops
            var imageList = ArrayList<String>()
            pathList.forEach {
                imageList.add("${poster_baseUrl}" + it.file_path)
            }
            if (imageList.size > 5) {
                imageList = imageList.slice(0..4).toList() as ArrayList<String>
            }
            binding.imageSlider.adapter = SliderAdapter(
                this@Description,
                GlideImageLoaderFactory(),
                imageUrls = imageList
            )
        })

        viewModel.desc.observe(this, {
            binding.loading.visibility = View.GONE
            binding.title.text = it.original_title
            binding.date.text = it.release_date
            binding.overview.text = it.overview
            binding.budget.text ="$"+it.budget.toString()
            binding.revenue.text ="$"+it.revenue.toString()
            binding.popularity.text = "%.2f".format(it.popularity)
            binding.voteCount.text = it.vote_count.toString()
            binding.voteAverage.text = "%.2f".format(it.vote_average)
            var genres: String = ""
            it.genres?.forEach {
                genres += it.name + ", "
            }
            if(genres!=""){
                genres = genres.substring(0, genres.length - 2)
                binding.genres.text = genres
            }


            var countries=""
            it.production_countries?.forEach {
                countries += it.name + ", "
            }
            if(countries!=""){
                countries=countries.substring(0, countries.length - 2)
                binding.country.text =countries
            }


            var languages=""
            it.spoken_languages?.forEach {
                languages += it.name + ", "
            }
            if(languages!=""){
                languages=languages.substring(0, languages.length - 2)
                binding.language.text =languages
            }

            var homepage=it.homepage
            binding.homepage.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(homepage)))
            }

        })

        viewModel.key.observe(this, {
            var keyList = it.results
            var key = keyList?.get(0)!!.key

            binding.watchBtn.setOnClickListener {
                var intent = Intent(this@Description, VideoPlayer::class.java)
                intent.putExtra("id", key)
                startActivity(intent)
                Log.i("MovieKey", key.toString())
            }
        })
    }


}