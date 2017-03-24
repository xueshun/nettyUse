package bhz.netty.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * �ļ�ѹ��
 * 		1.����ѹ��
 * 		2.����ѹ��
 * @author Administrator
 *		����ѹ��
 */
public class GzipUtils {
	
	//ѹ��
	public static byte[] gzip(byte[] data) throws Throwable{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(bos);
		gzip.write(data);
		gzip.finish();
		gzip.close();
		byte[] ret = bos.toByteArray();
		bos.close();
		return ret;
		
	}
	//��ѹ
	public static byte[] ungizp(byte[] data) throws Exception{
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		GZIPInputStream gzip = new GZIPInputStream(bis);
		byte[] buf = new byte[1024];
		int num = -1;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while((num = gzip.read(buf, 0, buf.length)) != -1){
			bos.write(buf,0,num);
		}
		gzip.close();
		bis.close();
		byte[] ret = bos.toByteArray();
		bos.flush();
		bos.close();
		return ret;	
	}
	
	public static void main(String[] args) throws Throwable {
		//��ȡ�ļ�
		String readPath = System.getProperty("user.dir") + File.separatorChar + "sources" +File.separatorChar
				+"006.jpg";
		File file = new File(readPath);
		FileInputStream in = new FileInputStream(file);
		byte[] data = new byte[in.available()];
		in.read(data);
		in.close();
		
		System.out.println("�ļ�ԭʼ��С��" + data.length);
		
		//����ѹ��
		byte[] ret1 = GzipUtils.gzip(data);
		System.out.println("ѹ��֮���С��"+ret1.length);
		
		byte[] ret2 = GzipUtils.ungizp(ret1);
		System.out.println("��ԭ֮���С��"+ ret2.length);
		
		//д���ļ�
		String writePath = System.getProperty("user.dir") + File.separatorChar + "receive" + File.separatorChar +
				File.separatorChar + "006.jpg";
		
		FileOutputStream fos = new FileOutputStream(writePath);
		fos.write(ret2);
		fos.close();
	}
}
