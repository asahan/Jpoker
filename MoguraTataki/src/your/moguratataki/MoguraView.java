package your.moguratataki;

import android.content.res.*;
import android.content.*;
import android.graphics.*;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.*;
import java.util.ArrayList;
import java.util.Random;

//두더지 잡기 뷰
public class MoguraView extends SurfaceView 
	implements SurfaceHolder.Callback,Runnable {
		//장면 정수 (1)
		private final static int
		S_TITLE    =0,
		S_PLAY     =1,
		S_GAMEOVER =2;
		
		// 시스템
		private SurfaceHolder holder; //표면 홀더
		private Thread        thread; //스레드
		private Graphics      g; //그래픽스
		
		//게임
		private int init=S_TITLE; //초기화 (1)
		private int scene; //장면(1)
		private int score; //점수
		private int High_score;
		private long endTime; //종료시간
		private ArrayList<Mogura> moguras; //두더지
		private Bitmap[] bmp=new Bitmap[1];
		private MediaPlayer mp;
		private SoundPool sp;
		private int tak;
		//생성자
		public MoguraView(Context context) {
			super(context);
			
			//그림읽기
			Resources r=getResources();
			Mogura.bmp[0]=BitmapFactory.decodeResource(r,R.drawable.mogura0);
			Mogura.bmp[1]=BitmapFactory.decodeResource(r, R.drawable.mogura1);
			Mogura.bmp[2]=BitmapFactory.decodeResource(r,R.drawable.mogura2);
			bmp[0]=BitmapFactory.decodeResource(r,R.drawable.mogura3);
			mp=MediaPlayer.create(context, R.raw.chickenbgm);
			sp = new SoundPool(1,AudioManager.STREAM_MUSIC,0);
			tak=sp.load(context,R.raw.click_gogi,1);
			//그래픽스의 생성
			holder=getHolder();
			holder.addCallback(this);
			g=new Graphics(holder);
		}
		
		//표면생성
		public void surfaceCreated(SurfaceHolder holder) {
			thread=new Thread(this);
			thread.start();
		}
		
		//표면파괴
		public void surfaceDestroyed(SurfaceHolder holder){
			thread = null;
		}
		
		//표면변경
		public void surfaceChanged(SurfaceHolder holder, int format, int w, int h){
		}
		
		//스레드 처리
		public void run() {
			String str;
			
			//정기처리(2)
			while(thread!=null) {
				//장면의 초기화
				if (init>=0) {
					scene=init;
					init=-1;
					
					//타이틀 초기화
					if(scene==S_TITLE) {
						if(High_score<score)
						High_score=score;
						score=0;
						moguras=new ArrayList<Mogura>();
						int dy=80;
						for (int i=0; i<10; i++) {
							moguras.add(new Mogura(rand(getWidth ()/2-80),dy));
							moguras.add(new Mogura(getWidth()/2+rand(getWidth()/2-80),dy));
							dy+=30;
						}
					}
					
					//플레이의 초기화
					else if(scene==S_PLAY) {
						endTime=System.currentTimeMillis()+30000;
					}
					//게임오버의 초기화
					else if(scene==S_GAMEOVER) {
						endTime=System.currentTimeMillis()+3000;
					}
				}
				
				//배경 그리기
				g.lock();
				g.setColor(Color.rgb(88, 197, 241));
				g.fillRect(0, 0, getWidth(),100);
				g.setColor(Color.rgb(144, 198, 116));
				g.fillRect(0,100,getWidth(),getHeight()-100);
				g.drawBitmap(bmp[0], 0, 100);
				
				//두더지의 그리기
				for(int i=0; i<moguras.size();i++) {
					if(scene==S_PLAY) moguras.get(i).tick();
					moguras.get(i).draw(g);
				}
				
				//점수그리기
				g.setColor(Color.rgb(255, 0, 0));
				g.setFontSize(20);
				str="Score";
				g.drawString(str,(120-g.stringWidth(str))/2,20);
				str=""+score;
				g.setFontSize(60);
				g.drawString(str,(120-g.stringWidth(str))/2,80);
				
				g.setColor(Color.rgb(0, 0, 0));
			    g.setFontSize(20);
			    str="HighScore";
			    g.drawString(str,(300-g.stringWidth(str))/2,20);
			    str=""+High_score;
			    g.setFontSize(60);
			    g.drawString(str,(300-g.stringWidth(str))/2,80);
			    
				//시간그리기
				g.setColor(Color.rgb(40, 40, 125));
				g.setFontSize(20);
				str="Time";
				g.drawString(str, getWidth()-120+(120-g.stringWidth(str))/2,20);
				g.setFontSize(40);
				int time=(int)(endTime-System.currentTimeMillis())/1000;
				
				if(scene!=S_PLAY) {
					str="00:00";
				} else if(time<=0) {
					str="00:00";
					init=S_GAMEOVER;
				} else if(time<10) {
					str="00:0"+time;
				} else {
					str="00:"+time;
				}
				g.drawString(str,getWidth()-120+(120-g.stringWidth(str))/2,70);
				
				//메시지 그리기
				if(scene==S_TITLE || scene==S_GAMEOVER) {
					if(scene==S_TITLE) str="닭다리 먹기";
					if(scene==S_GAMEOVER) str="게임 종료";
					g.setColor(Color.rgb(255, 0, 0));
					g.setFontSize(80);
					g.drawString(str, (getWidth() -g.stringWidth(str))/2,200);
				}
				g.unlock();
				
				//슬립
				try {
					Thread.sleep(50);
				} catch (Exception e) {
				}
			}
		}
		
		//터치 이벤트의 처리
		@Override
		public boolean onTouchEvent (MotionEvent event) {
			int touchX=(int) event.getX();
			int touchY=(int) event.getY();
			int touchAction=event.getAction();
			if(touchAction==MotionEvent.ACTION_DOWN) {
				if(scene==S_TITLE) {
					init=S_PLAY;

				mp.setLooping(true);
				mp.start();
				} else if (scene==S_PLAY) {
					
					for (int i=0; i<moguras.size(); i++) {
						if(moguras.get(i).isHit(touchX,touchY)) {
							score++; sp.play(tak, 1,1,0,0,1);
						}
					}
				} else if (scene==S_GAMEOVER) {
					mp.pause();
					if (endTime<System.currentTimeMillis()) init=S_TITLE;
				}
			}
			return true;
		}
			
		//난수 구하기 (3)
		private static Random rand=new Random();
		public static int rand(int num) {
			return (rand.nextInt()>>>1) %num;
			}
}
