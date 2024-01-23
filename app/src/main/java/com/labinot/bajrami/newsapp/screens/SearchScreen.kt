package com.labinot.bajrami.newsapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.labinot.bajrami.newsapp.nvgraph.Route
import com.labinot.bajrami.newsapp.presentation.components.mSearchBar
import com.labinot.bajrami.newsapp.utils.Constants.QUERY_NAME
import com.labinot.bajrami.newsapp.utils.DataStore

@Composable
fun SearchScreen(navController: NavController, padd: PaddingValues){

    val context = LocalContext.current
    val dataStore = DataStore(context)
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(padd),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(5.dp))

                        mSearchBar() { mQuery ->

                    try {
                        dataStore.saveData(QUERY_NAME, mQuery)
                        navController.navigate(Route.NewsNavigationScreen.name)
                    } catch (e: Exception) {

                        e.printStackTrace()
                    }


                }

    }


}