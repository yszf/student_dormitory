package cn.edu.xsyu.dorm.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Tools {
	 public static String getJsonContent(String path,String content) {
        try {
        	URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setDoOutput(true);// �����������
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type","application/json; charset=UTF-8"); // ��������
			OutputStream os = conn.getOutputStream();
			os.write(content.getBytes());
			os.close();
			int code = conn.getResponseCode();
            if (code == 200) {
                // ��õ�����������ʱ�������Ѿ������˷���˷��ػ�����JSON������,��ʱ��Ҫ�������ת�����ַ���
                return getTextFromStream(conn.getInputStream());
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return "hahaha";
    }
	
	public static String getTextFromStream(InputStream is){
		byte[] b = new byte[1024];
		int len;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			while((len = is.read(b)) != -1){
				bos.write(b,0,len);
			}
			//���ֽ����������ת�����ֽ����飬Ȼ�����ֽ����鹹��һ���ַ���
			String text = new String(bos.toByteArray(),"gbk");
		//	String text = new String(bos.toByteArray());
		//	System.out.println("text:"+text);
			return text;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
