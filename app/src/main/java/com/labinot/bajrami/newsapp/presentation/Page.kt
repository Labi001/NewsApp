package com.labinot.bajrami.newsapp.presentation

import androidx.annotation.DrawableRes
import com.labinot.bajrami.newsapp.R

data class Page(

    val title: String,
    val description: String,
    @DrawableRes val image: Int

)

val pages = listOf(

    Page(

        title = "Lorem is Simply dumy",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry",
        image = R.drawable.onboarding1

    ),

    Page(

        title = "Lorem is Simply dumy",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry",
        image = R.drawable.onboarding2

    ),

    Page(

        title = "Lorem is Simply dumy",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry",
        image = R.drawable.onboarding3

    )

)