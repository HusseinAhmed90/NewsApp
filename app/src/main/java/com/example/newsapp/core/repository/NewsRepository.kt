package com.example.newsapp.core.repository

class NewsRepository (private val dataSource: NewsDataSource) {
    fun getNews() = dataSource.getNews()
}