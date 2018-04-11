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
			conn.setDoOutput(true);// 设置允许输出
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type","application/json; charset=UTF-8"); // 内容类型
			OutputStream os = conn.getOutputStream();
			os.write(content.getBytes());
			os.close();
			int code = conn.getResponseCode();
            if (code == 200) {
                // 或得到输入流，此时流里面已经包含了服务端返回回来的JSON数据了,此时需要将这个流转换成字符串
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
			//将字节数组输出流转换成字节数组，然后用字节数组构造一个字符串
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
