package your.moguratataki;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.PixelFormat;
import android.view.Window;

/**
 * @uml.dependency   supplier="your.moguratataki.main"
 */
public class CarbreakActivity extends main {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(new CarbreakView(this));
        //setContentView(R.layout.main);
    }
}