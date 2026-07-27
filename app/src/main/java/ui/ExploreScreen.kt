package com.pardevs.openguide.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pardevs.openguide.R


data class Place(
    val name: String,
    val imageRes: Int,
    val description: String,
    val location: String,
    val mapQuery: String
)

// All places for Tbilisi - add more Place(...) entries here later
val tbilisiPlaces = listOf(
    Place(
        name = "Mtatsminda Park",
        imageRes = R.drawable.mtatsminda_park,
        description = "\uD83C\uDFA2 Amusement park\nCloses 12:00 AM\nRating:4.6",
        location = "Mtatsminda Park, Upper Plateau, Funicular, Tbilisi 0105, Georgia",
        mapQuery = "Mtatsminda+Park+Tbilisi"
    ),
    Place(
        name = "Narikala Fortress",
        imageRes = R.drawable.narikala_fortress,
        description = "Fortress \nOpen 24/7\nRating:4,7" ,
        location = "Narikala Fortress, Sololaki Hill, Old Town, Tbilisi 0105, Georgia",
        mapQuery = "Narikala+Fortress+Tbilisi"
    ),
    Place(
        name = "Holy Trinity Cathedral ",
        imageRes = R.drawable.mathedral,
        description ="The Holy Trinity Cathedral of Tbilisi, commonly known as Sameba, is a modern symbol of Georgia's spiritual rebirth. In 1989, as Georgia prepared for independence from the Soviet Union, plans were drawn up to build a massive cathedral to mark 2,000 years of Christianity. Construction was delayed by political unrest after the Soviet collapse, but work finally began on Elia Hill in 1995. Funded largely by private donations and completed in 2004, Sameba blends traditional Georgian church architecture with Byzantine elements. Soaring over 87 meters high with a golden dome, it is the largest church in Georgia and a major landmark overlooking the entire capital.",
        location = "Holy Trinity Cathedral of Tbilisi, Elia Hill, Avlabari, Tbilisi 0103, Georgia",
        mapQuery = "Holy+Trinity+Cathedral+Tbilisi",
    ),
)
@Composable
fun ExploreScreen(modifier: Modifier = Modifier) {
    val countryCities = mapOf(
        "Georgia" to listOf("Tbilisi", "Batumi", "Kutaisi"),
        "France" to listOf("Paris", "Nice", "Lyon"),
        "Japan" to listOf("Tokyo", "Osaka", "Kyoto"),
        "Italy" to listOf("Rome", "Milan", "Venice"),
        "Brazil" to listOf("Rio de Janeiro", "São Paulo", "Salvador"),
        "Egypt" to listOf("Cairo", "Luxor", "Alexandria")
    )

    var selectedCountry by remember { mutableStateOf<String?>(null) }
    var selectedCity by remember { mutableStateOf<String?>(null) }
    var countryExpanded by remember { mutableStateOf(false) }
    var cityExpanded by remember { mutableStateOf(false) }

    // Tracks which place's detail page is open (by name), or null if none
    var openDetail by remember { mutableStateOf<String?>(null) }

    val selectedPlace = tbilisiPlaces.find { it.name == openDetail }
    if (selectedPlace != null) {
        PlaceDetailScreen(
            name = selectedPlace.name,
            imageRes = selectedPlace.imageRes,
            description = selectedPlace.description,
            location = selectedPlace.location,
            mapQuery = selectedPlace.mapQuery,
            onBack = { openDetail = null }
        )
        return
    }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box {
                Button(
                    onClick = { countryExpanded = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF003366),
                        contentColor = Color.White
                    )
                ) {
                    Text(selectedCountry ?: "Countries")
                }

                DropdownMenu(
                    expanded = countryExpanded,
                    onDismissRequest = { countryExpanded = false },
                    modifier = Modifier.background(Color(0xFF003366))
                ) {
                    countryCities.keys.forEach { country ->
                        DropdownMenuItem(
                            text = { Text(country, color = Color.White) },
                            onClick = {
                                selectedCountry = country
                                selectedCity = null
                                countryExpanded = false
                            }
                        )
                    }
                }
            }

            if (selectedCountry != null) {
                Box {
                    Button(
                        onClick = { cityExpanded = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF003366),
                            contentColor = Color.White
                        )
                    ) {
                        Text(selectedCity ?: "Cities")
                    }

                    DropdownMenu(
                        expanded = cityExpanded,
                        onDismissRequest = { cityExpanded = false },
                        modifier = Modifier.background(Color(0xFF003366))
                    ) {
                        countryCities[selectedCountry]?.forEach { city ->
                            DropdownMenuItem(
                                text = { Text(city, color = Color.White) },
                                onClick = {
                                    selectedCity = city
                                    cityExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        Text(
            text = when {
                selectedCountry == null -> "Select a country to explore"
                selectedCity == null -> "Now pick a city in $selectedCountry"
                else -> "Showing results for: $selectedCity, $selectedCountry"
            },
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )

        if (selectedCountry == "Georgia" && selectedCity == "Tbilisi") {
            tbilisiPlaces.forEach { place ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .background(Color(0xFF003366), shape = RoundedCornerShape(12.dp))
                        .clickable { openDetail = place.name }
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = place.name,
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = place.location,
                            color = Color.LightGray,
                            fontSize = 14.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun PlaceDetailScreen(
    name: String,
    imageRes: Int,
    description: String,
    location: String,
    mapQuery: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Text("Back", color = Color.White)
        }

        Image(
            painter = painterResource(id = imageRes),
            contentDescription = name,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = name,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = description,
                color = Color.White,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Location: $location",
                color = Color.LightGray,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val gmmIntentUri = Uri.parse("google.navigation:q=$mapQuery")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")

                    if (mapIntent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(mapIntent)
                    } else {
                        val fallbackUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$mapQuery")
                        context.startActivity(Intent(Intent.ACTION_VIEW, fallbackUri))
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF003366),
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Navigate")
            }
        }
    }
}