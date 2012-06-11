package your.moguratataki;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class main extends Activity {

	@Override
	 
    public void onCreate(Bundle savedInstanceState)
 
    {
 
        super.onCreate(savedInstanceState);
 
        setContentView(R.layout.main);
        
        
        
        
    	Button button1 = (Button)findViewById(R.id.game1);
 
        button1.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View v) {
 
            	
            		
            		startActivity(new Intent(main.this, ongame.class));//1번게임 실행
            	 
            }
        });
        
        Button button2 = (Button)findViewById(R.id.game2);
        
        button2.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View v) {
 
            	
            		
            		startActivity(new Intent(main.this, CarbreakActivity.class)); //2게임 실행
            	 
            }
        });
    }
}

