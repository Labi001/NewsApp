package com.labinot.bajrami.newsapp.nvgraph


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.labinot.bajrami.newsapp.R
import com.labinot.bajrami.newsapp.presentation.components.BottomNavigationItem
import com.labinot.bajrami.newsapp.presentation.components.DetailTopBar
import com.labinot.bajrami.newsapp.presentation.components.NewsAppBar
import com.labinot.bajrami.newsapp.presentation.components.NewsBottomNavigation
import com.labinot.bajrami.newsapp.screens.SharedViewModel
import com.labinot.bajrami.newsapp.screens.bookmark.BookMarkViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val backstackState = navController.currentBackStackEntryAsState().value

    var selectedItem by rememberSaveable {

       mutableIntStateOf(0)
    }

    selectedItem = remember(key1 = backstackState) {

        when (backstackState?.destination?.route) {

            Route.DetailScreen.name -> 0
            Route.SearchScreen.name -> 1
            Route.BookMarksScreen.name -> 2
            else -> 0

        }

    }

    val bottomNavigationItems = remember {

        listOf(

            BottomNavigationItem(
                icon = R.drawable.ic_home,
                text = "HOME"
            ),

            BottomNavigationItem(
                icon = R.drawable.ic_search,
                text = "SEARCH"
            ),

            BottomNavigationItem(
                icon = R.drawable.ic_bookmark,
                text = "BOOKMARK"
            )

        )

    }

    val isBottomBarVisible = remember(key1 = backstackState){


        backstackState?.destination?.route == Route.NewsNavigationScreen.name ||
        backstackState?.destination?.route == Route.SearchScreen.name ||
        backstackState?.destination?.route == Route.BookMarksScreen.name

    }

    val DetailNavBarVisible = remember(key1 = backstackState){


        backstackState?.destination?.route == Route.DetailScreen.name


    }

    val BookMarkScreenVisible = remember(key1 = backstackState){


        backstackState?.destination?.route == Route.BookMarksScreen.name


    }

    val context = LocalContext.current
    val sharedViewModel: SharedViewModel = viewModel()
    val bookMarkViewModel: BookMarkViewModel = viewModel()

    val article = sharedViewModel.article

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {

            if (isBottomBarVisible) {

                NewsAppBar(
                    title = if(BookMarkScreenVisible) "Bookmark Screen"
                    else stringResource(id = R.string.app_name),
                    icon = null
                )
            } else if (DetailNavBarVisible) {

                DetailTopBar(
                    title = "Detail Screen",
                    marticle = article,
                    onBackIconClicked = {

                        navController.popBackStack()
                    },

                   onBrowsableClicked = {

                        Intent(Intent.ACTION_VIEW).also {

                            if(article != null){

                                it.data = Uri.parse(article.url)

                                if(it.resolveActivity(context.packageManager) != null){

                                    context.startActivity(it)
                                }
                            }


                        }

                    },
                    onShareClicked = {

                        Intent(Intent.ACTION_SEND).also {

                            if(article !=null){
                                it.putExtra(Intent.EXTRA_TEXT,article.url)
                                it.type = "text/plain"

                                if(it.resolveActivity(context.packageManager) != null){

                                    context.startActivity(it)
                                }
                            }


                        }

                    },

                    onBookMarkClicked = { article, isAdded ->

                        if(isAdded.value){

                            bookMarkViewModel.insertArticle(article)

                        }else{

                            bookMarkViewModel.deleteArticle(article)

                        }

                        showToast(context,isAdded)
                    }
                )

            }


        },
        bottomBar = {

            if (isBottomBarVisible) {

                NewsBottomNavigation(items = bottomNavigationItems,
                    selected = selectedItem,
                    onItemClick = { index ->

                        when (index) {

                            0 -> NavigatetoTap(
                                navController = navController,
                                route = Route.NewsNavigationScreen.name
                            )

                            1 -> NavigatetoTap(
                                navController = navController,
                                route = Route.SearchScreen.name
                            )

                            2 -> NavigatetoTap(
                                navController = navController,
                                route = Route.BookMarksScreen.name
                            )

                        }

                    })
            }


        },


        ){ myPadding->

         BottomNavGraph(navController = navController,
                       mpadding = myPadding)
    }


}



fun NavigatetoTap(navController: NavController, route:String) {

    navController.navigate(route){

        navController.graph.startDestinationRoute?.let { homeScreen ->

            popUpTo(homeScreen){

                saveState = true
            }
            restoreState = true
            launchSingleTop = true

        }

    }

}

fun showToast(context: Context
              , added: MutableState<Boolean>
) {

    if(added.value){

        Toast.makeText(context,"Saved to Bookmark!",
            Toast.LENGTH_SHORT).show()

    }else{

        Toast.makeText(context,"Deleted from Bookmark!",
            Toast.LENGTH_SHORT).show()

    }

}

