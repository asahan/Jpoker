package your.moguratataki;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.PixelFormat;
import android.view.Window;

public class MoguraTatakiActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(new MoguraView(this));
    }
}