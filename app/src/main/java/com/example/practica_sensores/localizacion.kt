package com.example.practica_sensores

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class Localizacion : AppCompatActivity() {

    // Cliente de ubicación
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_localizacion)

        // Inicializar cliente de ubicación
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // Referencias a vistas
        val btnObtenerUbicacion = findViewById<Button>(R.id.btn_obtener_ubicacion)
        val tvMensaje = findViewById<TextView>(R.id.tv_mensaje)

        // Configurar botón para obtener ubicación
        btnObtenerUbicacion.setOnClickListener {
            obtenerUbicacion(tvMensaje)
        }
    }

    private fun obtenerUbicacion(tvMensaje: TextView) {
        // Verificar permisos
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Solicitar permisos si no se han concedido
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }

        // Obtener última ubicación conocida
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val direccion = obtenerDireccion(location.latitude, location.longitude)
                tvMensaje.text =
                    "Latitud: ${location.latitude}\nLongitud: ${location.longitude}\nDirección: $direccion"
            } else {
                tvMensaje.text = "No se pudo obtener la ubicación."
            }
        }
    }

    private fun obtenerDireccion(lat: Double, lon: Double): String {
        val geocoder = Geocoder(this)
        // Manejar la excepción de geocodificación
        return try {
            val direcciones = geocoder.getFromLocation(lat, lon, 1)
            // Comprobar si direcciones no es null y tiene al menos una dirección
            direcciones?.firstOrNull()?.getAddressLine(0) ?: "Dirección no disponible"
        } catch (e: Exception) {
            "Error al obtener la dirección."
        }
    }

    // Este método se llama cuando el usuario responde a la solicitud de permisos
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, obtenemos la ubicación
                obtenerUbicacion(findViewById(R.id.tv_mensaje))
            } else {
                // Permiso denegado, muestra un mensaje al usuario
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
