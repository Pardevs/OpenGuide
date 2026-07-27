package com.example.openguide

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.maplibre.android.MapLibre
import org.maplibre.android.camera.CameraPosition
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.maps.MapView

@Composable
fun MapScreen() {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            MapLibre.getInstance(context)

            MapView(context).apply {
                onCreate(null)
                getMapAsync { map ->
                    map.setStyle("https://tiles.openfreemap.org/styles/liberty")
                    map.cameraPosition = CameraPosition.Builder()
                        .target(LatLng(14.5995, 120.9842))
                        .zoom(12.0)
                        .build()
                }
            }
        },
        update = { mapView ->
            mapView.onResume()
        }
    )
}