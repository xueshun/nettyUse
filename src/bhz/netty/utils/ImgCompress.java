package bhz.netty.utils;

import java.awt.Image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import com.sun.image.codec.jpeg.*; 
import javax.imageio.ImageIO;


public class ImgCompress {  
	private Image img;  
	private int width;  
	private int height;  
	@SuppressWarnings("deprecation")  
	public static void main(String[] args) throws Exception {  
		System.out.println("��ʼ��" + new Date().toLocaleString());  
		String readPath = System.getProperty("user.dir") + File.separatorChar + "sources" + File.separatorChar + "006.jpg";
		ImgCompress imgCom = new ImgCompress(readPath);  
		imgCom.resizeFix(1400, 1400);  
		System.out.println("������" + new Date().toLocaleString());  
	}  
	/** 
	 * ���캯�� 
	 */  
	public ImgCompress(String fileName) throws IOException {  
		File file = new File(fileName);// �����ļ�  
		img = ImageIO.read(file);      // ����Image����  
		width = img.getWidth(null);    // �õ�Դͼ��  
		height = img.getHeight(null);  // �õ�Դͼ��  
	}  
	/** 
	 * ���տ�Ȼ��Ǹ߶Ƚ���ѹ�� 
	 * @param w int ����� 
	 * @param h int ���߶� 
	 */  
	public void resizeFix(int w, int h) throws IOException {  
		if (width / height > w / h) {  
			resizeByWidth(w);  
		} else {  
			resizeByHeight(h);  
		}  
	}  
	/** 
	 * �Կ��Ϊ��׼���ȱ�������ͼƬ 
	 * @param w int �¿�� 
	 */  
	public void resizeByWidth(int w) throws IOException {  
		int h = (int) (height * w / width);  
		resize(w, h);  
	}  
	/** 
	 * �Ը߶�Ϊ��׼���ȱ�������ͼƬ 
	 * @param h int �¸߶� 
	 */  
	public void resizeByHeight(int h) throws IOException {  
		int w = (int) (width * h / height);  
		resize(w, h);  
	}  
	/** 
	 * ǿ��ѹ��/�Ŵ�ͼƬ���̶��Ĵ�С 
	 * @param w int �¿�� 
	 * @param h int �¸߶� 
	 */  
	public void resize(int w, int h) throws IOException {  
		// SCALE_SMOOTH �������㷨 ��������ͼƬ��ƽ���ȵ� ���ȼ����ٶȸ� ���ɵ�ͼƬ�����ȽϺ� ���ٶ���  
		BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_BYTE_GRAY );   
		image.getGraphics().drawImage(img, 0, 0, w, h, null); // ������С���ͼ  
		String writPath = System.getProperty("user.dir") + File.separatorChar + "receive" + File.separatorChar + "006.jpg";
		File destFile = new File(writPath);  
		FileOutputStream out = new FileOutputStream(destFile); // ������ļ���  
		// ��������ʵ��bmp��png��gifתjpg  
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
		encoder.encode(image); // JPEG����  
		out.close();  
	}  
}  


