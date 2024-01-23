package com.labinot.bajrami.newsapp.screens.news

import androidx.lifecycle.ViewModel
import com.labinot.bajrami.newsapp.models.NewsObject
import com.labinot.bajrami.newsapp.repository.NewsRepository
import com.labinot.bajrami.weathertest.data.DataOrException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsScreenViewModel @Inject constructor(private val repository: NewsRepository)
    :ViewModel() {



       suspend fun getNewsData(query:String):
       DataOrException<NewsObject,Boolean,Exception>{

          return repository.getNews(m_query = query)
       }

}