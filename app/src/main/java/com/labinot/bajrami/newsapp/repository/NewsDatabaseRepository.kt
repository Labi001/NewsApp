package com.labinot.bajrami.newsapp.repository

import com.labinot.bajrami.newsapp.data.NewsDao
import com.labinot.bajrami.newsapp.models.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsDatabaseRepository @Inject constructor(private val newsDao: NewsDao) {

    fun getArticle(): Flow<List<Article>> = newsDao.getArticles()

    suspend fun insertArticle(article: Article) = newsDao.insertArticle(article)

    suspend fun deleteArticle(article: Article) = newsDao.deleteArticle(article)



}