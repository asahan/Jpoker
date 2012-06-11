package your.moguratataki;

import android.graphics.Bitmap;

public class car_2nd {
	public static Bitmap [] bmp = new Bitmap[2];
	public static final int normal=0;
	public static final int crash=1;
	public static final int touched=2;
	
	
	public int state;
	public int damage;
	public int x;
	public int y;
	public int number;
	public car_2nd(int x, int y){
		state = normal;
		damage = 0;
		this.x=x;
		this.y=y;
	}
	
	public boolean isHit(int touchX,int touchY)
	{
		if(x<touchX && touchX < x+125 && y< touchY && touchY < y+250)
		{
			damage++;
			if(damage >= 60)
				state = crash;
			return true;
		}
		return false;
	}
	
	public void draw(Graphics g)
	{
		int set=0;
		if(state == normal)
			set = 0;
		else if(state == crash )
			set =1;
		else if(state == touched)
			set =1 ;
		g.drawBitmap(bmp[set],x,y);
	}
	public void touch_down()
	{
		if(state == normal)
			state = touched;
	
		
	}
	public void touch_up()
	{
		if(state == touched)
			state = normal;
		
	}

}
