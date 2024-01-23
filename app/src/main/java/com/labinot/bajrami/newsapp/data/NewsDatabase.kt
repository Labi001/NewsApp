package com.labinot.bajrami.newsapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.labinot.bajrami.newsapp.models.Article

@RequiresApi(Build.VERSION_CODES.O)
@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(NewsTypeConvertor::class)
abstract class NewsDatabase: RoomDatabase(){

     abstract fun newsDao():NewsDao
}
