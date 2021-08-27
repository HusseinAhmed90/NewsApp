package com.example.newsapp.network

import com.example.newsapp.core.data.NewsModel

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsModel>
)