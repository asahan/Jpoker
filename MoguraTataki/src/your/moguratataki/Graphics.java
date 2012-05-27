package your.moguratataki;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

public class Graphics {
	private SurfaceHolder holder;
	private Paint         paint;
	private Canvas        canvas;
	
	//�׷��Ƚ�
	public Graphics(SurfaceHolder holder) {
		this.holder=holder;
		paint=new Paint();
		paint.setAntiAlias(true);
	}
	
	//���
	public void lock() {
		canvas=holder.lockCanvas();
	}
	//�������
	public void unlock() {
		holder.unlockCanvasAndPost(canvas);
	}
	
	//������
	public void setColor(int color) {
		paint.setColor(color);
	}
	
	//��Ʈ ũ�� ����
	public void setFontSize(int fontSize) {
		paint.setTextSize(fontSize);
	}
	//���� �� ���ϱ�
	public int stringWidth(String string) {
		return (int) paint.measureText(string);
	}
	
	//�簢�� �׸���
	public void fillRect(int x, int y, int w, int h) {
		paint.setStyle(Paint.Style.FILL);
		canvas.drawRect(new Rect(x,y,x+w,y+h), paint);
	}
	
	//��Ʈ�� �׸���
	public void drawBitmap(Bitmap bitmap,int x, int y) {
		canvas.drawBitmap(bitmap, x, y, null);
	}
	
	//���ڿ� �׸���
	public void drawString(String string, int x, int y) {
		canvas.drawText(string, x, y, paint);
	}
}
