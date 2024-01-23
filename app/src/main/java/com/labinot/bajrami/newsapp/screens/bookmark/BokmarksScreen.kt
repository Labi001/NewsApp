package com.labinot.bajrami.newsapp.screens.bookmark

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.labinot.bajrami.newsapp.R
import com.labinot.bajrami.newsapp.models.Article
import com.labinot.bajrami.newsapp.nvgraph.Route
import com.labinot.bajrami.newsapp.presentation.components.ImageSquare
import com.labinot.bajrami.newsapp.screens.SharedViewModel

@Composable
fun BookMarksScreen(
    navController: NavController,
    padd: PaddingValues,
    bokmmarkVM: BookMarkViewModel,
    sharedViewModel: SharedViewModel
){

    val articles = bokmmarkVM.articleList.collectAsState().value
    val isEmpty = bokmmarkVM.isEmpty

    var startAnimation by remember {
        mutableStateOf(false)
    }

    val alphaAnimation by animateFloatAsState(
        targetValue = if(startAnimation) 0.3f else 0f,
        animationSpec = tween(durationMillis = 1500),
        label = ""
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(padd)) {

        Spacer(modifier = Modifier.height(5.dp))


        Spacer(modifier = Modifier.height(10.dp))

        if(isEmpty){

            LaunchedEffect(key1 = true){
                startAnimation = true
            }

            Column(modifier = Modifier.wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {

                Text(text = "There is no article saved!",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    color =  colorResource(id = R.color.body))

                Image(modifier = Modifier.size(500.dp),
                    painter = painterResource(id = R.drawable.empty_icon),
                    contentDescription = stringResource(R.string.empty_box),
                    alpha = alphaAnimation
                )


            }

        }else{

            LazyColumn{

                items(items = articles){ article->

                    ArticleCardDetail(
                        articleData = article,
                        onClick = {artic->

                            sharedViewModel.addArticle(artic)
                            navController.navigate(Route.DetailScreen.name)

                        },

                        bookMarkViewModel = bokmmarkVM
                    )

                }

            }
        }




    }

}


@Composable
fun ArticleCardDetail(
    articleData: Article,
    onClick: (Article) -> Unit,
    bookMarkViewModel: BookMarkViewModel,

    ) {

    val title_color = if(isSystemInDarkTheme()) colorResource(id = R.color.text_titlen)
    else colorResource(id = R.color.text_title)
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick.invoke(articleData) }
        .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(

            defaultElevation = 8.dp

        )
    ) {

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            ImageSquare(artData = articleData,
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 3.dp)
                    .weight(0.8f),
                verticalArrangement = Arrangement.SpaceAround
            ) {

                Text(
                    text = articleData.title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = title_color,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {


                    Text(
                        text = articleData.source.name,
                        style = MaterialTheme.typography.labelMedium,
                        color = title_color
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Icon(
                        modifier = Modifier.size(11.dp),
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = "Icon Time",
                        tint = if(isSystemInDarkTheme()) colorResource(id = R.color.bodyn)
                        else colorResource(id = R.color.body)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = articleData.publishedAt,
                        style = MaterialTheme.typography.labelMedium,
                        color =title_color,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                }


            }


            IconButton(modifier = Modifier.weight(0.2f),
                onClick = {

                    bookMarkViewModel.deleteArticle(articleData)


                }) {

                Icon(modifier = Modifier.size(25.dp),
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete_icon),
                    tint = Color.Red)

            }




        }

    }




}