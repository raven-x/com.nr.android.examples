package com.example.vkirillov.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor rotationSensor;
    private float[] gravity = new float[3];
    private float[] motion = new float[3];
    private double ratio;
    private double angle;
    private TextView txtGravity;
    private TextView txtRotation;
    private int counter;
    private final AccelerationSensorListener accelerationSensorListener = new AccelerationSensorListener();
    private final RotationSensorListener rsl = new RotationSensorListener();

    private Map<Integer, String> sensorTypes = new HashMap<Integer, String>();
    {
        sensorTypes.put(Sensor.TYPE_ACCELEROMETER,
                "TYPE_ACCELEROMETER");
        sensorTypes.put(Sensor.TYPE_AMBIENT_TEMPERATURE,
                "TYPE_AMBIENT_TEMPERATURE");
        sensorTypes.put(Sensor.TYPE_ROTATION_VECTOR,
                "TYPE_ROTATION_VECTOR");
        sensorTypes.put(Sensor.TYPE_GYROSCOPE, "TYPE_GYROSCOPE");
        sensorTypes.put(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR, "TYPE_GEOMAGNETIC_ROTATION_VECTOR");
        sensorTypes.put(Sensor.TYPE_GRAVITY, "TYPE_GRAVITY");
        sensorTypes.put(Sensor.TYPE_LIGHT, "LIGHT");
        sensorTypes.put(Sensor.TYPE_TEMPERATURE, "TYPE_TEMPERATURE");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtGravity = (TextView) findViewById(R.id.txtGravity);
        txtRotation = (TextView) findViewById(R.id.txtRotation);
        txtGravity.setMovementMethod(new ScrollingMovementMethod());
        txtRotation.setMovementMethod(new ScrollingMovementMethod());
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        TextView txt = (TextView) findViewById(R.id.txt);
        txt.setMovementMethod(new ScrollingMovementMethod());

        StringBuilder message = new StringBuilder("The sensors on this device are:\n");
        for(Sensor sensor : sensorList){
            message.append("--- ").append(sensor.getName()).append("\n");
            message.append("\tType: ").append(sensorTypes.get(sensor.getType())).append("\n");
            message.append("\tVendor: ").append(sensor.getVendor()).append("\n");
            message.append("\tVersion: ").append(sensor.getVersion()).append("\n");
            message.append("\tMin delay: ").append(sensor.getMinDelay()).append("\n");
            message.append("\tFIFO max event count: ").append(sensor.getFifoMaxEventCount()).append("\n");
            message.append("\tResolution: ").append(sensor.getResolution()).append("\n");
            message.append("\tMax range: ").append(sensor.getMaximumRange()).append("\n");
            message.append("\tPower: ").append(sensor.getPower()).append("\n");
        }

        txt.setText(message.toString());
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(accelerationSensorListener, accelerometer);
        sensorManager.unregisterListener(rsl, rotationSensor);
        super.onPause();
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(accelerationSensorListener, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(rsl, rotationSensor, SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    private class AccelerationSensorListener implements SensorEventListener{
        @Override
        public void onSensorChanged(SensorEvent event) {
            for(int i=0;i<3;i++){
                //IIR, low pass filter
                gravity[i] = 0.1f * event.values[i] + 0.9f * gravity[i];
                //motion is what's left over
                motion[i] = event.values[i] - gravity[i];
            }

        /*ratio is gravity on the Y axis compared to full
        gravity, should be no more than 1, no less than -1*/
            ratio = gravity[1]/SensorManager.GRAVITY_EARTH;
            //-1 <= ratio <= 1
            ratio = Math.max(-1.0f, Math.min(1.0f, ratio));

            // convert radians to degrees, make negative if facing up
            angle = Math.toDegrees(Math.acos(ratio));
            if(gravity[2] < 0){
                angle = -angle;
            }

            //
            // Display every 10th value
            if(counter++ % 10 == 0) {
                String msg = String.format(
                        "Raw values\nX: %8.4f\nY: %8.4f\nZ: %8.4f\n" +
                                "Gravity\nX: %8.4f\nY: %8.4f\nZ: %8.4f\n" +
                                "Motion\nX: %8.4f\nY: %8.4f\nZ: %8.4f\nAngle: %8.1f",
                        event.values[0], event.values[1],
                        event.values[2],
                        gravity[0], gravity[1], gravity[2],
                        motion[0], motion[1], motion[2],
                        angle);
                txtGravity.setText(msg);
                txtGravity.invalidate();
                counter=1;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    private class RotationSensorListener implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {
                float[] rotationMatrix = new float[9];
                float[] orientation = new float[3];
                SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
                SensorManager.getOrientation(rotationMatrix, orientation);
                String msg = String.format("azimuth: %.2f, pitch: %.2f, roll: %.2f",
                        orientation[0], orientation[1], orientation[2]);
                txtRotation.setText(msg);
                txtRotation.invalidate();
                counter = 1;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    }
}
