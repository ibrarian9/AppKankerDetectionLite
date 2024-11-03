package com.dicoding.asclepius.remote.retrofit

import com.google.gson.annotations.SerializedName

data class News (

	@field:SerializedName("totalResults")
	val totalResults: Int,

	@field:SerializedName("articles")
	val articles: List<ArticlesItem>,

	@field:SerializedName("status")
	val status: String
)

data class ArticlesItem (

	@field:SerializedName("urlToImage")
	val urlToImage: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("title")
	val title: String,
)
