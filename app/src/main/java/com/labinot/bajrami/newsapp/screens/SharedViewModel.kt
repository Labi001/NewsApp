package com.labinot.bajrami.newsapp.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.labinot.bajrami.newsapp.models.Article

class SharedViewModel: ViewModel() {

    var article by mutableStateOf<Article?>(null)
        private set

    fun addArticle(newArticle:Article){

        article = newArticle
    }

}