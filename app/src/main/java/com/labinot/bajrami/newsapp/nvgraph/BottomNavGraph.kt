package com.labinot.bajrami.newsapp.nvgraph

import android.content.Context
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.labinot.bajrami.newsapp.presentation.components.onboarding_c.OnBoardScreen
import com.labinot.bajrami.newsapp.screens.bookmark.BookMarksScreen
import com.labinot.bajrami.newsapp.screens.DetailScreen
import com.labinot.bajrami.newsapp.screens.SearchScreen
import com.labinot.bajrami.newsapp.screens.SharedViewModel
import com.labinot.bajrami.newsapp.screens.bookmark.BookMarkViewModel
import com.labinot.bajrami.newsapp.screens.news.NewsScreen
import com.labinot.bajrami.newsapp.utils.Constants

@Composable
fun BottomNavGraph(navController: NavHostController,
                   mpadding: PaddingValues){

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE)
    val IsSecondTime = sharedPreferences.getBoolean(Constants.KEY_NAME,false)

    val sharedViewModel:SharedViewModel = viewModel()

    val OnBoardScreen = Route.OnboardinScreen.name
    val NewsScreen = Route.NewsNavigationScreen.name
    val bookmarkViewModel:BookMarkViewModel = hiltViewModel()


    NavHost(navController = navController,
        startDestination = if(IsSecondTime) NewsScreen else OnBoardScreen ){


        composable(Route.OnboardinScreen.name )
        {


            OnBoardScreen(
                navController = navController
            )

        }

        composable(Route.NewsNavigationScreen.name,
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )

            }){

            NewsScreen(navController = navController,
                        sharedViewModel = sharedViewModel,
                        bookMarkVM = bookmarkViewModel,
                        padding = mpadding)

        }

        composable(Route.SearchScreen.name){

            SearchScreen(navController = navController,
                        padd = mpadding)

        }

        composable(Route.BookMarksScreen.name,
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )

            }){


            BookMarksScreen(navController = navController,
                sharedViewModel = sharedViewModel,
                bokmmarkVM=bookmarkViewModel,
                padd = mpadding)

        }

        composable(Route.DetailScreen.name,
            enterTransition = {
                slideInHorizontally (
                    initialOffsetX = {fullWidth -> -fullWidth},
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )

            }
         ){

                DetailScreen(navController = navController,
                    sharedViewModel = sharedViewModel,
                    padd = mpadding
                   )


        }

    }

}