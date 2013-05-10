package net.kibotu.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;

public class ShakerHandler {

    private final SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(final SensorEvent e) {
            if ( e.sensor.getType() == Sensor.TYPE_ACCELEROMETER ) {
                double netForce = e.values[0] * e.values[0];

                netForce += e.values[1] * e.values[1];
                netForce += e.values[2] * e.values[2];

                if ( threshold < netForce ) {
                    isShaking();
                } else {
                    isNotShaking();
                }
            }
        }

        @Override
        public void onAccuracyChanged(final Sensor sensor, final int accuracy) {
            // unused
        }
    };
    private SensorManager mgr = null;
    private long lastShakeTimestamp = 0;
    private double threshold = 1.0d;
    private long gap = 0;
    private ShakerHandler.Callback cb = null;

    /**
     * Handler for Shaking Sensor Service.
     *
     * @param ctxt
     * @param threshold
     * @param gap
     * @param cb
     */
    public ShakerHandler(final Context ctxt, final double threshold, final long gap, final ShakerHandler.Callback cb) {
        this.threshold = threshold * threshold;
        this.threshold = this.threshold * SensorManager.GRAVITY_EARTH
                * SensorManager.GRAVITY_EARTH;
        this.gap = gap;
        this.cb = cb;

        mgr = ( SensorManager ) ctxt.getSystemService( Context.SENSOR_SERVICE );
        mgr.registerListener( listener, mgr.getDefaultSensor( Sensor.TYPE_ACCELEROMETER ), SensorManager.SENSOR_DELAY_UI );
    }

    public void close() {
        mgr.unregisterListener( listener );
    }

    private void isShaking() {
        final long now = SystemClock.uptimeMillis();

        if ( lastShakeTimestamp == 0 ) {
            lastShakeTimestamp = now;

            if ( cb != null ) {
                cb.shakingStarted();
            }
        } else {
            lastShakeTimestamp = now;
        }
    }

    private void isNotShaking() {
        final long now = SystemClock.uptimeMillis();

        if ( lastShakeTimestamp > 0 ) {
            if ( now - lastShakeTimestamp > gap ) {
                lastShakeTimestamp = 0;

                if ( cb != null ) {
                    cb.shakingStopped();
                }
            }
        }
    }

    public interface Callback {
        void shakingStarted();

        void shakingStopped();
    }
}
