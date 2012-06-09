package your.moguratataki;

import android.content.res.*;
import android.content.*;
import android.graphics.*;
import android.view.*;
import java.util.ArrayList;
import java.util.Random;

//�δ��� ��� ��
public class MoguraView extends SurfaceView 
	implements SurfaceHolder.Callback,Runnable {
		//��� ���� (1)
		private final static int
		S_TITLE    =0,
		S_PLAY     =1,
		S_GAMEOVER =2;
		
		// �ý���
		private SurfaceHolder holder; //ǥ�� Ȧ��
		private Thread        thread; //������
		private Graphics      g; //�׷��Ƚ�
		
		//����
		private int init=S_TITLE; //�ʱ�ȭ (1)
		private int scene; //���(1)
		private int score; //����
		private long endTime; //����ð�
		private ArrayList<Mogura> moguras; //�δ���
		private Bitmap[] bmp=new Bitmap[1];
		
		//������
		public MoguraView(Context context) {
			super(context);
			
			//�׸��б�
			Resources r=getResources();
			Mogura.bmp[0]=BitmapFactory.decodeResource(r,R.drawable.mogura0);
			Mogura.bmp[1]=BitmapFactory.decodeResource(r, R.drawable.mogura1);
			Mogura.bmp[2]=BitmapFactory.decodeResource(r,R.drawable.mogura2);
			bmp[0]=BitmapFactory.decodeResource(r,R.drawable.mogura3);
			
			//�׷��Ƚ��� ����
			holder=getHolder();
			holder.addCallback(this);
			g=new Graphics(holder);
		}
		
		//ǥ�����
		public void surfaceCreated(SurfaceHolder holder) {
			thread=new Thread(this);
			thread.start();
		}
		
		//ǥ���ı�
		public void surfaceDestroyed(SurfaceHolder holder){
			thread = null;
		}
		
		//ǥ�麯��
		public void surfaceChanged(SurfaceHolder holder, int format, int w, int h){
		}
		
		//������ ó��
		public void run() {
			String str;
			
			//����ó��(2)
			while(thread!=null) {
				//����� �ʱ�ȭ
				if (init>=0) {
					scene=init;
					init=-1;
					
					//Ÿ��Ʋ �ʱ�ȭ
					if(scene==S_TITLE) {
						score=0;
						moguras=new ArrayList<Mogura>();
						int dy=80;
						for (int i=0; i<10; i++) {
							moguras.add(new Mogura(rand(getWidth ()/2-80),dy));
							moguras.add(new Mogura(getWidth()/2+rand(getWidth()/2-80),dy));
							dy+=30;
						}
					}
					
					//�÷����� �ʱ�ȭ
					else if(scene==S_PLAY) {
						endTime=System.currentTimeMillis()+30000;
					}
					//���ӿ����� �ʱ�ȭ
					else if(scene==S_GAMEOVER) {
						endTime=System.currentTimeMillis()+3000;
					}
				}
				
				//��� �׸���
				g.lock();
				g.setColor(Color.rgb(88, 197, 241));
				g.fillRect(0, 0, getWidth(),100);
				g.setColor(Color.rgb(144, 198, 116));
				g.fillRect(0,100,getWidth(),getHeight()-100);
				g.drawBitmap(bmp[0], 0, 100);
				
				//�δ����� �׸���
				for(int i=0; i<moguras.size();i++) {
					if(scene==S_PLAY) moguras.get(i).tick();
					moguras.get(i).draw(g);
				}
				
				//�����׸���
				g.setColor(Color.rgb(255, 0, 0));
				g.setFontSize(20);
				str="Score";
				g.drawString(str,(120-g.stringWidth(str))/2,20);
				str=""+score;
				g.setFontSize(60);
				g.drawString(str,(120-g.stringWidth(str))/2,80);
				
				//�ð��׸���
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
				
				//�޽��� �׸���
				if(scene==S_TITLE || scene==S_GAMEOVER) {
					if(scene==S_TITLE) str="�ߴٸ� �Ա�";
					if(scene==S_GAMEOVER) str="���� ����";
					g.setColor(Color.rgb(255, 0, 0));
					g.setFontSize(80);
					g.drawString(str, (getWidth() -g.stringWidth(str))/2,200);
				}
				g.unlock();
				
				//����
				try {
					Thread.sleep(50);
				} catch (Exception e) {
				}
			}
		}
		
		//��ġ �̺�Ʈ�� ó��
		@Override
		public boolean onTouchEvent (MotionEvent event) {
			int touchX=(int) event.getX();
			int touchY=(int) event.getY();
			int touchAction=event.getAction();
			if(touchAction==MotionEvent.ACTION_DOWN) {
				if(scene==S_TITLE) {
					init=S_PLAY;
				} else if (scene==S_PLAY) {
					for (int i=0; i<moguras.size(); i++) {
						if(moguras.get(i).isHit(touchX,touchY)) score++;
					}
				} else if (scene==S_GAMEOVER) {
					if (endTime<System.currentTimeMillis()) init=S_TITLE;
				}
			}
			return true;
		}
			
		//���� ���ϱ� (3)
		private static Random rand=new Random();
		public static int rand(int num) {
			return (rand.nextInt()>>>1) %num;
			}
}
