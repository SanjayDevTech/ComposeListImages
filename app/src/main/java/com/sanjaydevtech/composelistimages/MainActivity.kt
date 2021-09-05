package com.sanjaydevtech.composelistimages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.sanjaydevtech.composelistimages.ui.theme.ComposeListImagesTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeListImagesTheme {
                ListImagesScreen(viewModel)
            }
        }
    }
}

val MyAppBar = @Composable {
    TopAppBar(
        title = {
            Text(stringResource(id = R.string.app_name))
        }
    )
}

@ExperimentalMaterialApi
@Composable
fun ListImagesScreen(viewModel: MainViewModel) {
    val response: Response? by viewModel.images.observeAsState()
    val status: Status by viewModel.status.observeAsState(initial = Status.Idle)
    var query: String by remember {
        mutableStateOf("")
    }

        LaunchedEffect(key1 = query) {
            println("Started $query")

            if (query.isBlank()) {
                viewModel.setLoading(false)
                return@LaunchedEffect
            }
            viewModel.setLoading(true)
            delay(2000)
            viewModel.fetch(query)
            println("Ended $query")
        }


    Scaffold(
        topBar = MyAppBar,
    ) {
        val photos = response?.photos
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TextField(modifier = Modifier.fillMaxWidth().padding(10.dp),value = query, onValueChange = { query = it }, singleLine = true, label = {Text("search")})
            when (status) {
                Status.Idle -> {
                    Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Type something")
                    }
                }
                Status.Loading -> {
                    Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Loading")
                    }
                }
                Status.Error -> {
                    Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Error")
                    }
                }
                else -> {
                    if (photos != null) {
                        LazyColumn {
                            items(
                                count = photos.size,
                                key = { i -> photos[i].id }
                            ) {
                                Card(
                                    modifier = Modifier.padding(10.dp),
                                    onClick = {

                                    }
                                ) {
                                    Column {
                                        Image(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(230.dp),
                                            contentScale = ContentScale.Crop,
                                            painter = rememberImagePainter(photos[it].src.medium),
                                            contentDescription = photos[it].photographer,
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Unknown error")
                        }
                    }
                }
            }
        }
    }
}
