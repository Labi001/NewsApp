package com.labinot.bajrami.newsapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.labinot.bajrami.newsapp.R
import com.labinot.bajrami.newsapp.models.Article
import com.labinot.bajrami.newsapp.presentation.components.ImageSquare

@SuppressLint("SuspiciousIndentation")
@Composable
fun DetailScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    padd: PaddingValues
){

    val article = sharedViewModel.article


        if(article != null){

            ShowDetails(article = article,
                paddingV = padd)


        }





}

@Composable
fun ShowDetails(article: Article,
                paddingV: PaddingValues) {

    var title_color = if(isSystemInDarkTheme()) colorResource(id = R.color.text_titlen)
    else colorResource(id = R.color.text_title)
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(paddingV),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 2.dp,
                end = 2.dp,
                top = 2.dp
            )
        ){


            item {

                ImageSquare(artData = article,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(248.dp)
                        .padding(horizontal = 6.dp))

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    text = article.title,
                    color = title_color,
                    style = MaterialTheme.typography.displaySmall,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(7.dp),
                    text = article.content,
                    color = title_color,
                    style = MaterialTheme.typography.bodyMedium,
                )

            }
            

        }





    }

}
