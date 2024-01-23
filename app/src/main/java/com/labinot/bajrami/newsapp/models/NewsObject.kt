package com.labinot.bajrami.newsapp.models

data class NewsObject(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)