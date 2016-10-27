package org.gztech.compass;

import org.gztech.compass.view.DialView;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private SensorManager mSensorManager;
	private Sensor mSensor;
	private float[] mValues;
	private TextView mTextView, mOrientation;
	private DialView mDialView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.e("sos", "onCreate");
		final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
		stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
			@Override
			public void onLayoutInflated(WatchViewStub stub) {

				mTextView = (TextView) stub.findViewById(R.id.text);
				mOrientation = (TextView) stub.findViewById(R.id.orientation);

			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		Log.e("sos", "onStart");
		// 取得Sensor（传感器）的管理类
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		// 取得方向传感器
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		// 注册传感器监听事件
		mSensorManager.registerListener(mSensorEventListener, mSensor,
				SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.e("sos", "onStop");
		mSensorManager.unregisterListener(mSensorEventListener);
	}

	SensorEventListener mSensorEventListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			Log.e("sos", "event:" + event.values[0]);

			mValues = event.values;
			final float direction = mValues[0];

			if (direction >= 0 && direction < 90) {

				new UiThread(String.valueOf(direction), "北").start();

				// mOrientation.setText("北");
				// mTextView.setText("direction:" + direction);

			} else if (direction >= 90 && direction < 180) {

				new UiThread(String.valueOf(direction), "东").start();
				// mOrientation.setText("东");
				// mTextView.setText("direction:" + direction);

			} else if (direction >= 180 && direction < 270) {

				new UiThread(String.valueOf(direction), "南").start();
				// mOrientation.setText("南");
				// mTextView.setText("direction:" + direction);

			} else if (direction >= 270 && direction < 360) {

				new UiThread(String.valueOf(direction), "西").start();
				// mOrientation.setText("西");
				// mTextView.setText("direction:" + direction);
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub

			Log.e(TAG, "accuracy:" + accuracy);
		}
	};

	class UiThread extends Thread {

		String direction;
		String orientation;

		public UiThread(String direction, String orientation) {
			super();
			this.direction = direction;
			this.orientation = orientation;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();

			Message msg = new Message();
			msg.what = 0;
			Bundle data = new Bundle();
			data.putString("orientation", orientation);
			data.putString("direction", direction);
			msg.setData(data);
			mHandler.sendMessage(msg);
		}

	}

	Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {

			Bundle bundle = msg.getData();
			String direction = bundle.getString("direction");
			String orientation = bundle.getString("orientation");

			mOrientation.setText(orientation);
			mTextView.setText(direction);

		};
	};
}
