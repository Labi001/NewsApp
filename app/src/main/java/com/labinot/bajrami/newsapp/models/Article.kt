package com.labinot.bajrami.newsapp.models

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.Date
import javax.annotation.Nonnull


@RequiresApi(Build.VERSION_CODES.O)
@Entity(tableName = "news_tbl")
 class Article(

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "description")
    val description: String,

    @Nonnull
    @PrimaryKey
    @ColumnInfo(name = "publishedAt")
    val publishedAt: String,

    @ColumnInfo(name = "source")
    val source: Source,


    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "urlToImage")
    val urlToImage: String

)