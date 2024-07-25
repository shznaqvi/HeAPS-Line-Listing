package edu.aku.hassannaqvi.heapslinelisting.core;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorManagerSingleton implements SensorEventListener {
    private static SensorManagerSingleton INSTANCE = null;
    private final SensorManager sensorManager;
    private final Sensor rotationVectorSensor;
    private float azimuth;

    private SensorManagerSingleton(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    public static synchronized SensorManagerSingleton getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SensorManagerSingleton(context);
        }
        return INSTANCE;
    }

    public void startListening() {
        sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_UI);
    }

    public void stopListening() {
        sensorManager.unregisterListener(this);
    }

    public float getAzimuth() {
        return azimuth;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            float[] rotationMatrix = new float[9];
            float[] orientationValues = new float[3];
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
            SensorManager.getOrientation(rotationMatrix, orientationValues);
            azimuth = (float) Math.toDegrees(orientationValues[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }
}
