package com.labinot.bajrami.newsapp.presentation.components.onboarding_c

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.labinot.bajrami.newsapp.nvgraph.Route
import com.labinot.bajrami.newsapp.presentation.components.PageIndicator
import com.labinot.bajrami.newsapp.presentation.components.common.NewsButton
import com.labinot.bajrami.newsapp.presentation.components.common.NewsTextButton
import com.labinot.bajrami.newsapp.presentation.pages
import com.labinot.bajrami.newsapp.utils.Constants
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardScreen(
    navController: NavHostController
){

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE)

    Column(modifier = Modifier.fillMaxSize()) {

        val pagerState = rememberPagerState(initialPage = 0) {

            pages.size
        }

        val buttonState = remember {

            derivedStateOf {

                when(pagerState.currentPage){

                    0 -> listOf("","Next")
                    1 -> listOf("Back","Next")
                    2 -> listOf("Back","Get Started")

                    else -> listOf("","")

                }
            }
        }
        
        HorizontalPager(state = pagerState) { index ->

            onBoardingPage(page = pages[index])
            
        }
        
        Spacer(modifier = Modifier.weight(1f))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            
            PageIndicator(
                modifier = Modifier.width(52.dp),
                pageSize = pages.size,
                selectedPage = pagerState.currentPage)

            Row(verticalAlignment = Alignment.CenterVertically) {

                val scope = rememberCoroutineScope()
                if(buttonState.value[0].isNotEmpty()){

                    NewsTextButton(text = buttonState.value[0],
                        onClickButton = {
                            scope.launch {

                                pagerState.animateScrollToPage(page = pagerState.currentPage - 1)

                            }
                        }
                    )

                }

                NewsButton(text = buttonState.value[1],
                    onClick = {

                        scope.launch {

                            if(pagerState.currentPage == 2){

                                sharedPreferences.edit().apply(){

                                    putBoolean(Constants.KEY_NAME,true)

                                }.apply()
                             navController.navigate(Route.NewsNavigationScreen.name)
                            }else{

                                pagerState.animateScrollToPage(
                                    page = pagerState.currentPage + 1
                                )

                            }

                        }

                    })

            }

        }

       Spacer(modifier = Modifier.weight(0.5f))

    }

}