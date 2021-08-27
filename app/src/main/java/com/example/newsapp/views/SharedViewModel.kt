package com.example.newsapp.views

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.core.data.NewsModel
import com.example.newsapp.core.repository.NewsDataSource
import com.example.newsapp.core.repository.NewsRepository
import com.example.newsapp.network.NewsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


enum class NewsApiStatus {LOADING, ERROR, DONE}

class SharedViewModel(application: Application) : AndroidViewModel(application), NewsDataSource {

    private val _search = MutableLiveData<String>()
    val search: LiveData<String>
        get() = _search

    private val _newsList = MutableLiveData<List<NewsModel>>()
    val newsList: LiveData<List<NewsModel>>
        get() = _newsList

    private val _selectedItem = MutableLiveData<NewsModel>()
    val selectedItem: LiveData<NewsModel>
        get() = _selectedItem

    private val _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus> get() = _status

    private var repo: NewsRepository

    init {
        _search.value = ""
        repo = NewsRepository(this)
    }

    fun fetchData() {
        repo.getNews()
    }

    override fun getNews() {
        _status.value = NewsApiStatus.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val getNews = NewsApi.retrofitService.getNews()
                withContext(Dispatchers.Main){
                    try {
                        _newsList.value = getNews.articles
                        _status.value = NewsApiStatus.DONE
                        println("Size : ${getNews.articles.size}")
                    } catch (t: Throwable) {
                        _status.value = NewsApiStatus.ERROR
                        _newsList.value = arrayListOf()
                        println("Error#$ : ${t.message}")
                    }
                }
            }catch (t: Throwable) {
                withContext(Dispatchers.Main){
                    _status.value = NewsApiStatus.ERROR
                    _newsList.value = arrayListOf()
                    println("Error#$ : ${t.message}")
                }
            }
        }
    }

    fun onNewsItemClicked(item: NewsModel) {
        _selectedItem.value = item
    }

}