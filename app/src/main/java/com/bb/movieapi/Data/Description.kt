package com.bb.movieapi.Data

data class Description(
    val original_title: String,
    val overview: String,
    val popularity: Float,
    val release_date: String,
    val genres: List<Genre>,
    val vote_count:Int,
    val vote_average:Float,
    val budget:Int,
    val revenue:Int,
    val original_language:String
)