package com.labinot.bajrami.newsapp.repository

import com.labinot.bajrami.newsapp.models.NewsObject
import com.labinot.bajrami.newsapp.network.NewsApi
import com.labinot.bajrami.weathertest.data.DataOrException
import javax.inject.Inject

class NewsRepository @Inject constructor(private val api: NewsApi) {

    suspend fun getNews(m_query:String): DataOrException<NewsObject,Boolean,Exception>{

        val response = try {

            api.getNews(query = m_query)
        }catch (e:Exception){

            return DataOrException(e=e)
        }
         return DataOrException(data = response)
    }


}