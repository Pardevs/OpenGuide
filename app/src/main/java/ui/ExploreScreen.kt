package com.pardevs.openguide.ui
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import com.example.openguide.R
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    var showDetail by remember { mutableStateOf(false) }

    if (showDetail) {
        MtatsmindaParkDetailScreen(onBack = { showDetail = false })
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
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(Color(0xFF003366), shape = RoundedCornerShape(12.dp))
                    .clickable { showDetail = true }
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Mtatsminda Park",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Tbilisi-0105",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun MtatsmindaParkDetailScreen(onBack: () -> Unit) {
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
            painter = painterResource(id = R.drawable.mtatsminda_park),
            contentDescription = "Mtatsminda Park",
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Mtatsminda Park",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "High atop Mount Mtatsminda, the park began its life in 1938 when Soviet authorities transformed the mountain plateau into a sprawling public recreation zone complete with gardens, walkways, and early attractions. The mountain itself had already been linked to the city center below since 1905 by a historic cable funicular railway. After the collapse of the Soviet Union in the 1990s, the area fell into deep neglect until a massive renovation in the late 2000s completely revitalized it. Today, it stands as a bustling amusement and landscape park featuring family rides, restaurants, and its famous 65-meter Giant Ferris Wheel perched right on the cliff edge.",
                color = Color.White,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Location: Mtatsminda Park, Upper Plateau, Funicular, Tbilisi 0105, Georgia",
                color = Color.LightGray,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val gmmIntentUri = Uri.parse("google.navigation:q=Mtatsminda+Park+Tbilisi")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")

                    if (mapIntent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(mapIntent)
                    } else {
                        val fallbackUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=Mtatsminda+Park+Tbilisi")
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