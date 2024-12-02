package com.example.practica_sensores

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class ContadorPasosActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var stepCounterSensor: Sensor? = null
    private var totalSteps = 0f
    private var previousTotalSteps = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contador_pasos)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val PERMISSION_REQUEST_ACTIVITY_RECOGNITION = 100
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACTIVITY_RECOGNITION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                    PERMISSION_REQUEST_ACTIVITY_RECOGNITION
                )
            }
        }

        if (stepCounterSensor == null) {
            println("El sensor de pasos no está disponible en este dispositivo")
        }

        // Configura los botones
        val startButton = findViewById<Button>(R.id.btn_empezar)
        val stopButton = findViewById<Button>(R.id.btn_detener)

        startButton.setOnClickListener {
            stepCounterSensor?.also { sensor ->
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
            }
        }

        stopButton.setOnClickListener {
            sensorManager.unregisterListener(this)
            totalSteps = 0f
            previousTotalSteps = 0f

            val stepsTextView = findViewById<TextView>(R.id.txt_pasos)
            stepsTextView.text = "0.00"
        }
    }

    override fun onResume() {
        super.onResume()
        stepCounterSensor?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            if (previousTotalSteps == 0f) {
                previousTotalSteps = event.values[0]
            }
            val currentSteps = event.values[0] - previousTotalSteps
            totalSteps = currentSteps

            val stepsTextView = findViewById<TextView>(R.id.txt_pasos)
            stepsTextView.text = String.format("%.2f", totalSteps)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}