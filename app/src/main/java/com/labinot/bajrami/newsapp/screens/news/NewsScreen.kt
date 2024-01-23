package com.labinot.bajrami.newsapp.screens.news

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.labinot.bajrami.newsapp.R
import com.labinot.bajrami.newsapp.models.Article
import com.labinot.bajrami.newsapp.models.NewsObject
import com.labinot.bajrami.newsapp.nvgraph.Route
import com.labinot.bajrami.newsapp.presentation.components.ArticleCardShimmerEffect
import com.labinot.bajrami.newsapp.presentation.components.ImageSquare
import com.labinot.bajrami.newsapp.screens.SharedViewModel
import com.labinot.bajrami.newsapp.screens.bookmark.BookMarkViewModel
import com.labinot.bajrami.newsapp.utils.Constants.QUERY_NAME
import com.labinot.bajrami.newsapp.utils.DataStore
import com.labinot.bajrami.weathertest.data.DataOrException
import kotlinx.coroutines.flow.MutableStateFlow

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun NewsScreen(
    navController: NavController,
    padding: PaddingValues,
    sharedViewModel: SharedViewModel,
    bookMarkVM: BookMarkViewModel
) {

    val newsViewModel = hiltViewModel<NewsScreenViewModel>()
    val context = LocalContext.current
    val dataStore = DataStore(context)


    val newsData = produceState<DataOrException<NewsObject, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {

        value = newsViewModel.getNewsData(query = dataStore.getData(QUERY_NAME, "sport"))

    }.value


    if (newsData.loading == true) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {

            repeat(10) {

                ArticleCardShimmerEffect(modifier = Modifier.fillMaxWidth())
            }

        }


    } else if (newsData.e != null) {

        Text(
            text = "Error - ${newsData.e!!.message}!",
            color = Color.Red,
            textAlign = TextAlign.Center
        )

    } else if (newsData.data != null) {

        MainScaffold(
            newsD = newsData.data!!,
            navController = navController,
            shViewModel = sharedViewModel,
            bookmarkVM = bookMarkVM,
            paddin = padding
        )

    }


}

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScaffold(
    newsD: NewsObject,
    navController: NavController,
    shViewModel: SharedViewModel,
    paddin: PaddingValues,
    bookmarkVM: BookMarkViewModel,

    ) {


    val serverArticles = newsD.articles


    val articles = mutableListOf<Article>()

    articles.addAll(serverArticles)

    val flow = MutableStateFlow(PagingData.from(articles))


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddin),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Spacer(modifier = Modifier.height(5.dp))


        val lazyPagingItems = flow.collectAsLazyPagingItems()


        val titles by remember {
            derivedStateOf {

                if (lazyPagingItems.itemCount > 10) {

                    lazyPagingItems.itemSnapshotList.items
                        .slice(IntRange(start = 0, endInclusive = 9))
                        .joinToString(separator = "  \uD83d\uDFE5") { it.title }

                } else {

                    ""
                }


            }

        }


        Text(
            text = titles,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
                .basicMarquee(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            color = colorResource(id = R.color.placeholder)
        )



        Spacer(modifier = Modifier.height(5.dp))

        LazyColumn(
            modifier = Modifier.padding(2.dp),
            contentPadding = PaddingValues(1.dp)
        ) {

            items(items = newsD.articles) { item: Article ->
                ArticleCard(
                    articleData = item,
                    onClick = { article ->

                        shViewModel.addArticle(article)
                        navController.navigate(Route.DetailScreen.name)

                    },
                    isDetailScreen = false,
                    bookMarkViewModel = bookmarkVM
                )

            }

        }


    }
}


@Composable
fun ArticleCard(
    articleData: Article,
    onClick: (Article) -> Unit,
    isDetailScreen: Boolean,
    bookMarkViewModel: BookMarkViewModel,

    ) {

    val title_color = if (isSystemInDarkTheme()) colorResource(id = R.color.text_titlen)
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            ImageSquare(
                artData = articleData,
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp)
            )

            Column(
                modifier = Modifier.padding(horizontal = 3.dp),
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
                        color = title_color,
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Icon(
                        modifier = Modifier.size(11.dp),
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = "Icon Time",
                        tint = colorResource(id = R.color.body)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = articleData.publishedAt,
                        style = MaterialTheme.typography.labelMedium,
                        color = title_color,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (isDetailScreen) {

                        IconButton(onClick = {

                            bookMarkViewModel.deleteArticle(articleData)


                        }) {

                            Icon(
                                modifier = Modifier.size(25.dp),
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.delete_icon),
                                tint = Color.Red
                            )

                        }

                    }

                }


            }


        }

    }


}




