package your.moguratataki;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class MoguraTatakiActivity extends Activity {
    /**
	 * Called when the activity is first created.
	 * @uml.property  name="handler"
	 * @uml.associationEnd  readOnly="true"
	 */
	Handler handler;
	/**
	 * @uml.property  name="isFinished"
	 */
	boolean isFinished = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.logo);

		initialize();		

		return;
	}

	private void initialize() {
		Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (isFinished == true) {
					finish();
					startActivity(new Intent(MoguraTatakiActivity.this,
							main.class));
				}

				return;
			}

		};
		handler.sendEmptyMessageDelayed(0, 1500);
	}

	@Override
	protected void onDestroy() {
		isFinished = false;
		super.onDestroy();
	}
}