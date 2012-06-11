package org.carbreak;

import android.content.*;
//import android.content.res.*;
import android.graphics.*;
import android.view.*;
import java.util.ArrayList;


public class CarbreakView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
	private final static int Title = 0;
	private final static int Play =1;
	private final static int Gameover= 2;
	
	private SurfaceHolder holder;
	private Thread	thread;
	private Graphics g;
	
	private int init = Title;
	private int scene;
	private int score;
	private long endTime;
	private ArrayList<car_1st> car_1; 
	private ArrayList<car_2nd> car_2; 
	private ArrayList<car_3rd> car_3; 
	private ArrayList<car_4th> car_4; 
	public CarbreakView(Context context) {
		super(context);
		car_1st.bmp[0]=BitmapFactory.decodeResource(getResources(),R.drawable.car0_normal);
		car_1st.bmp[1]=BitmapFactory.decodeResource(getResources(),R.drawable.car0_crash);
		car_2nd.bmp[0]=BitmapFactory.decodeResource(getResources(),R.drawable.car1_normal);
		car_2nd.bmp[1]=BitmapFactory.decodeResource(getResources(),R.drawable.car1_crash);
		car_3rd.bmp[0]=BitmapFactory.decodeResource(getResources(),R.drawable.car2_normal);
		car_3rd.bmp[1]=BitmapFactory.decodeResource(getResources(),R.drawable.car2_crash);
		car_4th.bmp[0]=BitmapFactory.decodeResource(getResources(),R.drawable.car3_normal);
		car_4th.bmp[1]=BitmapFactory.decodeResource(getResources(),R.drawable.car3_crash);
		
		
		//Car.bmp[0] = BitmapFactory.decodeResource(getResources(),R.drawable.car0_normal);
		//Car.bmp[1] = BitmapFactory.decodeResource(getResources(),R.drawable.car0_crash);
		/*
		car[0].bmp[0] = BitmapFactory.decodeResource(getResources(),R.drawable.car0_normal);
		car[0].bmp[1] = BitmapFactory.decodeResource(getResources(),R.drawable.car0_crash);
		car[1].bmp[0] = BitmapFactory.decodeResource(getResources(),R.drawable.car1_normal);
		car[1].bmp[1] = BitmapFactory.decodeResource(getResources(),R.drawable.car1_crash);
		car[2].bmp[0] = BitmapFactory.decodeResource(getResources(),R.drawable.car2_normal);
		car[2].bmp[1] = BitmapFactory.decodeResource(getResources(),R.drawable.car2_crash);
		car[3].bmp[0] = BitmapFactory.decodeResource(getResources(),R.drawable.car3_normal);
		car[3].bmp[1] = BitmapFactory.decodeResource(getResources(),R.drawable.car3_crash);
		*/
		holder = getHolder();
		holder.addCallback(this);
		g = new Graphics(holder);
		// TODO Auto-generated constructor stub
	}
	public void surfaceCreated(SurfaceHolder holder){
		thread = new Thread(this);
		thread.start();
	}
	public void surfaceDestroyed(SurfaceHolder holder){
		thread = null;
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h){
		
	}
	public void run(){
		String str;
		while(thread!=null){
			if(init>=0){
				scene = init;
				init = -1;
				
				if(scene == Title){
					score = 0;
					int x=150;
					int y=115;
					car_1=new ArrayList<car_1st>();
					car_1.add(new car_1st(x,y));
					car_2=new ArrayList<car_2nd>();
					car_2.add(new car_2nd(x+125,y));
					car_3=new ArrayList<car_3rd>();
					car_3.add(new car_3rd(x+250,y));
					car_4=new ArrayList<car_4th>();
					car_4.add(new car_4th(x+375,y));
					
				}
				else if(scene == Play){
					endTime = System.currentTimeMillis()+30000;
				}
				else if(scene == Gameover){
					endTime = System.currentTimeMillis()+3000;
				}
			}
			g.lock();
			g.setColor(Color.rgb(88,197,241));
			g.fillRect(0,0,getWidth(),100);
			g.setColor(Color.rgb(144,198,116));
			g.fillRect(0,100,getWidth(),getHeight()-100);
			
			car_1.get(0).draw(g);
			car_2.get(0).draw(g);
			car_3.get(0).draw(g);
			car_4.get(0).draw(g);
			g.setColor(Color.rgb(255,0,0));
			g.setFontSize(20);
			str="score";
			g.drawString(str,(120-g.stringWidth(str))/2,20);
			str=""+score;
			g.setFontSize(60);
			g.drawString(str,(120-g.stringWidth(str))/2,80);
			g.setColor(Color.rgb(40,40,125));
			g.setFontSize(20);
			str="Time";
			g.drawString(str,getWidth()-120+(120-g.stringWidth(str))/2,20);
			g.setFontSize(40);
			int time=(int)(endTime-System.currentTimeMillis())/1000;
			if(scene!=Play){
				str="00:00";
			}else if(time<=0){
				str="00:00";
				init=Gameover;
			}else if(time<10){
				str="00:0"+time;
			}else{
				str="00:"+time;
			}
			g.drawString(str,getWidth()-120+(120-g.stringWidth(str))/2,70);
			
			if(scene==Title || scene==Gameover) {
				if (scene==Title)    str="차 부시기";
				if (scene==Gameover) str="게임 종료";
				g.setColor(Color.rgb(255,0,0));
				g.setFontSize(80);
				g.drawString(str,(getWidth() -g.stringWidth(str))/2,200);
			}
			g.unlock();
			
			try{
				Thread.sleep(50);
			}catch (Exception e) {
			}
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int touchX=(int) event.getX();
		int touchY=(int) event.getY();
		int touchAction=event.getAction();
		if(touchAction==MotionEvent.ACTION_DOWN) {
			if(scene==Title) {
				init=Play;
			} else if(scene==Play){
					if(car_1.get(0).isHit(touchX,touchY))
					{
						car_1.get(0).touch_down();
						score++;
					}
					else if(car_2.get(0).isHit(touchX,touchY))
					{
						car_2.get(0).touch_down();
						score++;
					}
					else if(car_3.get(0).isHit(touchX,touchY))
					{
						car_3.get(0).touch_down();
						score++;
					}
					else if(car_4.get(0).isHit(touchX,touchY))
					{
						car_4.get(0).touch_down();
						score++;
					}
				
			}else if(scene ==Gameover) {
					if(endTime<System.currentTimeMillis()) init=Title;
			}
		}
		else if(touchAction == MotionEvent.ACTION_UP){
			if(scene == Play){
				if(car_1.get(0).isHit(touchX,touchY))
				{
					car_1.get(0).touch_up();
					
				}
				else if(car_2.get(0).isHit(touchX,touchY))
				{
					car_2.get(0).touch_up();
					
				}
				else if(car_3.get(0).isHit(touchX,touchY))
				{
					car_3.get(0).touch_up();
					
				}
				else if(car_4.get(0).isHit(touchX,touchY))
				{
					car_4.get(0).touch_up();
					
				}
			}
		}
		return true;
	}
}
		
