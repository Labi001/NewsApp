package com.labinot.bajrami.newsapp.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.labinot.bajrami.newsapp.R
import com.labinot.bajrami.newsapp.models.Article
import com.labinot.bajrami.newsapp.screens.bookmark.BookMarkViewModel

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsAppBar(
    title:String = "",
    icon:ImageVector?,
    onBackIconClicked:() -> Unit ={}
){
    val isSystemInDarkMode = isSystemInDarkTheme()


        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {

                    Image(modifier = Modifier.size(50.dp),
                        painter = painterResource(id = R.drawable.news_icon),
                        contentDescription = stringResource(R.string.app_icon)
                    )

                    Text(text = title,
                        color = MaterialTheme.colorScheme.primary,
                        fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                    )

                }

            },
            navigationIcon = {

                if(icon != null){

                    IconButton(onClick = { onBackIconClicked.invoke() }) {

                        Icon(imageVector = icon,
                            contentDescription = stringResource(R.string.back_icon),
                            tint =  if(isSystemInDarkMode) Color.White else Color.DarkGray
                        )

                    }

                }

            },
            colors = TopAppBarDefaults.topAppBarColors(

                containerColor =if(isSystemInDarkMode) Color(0xFF131C26) else Color(0xFFD6E4F4)
            ))






}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(
    title: String = "",
    onBackIconClicked: () -> Unit = {},
    onBookMarkClicked: (Article, MutableState<Boolean>) -> Unit,
    onShareClicked: () -> Unit = {},
    onBrowsableClicked: () -> Unit = {},
    marticle: Article?
){
    val isSystemInDarkMode = isSystemInDarkTheme()
    val bookMarkViewModel: BookMarkViewModel = viewModel()


    TopAppBar(modifier = Modifier.fillMaxWidth()
        ,title = {

        Text(text = title,
        color = MaterialTheme.colorScheme.primary,
        fontStyle = MaterialTheme.typography.labelSmall.fontStyle)

    },
        navigationIcon = {

            IconButton(onClick = { onBackIconClicked.invoke() }) {

                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back_icon),
                    tint = MaterialTheme.colorScheme.primary)

            }

        },
        actions = {

             val alreadyBookMark = bookMarkViewModel.articleList
                 .collectAsState().value.filter { item ->

                     (item.title == marticle?.title)
                 }

            val isAdded = remember {

                mutableStateOf(false)
            }

            IconButton(onClick = {

                if(alreadyBookMark.isNotEmpty()){
                    isAdded.value = false
                }else {

                    isAdded.value = true
                }

                onBookMarkClicked.invoke(marticle!!,isAdded)
            }) {

                Icon(modifier = Modifier.size(24.dp),
                    painter = if(alreadyBookMark.isEmpty()) painterResource(id = R.drawable.ic_outlinetbookmark)
                    else painterResource(id = R.drawable.ic_filledbookmark),
                    contentDescription = stringResource(R.string.bookmark_icon),
                    tint = MaterialTheme.colorScheme.primary
                )

            }

            IconButton(onClick = { onShareClicked.invoke() }) {

                Icon(modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.Share,
                    contentDescription = stringResource(R.string.share_icon),
                    tint = MaterialTheme.colorScheme.primary
                )

            }

            IconButton(onClick = { onBrowsableClicked.invoke() }) {

                Icon(modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.chrome),
                    contentDescription = stringResource(R.string.browsable_icon),
                    tint = MaterialTheme.colorScheme.primary
                )

            }


        },
        colors = TopAppBarDefaults.topAppBarColors(

            containerColor = if(isSystemInDarkMode) Color(0xFF131C26) else Color(0xFFD6E4F4)
        ))


}