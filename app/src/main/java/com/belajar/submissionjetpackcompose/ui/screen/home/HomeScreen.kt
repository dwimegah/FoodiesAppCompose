package com.belajar.submissionjetpackcompose.ui.screen.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.belajar.submissionjetpackcompose.R
import com.belajar.submissionjetpackcompose.ViewModelFactory
import com.belajar.submissionjetpackcompose.di.Injection
import com.belajar.submissionjetpackcompose.model.Food
import com.belajar.submissionjetpackcompose.ui.common.UiState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (String) -> Unit,
) {
    val query by viewModel.query
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.search(query)
            }
            is UiState.Success -> {
                HomeContent(
                    query = query,
                    onQueryChange = viewModel::search,
                    listFood = uiState.data,
                    navigateToDetail = navigateToDetail
                )
            }
            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    query: String,
    onQueryChange: (String) -> Unit,
    listFood: List<Food>,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        val listState = rememberLazyListState()

        LazyColumn (
            state = listState,
            modifier = Modifier.testTag("foodList")
        ) {
            item {
                SearchBar(
                    query = query,
                    onQueryChange = onQueryChange,
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                )
            }
            if (listFood.isNotEmpty()) {
                items(listFood, key = { it.id }) { f ->
                    FoodListItem(
                        id = f.id,
                        name = f.name,
                        photoUrl = f.photoUrl,
                        description = f.category,
                        navigateToDetail = navigateToDetail,
                        modifier = Modifier
                            .animateItemPlacement(tween(durationMillis = 200))
                            .clickable { navigateToDetail(f.id) }
                    )
                }
            }
            else {
                item {
                    Text(
                        text = stringResource(id = R.string.empty_data),
                        modifier = Modifier.testTag("emptyText")
                    )
                }
            }
        }
    }
}

@Composable
fun FoodListItem(
    id: String,
    name: String,
    photoUrl: String,
    description: String,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary,
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.clickable {
                navigateToDetail(id)
            }
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = "food photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = description,
                    modifier = Modifier.heightIn(0.dp, 100.dp)
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(50),
        colors = TextFieldDefaults.textFieldColors(
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(stringResource(id = R.string.search_food))
        },
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .shadow(56.dp)
    )
}