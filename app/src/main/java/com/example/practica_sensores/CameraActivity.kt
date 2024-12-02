package com.example.practica_sensores

import android.Manifest // Para manejar los permisos
import android.content.Intent // Para lanzar actividades (como la cámara)
import android.content.pm.PackageManager // Para verificar permisos
import android.graphics.Bitmap // Para manejar imágenes en formato Bitmap
import android.os.Bundle // Para manejar el ciclo de vida de la actividad
import android.provider.MediaStore // Para interactuar con la cámara
import android.widget.Button // Para el botón que tomará la foto
import android.widget.ImageView // Para mostrar la imagen capturada
import android.widget.Toast // Para mostrar mensajes al usuario
import androidx.activity.result.ActivityResultLauncher // Para manejar resultados de actividades
import androidx.activity.result.contract.ActivityResultContracts // Contrato para iniciar actividades
import androidx.appcompat.app.AppCompatActivity // Clase base para actividades
import androidx.core.app.ActivityCompat // Para pedir permisos en tiempo de ejecución
import androidx.core.content.ContextCompat // Para verificar el estado de permisos

class CameraActivity : AppCompatActivity() {

    private lateinit var btnTomarFoto: Button
    private lateinit var imageView: ImageView

    private lateinit var captureImageLauncher: ActivityResultLauncher<Intent>

    private val cameraPermission = Manifest.permission.CAMERA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        btnTomarFoto = findViewById(R.id.btnTomarFoto)
        imageView = findViewById(R.id.imageView)

        captureImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val bitmap = result.data?.extras?.get("data") as? Bitmap
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap)
                } else {
                    Toast.makeText(this, "No se pudo capturar la imagen", Toast.LENGTH_SHORT).show()
                }
            } else {
          }
        }

        if (ContextCompat.checkSelfPermission(this, cameraPermission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(cameraPermission), 100)
        }

        btnTomarFoto.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, cameraPermission) == PackageManager.PERMISSION_GRANTED) {
                abrirCamara()
            } else {
                Toast.makeText(this, "Se requieren permisos de cámara", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun abrirCamara() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (takePictureIntent.resolveActivity(packageManager) != null) {
            captureImageLauncher.launch(takePictureIntent)
        } else {
            Toast.makeText(this, "No se pudo abrir la cámara", Toast.LENGTH_SHORT).show()
        }
    }
}
