package com.pardevs.openguide.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Map
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val label: String, val icon: ImageVector) {
    object Explore : BottomNavItem("Explore", Icons.Filled.Explore)
    object Search : BottomNavItem("Search", Icons.Filled.Search)
    object Flights : BottomNavItem("Flights", Icons.Filled.Flight)
    object Map : BottomNavItem("Map", Icons.Filled.Map)
}