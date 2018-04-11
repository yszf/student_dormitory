package cn.edu.xsyu.dorm.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *  �ӷ����������ȡ��JSON���ݸ�ʽ���ַ���
 */
public class HttpUtils {

    public HttpUtils() {
    }

    public static String getJsonContent(String url_path) {
        try {
            URL url = new URL(url_path);
            System.out.println("url:"+url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000); // ����ʱʱ��3s
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            int code = connection.getResponseCode(); // ����״̬��
            System.out.println("code:"+code);
            if (code == 200) {
                // ��õ�����������ʱ�������Ѿ������˷���˷��ػ�����JSON������,��ʱ��Ҫ�������ת�����ַ���
                return changeInputStream(connection.getInputStream());
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return "error";
    }

    private static String changeInputStream(InputStream inputStream) {
        String jsonString = "";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int length = 0;
        byte[] data = new byte[1024];
        try {
            while (-1 != (length = inputStream.read(data))) {
                outputStream.write(data, 0, length);
            }
            // inputStream�������õ�����д��ByteArrayOutputStream����,
            // Ȼ��ͨ��outputStream.toByteArrayת���ֽ����飬��ͨ��new String()����һ���µ��ַ�����
        //    jsonString = new String(outputStream.toByteArray());
            jsonString = new String(outputStream.toByteArray(),"gbk");
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return jsonString;
    }
}
