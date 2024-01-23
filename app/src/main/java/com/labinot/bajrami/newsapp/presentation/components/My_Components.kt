package com.labinot.bajrami.newsapp.presentation.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.labinot.bajrami.newsapp.R
import com.labinot.bajrami.newsapp.models.Article


@Composable
fun ImageSquare(artData: Article,
                modifier: Modifier) {

    val context = LocalContext.current
    val imageLoader = remember(context) {
        ImageLoader.Builder(context)
            .crossfade(true)
            .build()
    }

    val imageUrl = artData.urlToImage



    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(

            defaultElevation = 8.dp
        ),
    ) {

        if (imageUrl.isNullOrEmpty()) {

            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.no_image),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(R.string.no_image)
            )

        } else {

            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberAsyncImagePainter(
                    model = imageUrl,
                    imageLoader = imageLoader
                ),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(R.string.image_url)
            )
        }


    }


}


fun Modifier.shimmerEffect(): Modifier {
    return composed {

        val transition = rememberInfiniteTransition(label = "")
        val alpha = transition.animateFloat(
            initialValue = 0.2f,
            targetValue = 0.9f,
            animationSpec = infiniteRepeatable(

                animation = tween(durationMillis = 1000),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        ).value

        this.background(
            color = colorResource(id = R.color.shimmer).copy(alpha = alpha)
        )
    }
}


@Composable
fun ArticleCardShimmerEffect(
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {

        Box(
            modifier = Modifier
                .size(100.dp)
                .padding(10.dp)
                .clip(
                    shape = RoundedCornerShape(16.dp)
                )
                .shimmerEffect(),
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 3.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center

        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(10.dp)
                    .shimmerEffect(),
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(15.dp)
                        .padding(5.dp)
                        .shimmerEffect(),
                )

            }


        }


    }


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun mSearchBar(
    onSearch: (String) -> Unit = {}
) {

    val searchQueryState = rememberSaveable {

        mutableStateOf("")
    }

    val valid = remember(searchQueryState.value) {

        searchQueryState.value.trim().isNotEmpty()
    }

    val keyBoardController = LocalSoftwareKeyboardController.current

    CommonTextField(
        valueState = searchQueryState,
        label = "Search",
        onAction = KeyboardActions {

            if (!valid) return@KeyboardActions

            onSearch(searchQueryState.value.trim())
            searchQueryState.value = ""
            keyBoardController?.hide()


        }
    )


}

@Composable
fun CommonTextField(
    valueState: MutableState<String>,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {

    val focusRequester = remember {

        FocusRequester()
    }

    LaunchedEffect(key1 = Unit){

        focusRequester.requestFocus()
    }

    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        placeholder = { Text(text = label,
            color = Color.DarkGray)  },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.DarkGray
            )
        },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = onAction,
        colors = TextFieldDefaults.colors(

            focusedTextColor = Color.Black,
            cursorColor = Color.Black,
            focusedContainerColor = Color.LightGray,
            unfocusedContainerColor = Color.LightGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent

        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .focusRequester(focusRequester)

    )


}





