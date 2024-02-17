package com.example.composetutorial

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel

class SensorHandler(viewModel: UserViewModel): SensorEventListener {
    private lateinit var mSensorManager : SensorManager
    private var resume = true
    private var mLight : Sensor ?= null
    private var viewmodel = viewModel

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null && resume) {
            if (event.sensor.type == Sensor.TYPE_PRESSURE) {
                viewmodel.setValue(event.values[0].toString())
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    fun initSensor(context: Context) {
        val mSensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL)
    }
}