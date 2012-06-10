package your.moguratataki;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;

public class ongame extends Activity {
	@Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(new MoguraView(this));
    }
/*
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
		startActivity(new Intent(ongame.this, main.class));
	}
	*/
	
}

