package com.example.openguide
import androidx.compose.foundation.layout.Box
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainScreen()
            }
        }
    }
}

val NavyBlue = Color(0xFF001F3F)

sealed class BottomNavItem(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Explore : BottomNavItem("Explore", Icons.Filled.Explore)
    object Search : BottomNavItem("Search", Icons.Filled.Search)
}

@Composable
fun MainScreen() {
    var selectedItem by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Explore) }
    val items = listOf(BottomNavItem.Explore, BottomNavItem.Search)

    Scaffold(
        containerColor = NavyBlue,
        bottomBar = {
            NavigationBar(
                containerColor = NavyBlue
            ) {
                items.forEach { item ->
                    NavigationBarItem(
                        selected = selectedItem == item,
                        onClick = { selectedItem = item },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.White,
                            unselectedIconColor = Color.LightGray,
                            unselectedTextColor = Color.LightGray,
                            indicatorColor = Color(0xFF003366)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(NavyBlue)
        ) {
            when (selectedItem) {
                is BottomNavItem.Explore -> ExploreScreen()
                is BottomNavItem.Search -> SearchScreen()
            }
        }
    }
}

@Composable
fun ExploreScreen(modifier: Modifier = Modifier) {
    Text("Explore Screen", color = Color.White, modifier = modifier.padding(16.dp))
}

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    Text("Search Screen", color = Color.White, modifier = modifier.padding(16.dp))
}

