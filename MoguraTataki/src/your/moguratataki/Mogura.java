package your.moguratataki;
import android.graphics.Bitmap;

//두더지
public class Mogura {
	//이미지
	public static Bitmap[] bmp=new Bitmap[3];
	
	//변수
	/**
	 * @uml.property  name="state"
	 */
	public int state; //(4)
	/**
	 * @uml.property  name="x"
	 */
	public int x;
	/**
	 * @uml.property  name="y"
	 */
	public int y;
	
	//생성자
	public Mogura(int x, int y) {
		this.x=x;
		this.y=y;
		state=-MoguraView.rand(5);
	}
	
	//충돌 판정
	public boolean isHit(int touchX, int touchY) {
		if(0<=state && state<20 && x<touchX && touchX<x+80 && y<touchY && touchY<y+80) {
			state=20;
			return true;
		}
		return false;
	}
	
	//정기처리
	public void tick() {
		state++;
		if(state==19 || state==39) {
			state=-MoguraView.rand(5);
		}
	}
	
	//그리기
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
