package com.remziakgoz.todolistwithcompose.presentation.views

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.remziakgoz.todolistwithcompose.R
import com.remziakgoz.todolistwithcompose.data.util.SharedPreferencesHelper
import com.remziakgoz.todolistwithcompose.domain.model.Item
import com.remziakgoz.todolistwithcompose.presentation.Screen

@Composable
fun ItemList(navController: NavController, viewModel: ItemViewModel) {

    val itemList = viewModel.itemList.value
    val isDarkTheme = LocalContext.current.resources.configuration.uiMode and
            android.content.res.Configuration.UI_MODE_NIGHT_MASK == android.content.res.Configuration.UI_MODE_NIGHT_YES

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val density = LocalDensity.current.density

    val topPadding = screenHeight * 0.1f
    val bottomPadding = screenHeight * 0.1f
    val adjustedTopPadding = topPadding / density
    val adjustedBottomPadding = bottomPadding / density

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FAB {
                navController.navigate(Screen.ListAddScreen.route)
            }
        },
        content = { innerPadding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .paint(painter = if (isDarkTheme) {
                        painterResource(R.drawable.todobackgroundfordark)
                    } else {
                        painterResource(R.drawable.todobackgroundforwhite)
                    }, contentScale = ContentScale.Crop)
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 25.dp + innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                        top = 9.dp,
                        end = 25.dp + innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                        bottom = 9.dp
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 78.dp + adjustedTopPadding, bottom = 10.dp + adjustedBottomPadding)
                ) {
                    items(itemList, key = { item -> item.id }) { item ->
                        val dismissState = rememberSwipeToDismissBoxState(
                            confirmValueChange = {
                                if (it == SwipeToDismissBoxValue.EndToStart) {
                                    viewModel.deleteItem(item)
                                    true
                                } else {
                                    false
                                }
                            }
                        )

                        LaunchedEffect(dismissState.currentValue) {
                            if (dismissState.currentValue == SwipeToDismissBoxValue.StartToEnd ||
                                dismissState.currentValue == SwipeToDismissBoxValue.EndToStart
                            ) {
                                dismissState.reset()
                            }
                        }

                        SwipeToDismissBox(
                            state = dismissState,
                            modifier = Modifier.fillMaxWidth(),
                            backgroundContent = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(
                                            start = 30.dp,
                                            top = 30.dp,
                                            end = 30.dp,
                                            bottom = 30.dp
                                        )
                                        .clip(shape = RoundedCornerShape(12.dp))
                                        .background(Color.Red),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    Text(
                                        text = "Delete",
                                        color = Color.White,
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.padding(end = 10.dp)
                                    )
                                }
                            }
                        ) {
                            ItemRow(item = item, context = LocalContext.current)
                        }
                    }
                }
            }
        })
}

@Composable
fun ItemRow(item: Item, context: Context) {
    val isChecked = remember { mutableStateOf(SharedPreferencesHelper.getCheckedState(context = context, itemId = item.id)) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, top = 10.dp, end = 25.dp, bottom = 10.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(Color.White)
            .heightIn(min = 70.dp)
            .shadow(5.dp, RoundedCornerShape(10.dp))
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                .wrapContentHeight(),
            text = item.toDoName ?: "",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
        ) {
            Checkbox(
                checked = isChecked.value,
                onCheckedChange = {
                    isChecked.value = it
                    SharedPreferencesHelper.saveCheckedState(context = context, itemId = item.id, isChecked = it)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Green,
                    uncheckedColor = Color.Gray,
                    checkmarkColor = Color.White
                ),
                modifier = Modifier.padding(end = 10.dp)
            )

            Text(
                text = "Completed",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}

@Composable
fun FAB(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = Color.Green,
        modifier = Modifier.padding(end = 12.dp)
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = Color.White)
    }
}