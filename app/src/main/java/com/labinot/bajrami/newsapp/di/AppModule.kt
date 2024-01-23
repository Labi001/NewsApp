package com.labinot.bajrami.newsapp.di


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.labinot.bajrami.newsapp.data.NewsDao
import com.labinot.bajrami.newsapp.data.NewsDatabase
import com.labinot.bajrami.newsapp.data.NewsTypeConvertor
import com.labinot.bajrami.newsapp.network.NewsApi
import com.labinot.bajrami.newsapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideWeatherDao(newsDataBase: NewsDatabase):NewsDao
            =newsDataBase.newsDao()


    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): NewsDatabase
            = Room.databaseBuilder(
        context,
        NewsDatabase::class.java,
        "news_database"

    )
        .addTypeConverter(NewsTypeConvertor())
        .fallbackToDestructiveMigration()
        .build()


    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi{

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)

    }

}