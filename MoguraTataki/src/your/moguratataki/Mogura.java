package your.moguratataki;
import android.graphics.Bitmap;

//�δ���
public class Mogura {
	//�̹���
	public static Bitmap[] bmp=new Bitmap[3];
	
	//����
	public int state; //(4)
	public int x;
	public int y;
	
	//������
	public Mogura(int x, int y) {
		this.x=x;
		this.y=y;
		state=-MoguraView.rand(500);
	}
	
	//�浹 ����
	public boolean isHit(int touchX, int touchY) {
		if(0<=state && state<20 && x<touchX && touchX<x+80 && y<touchY && touchY<y+80) {
			state=20;
			return true;
		}
		return false;
	}
	
	//����ó��
	public void tick() {
		state++;
		if(state==19 || state==39) {
			state=-MoguraView.rand(500);
		}
	}
	
	//�׸���
	public void draw(Graphics g) {
		int idx=0;
		if (state<0) {
			idx=0;
		} else if(state<20) {
			idx=1;
		} else if(state<40) {
			idx=2;
		}
		g.drawBitmap(bmp[idx],x,y);
	}
}
