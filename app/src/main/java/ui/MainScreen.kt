package com.pardevs.openguide.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

val NavyBlue = Color(0xFF001F3F)

@Composable
fun MainScreen() {
    var selectedItem by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Explore) }
    val items = listOf(BottomNavItem.Explore, BottomNavItem.Search, BottomNavItem.Flights)

    Scaffold(
        containerColor = NavyBlue,
        bottomBar = {
            NavigationBar(containerColor = NavyBlue) {
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
                is BottomNavItem.Flights -> FlightScreen()
            }
        }
    }
}
