/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.codelabs.paging.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.android.codelabs.paging.Injection
import com.example.android.codelabs.paging.model.Repo
import com.example.android.codelabs.paging.model.RepoSearchResult
import com.example.android.codelabs.paging.ui.staggered.StaggeredVerticalScreen

class SearchRepositoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get the view model
        val viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(owner = this))[SearchRepositoriesViewModel::class.java]

        setContent {
            val itemList = remember { mutableStateListOf<Repo>() }
            var lastListSize by remember { mutableStateOf(0) }

            LaunchedEffect(Unit) {
                viewModel.fetchContent()
                    .collect { result ->
                        when (result) {
                            is RepoSearchResult.Success -> {
                                Log.d("GithubRepository", "result.data ${result.data.size}")
                                itemList.clear()
                                itemList.addAll(result.data)
                            }
                            is RepoSearchResult.Error -> {
                                Toast.makeText(
                                    this@SearchRepositoriesActivity,
                                    "\uD83D\uDE28 Wooops $result.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
            }

            val scrollState = rememberScrollState()
            val endReached = remember {
                derivedStateOf {
                    (scrollState.value == scrollState.maxValue) && (lastListSize != itemList.size) && (scrollState.isScrollInProgress)
                }
            }

            Column(Modifier.verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier.size(100.dp)) {
                    Text("Other Top composable")
                }
                StaggeredVerticalScreen(
                    itemList = itemList,
                    numColumns = 2,
                    layoutModifier = Modifier.padding(
                        start = 12.dp,
                        bottom = 12.dp
                    ),
                    singleItemModifier = Modifier.padding(
                        end = 12.dp,
                        top = 12.dp
                    )
                ) { singleGridItem, singleItemModifier, index ->
                    SingleArticleItem(singleGridItem  , index)
                }

                if (endReached.value) {
                    lastListSize = itemList.size
                    Log.d("SearchRepositoriesActivity", "End of scroll lazyItems.itemCount=${itemList.size}")
                    viewModel.accept(UiAction.FetchMore)
                }
            }
        }
    }

    @Composable
    fun SingleArticleItem(
        uiModel: Repo,
        index: Int,
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                .background(Color.Green.copy(alpha = 0.3f))
                .background(Color.Red.copy(alpha = 0.3f)),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "$index : ${uiModel.description} ",
                style = MaterialTheme.typography.h6,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
