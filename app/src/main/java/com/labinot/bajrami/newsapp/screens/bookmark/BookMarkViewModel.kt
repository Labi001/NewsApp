package com.labinot.bajrami.newsapp.screens.bookmark

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.labinot.bajrami.newsapp.models.Article
import com.labinot.bajrami.newsapp.repository.NewsDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(private val repository: NewsDatabaseRepository)
    :ViewModel() {

    private val _articleList = MutableStateFlow<List<Article>> (emptyList())
    val articleList = _articleList.asStateFlow()

    private var _isEmpty by mutableStateOf(false)
    val isEmpty: Boolean
        get() = _isEmpty


    init {

        viewModelScope.launch(Dispatchers.IO) {

        repository.getArticle().distinctUntilChanged()
            .collect{ listofArticles->

                if(listofArticles.isNullOrEmpty()){

                    _isEmpty = true
                    Log.d("Tag","Empty BookMarks")

                }else{

                    _articleList.value = listofArticles
                    Log.d("TAG",":${articleList.value}")

                    _isEmpty = false
                }

            }

        }


    }


    fun insertArticle(article: Article) = viewModelScope.launch {

        repository.insertArticle(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {

        repository.deleteArticle(article)
    }



}