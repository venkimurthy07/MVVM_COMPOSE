package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.domain.util.Result
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(mainViewModel = mainViewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(mainViewModel: MainViewModel, modifier: Modifier = Modifier) {
    val allBreedsResult by mainViewModel.allBreeds.observeAsState()

    // We now pass a query to the fetch function
    LaunchedEffect(Unit) {
        mainViewModel.fetchAllBreeds()
    }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (val result = allBreedsResult) {
            is Result.Loading -> {
                CircularProgressIndicator()
            }
            is Result.Success -> {

                LazyColumn {
                    items(result.data.message.entries.toList()) { entry ->

                        var expanded by rememberSaveable { mutableStateOf(false) }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { expanded = !expanded },
                            elevation = CardDefaults.cardElevation(6.dp)
                        ) {

                            Column(modifier = Modifier.padding(16.dp)) {

                                // Header Row
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = entry.key,
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    Icon(
                                        imageVector = if (expanded)
                                            Icons.Default.KeyboardArrowUp
                                        else
                                            Icons.Default.KeyboardArrowDown,
                                        contentDescription = null
                                    )
                                }

                                // Expandable Section
                                AnimatedVisibility(visible = expanded) {

                                    if (entry.value.isNotEmpty()) {
                                        Column(modifier = Modifier.padding(top = 8.dp)) {

                                            entry.value.forEach { sub ->
                                                Text(
                                                    text = "• $sub",
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    modifier = Modifier.padding(vertical = 2.dp).clickable{

                                                    }
                                                )
                                            }
                                        }
                                    } else {
                                        Text(
                                            text = "No sub-breeds",
                                            modifier = Modifier.padding(top = 8.dp),
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            is Result.Error -> {
                Log.e("MainActivity", "API Error: ${result.exception.message}")
                Text(
                    color = Color.Red,
                    text = "API Error: ${result.exception.message}"
                )
            }
            null -> {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MyApplicationTheme {
        Text(text = "Preview")
    }
}
