package com.onway.makeploy.biz.task;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSONObject;

/**  
*   
* ��Ŀ��ƣ�wechatapi  
* ����ƣ�MediaUtil  
* ���������ϴ����ض�ý���ļ���?? 
* �����ˣ�Myna Wang  
* ����ʱ��??014-3-8 ����7:55:34  
* @version       
*/
public class MediaUtil{
    
    // �ϴ���ý���ļ�url
    protected final static String UPLOAD_MEDIA_URL="http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	/**
	 * �ϴ���ý����??
	 * 
	 * @param accessToken ���ýӿ�ƾ֤
	 * @param type ý���ļ����ͣ��ֱ���ͼƬ��image��??������voice��??��Ƶ��video��������ͼ��thumb??
	 * @param mediaFileUrl ý���ļ�url(�磺localhost:8080/test/upload/music.mp3)
	 * @return WeixinMedia
	 */
	public static WeixinMedia uploadMedia(String accessToken,String type,String mediaFileUrl) {
		WeixinMedia weixinMedia=null;
		String uploadMediaUrl=UPLOAD_MEDIA_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
		
		//������ݷָ�??
		String boundary="------------7da2e536604c8";
		try {
			URL uploadUrl=new URL(uploadMediaUrl);
			HttpURLConnection uploadConn=(HttpURLConnection)uploadUrl.openConnection();
			uploadConn.setDoOutput(true);
			uploadConn.setDoInput(true);
			uploadConn.setRequestMethod("POST");
			// ��������ͷContent-Type
			uploadConn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
			// ��ȡý���ļ��ϴ������������΢�ŷ�����д���??
			OutputStream outputStream=uploadConn.getOutputStream();
			
			URL mediaUrl=new URL(mediaFileUrl);
			HttpURLConnection mediaConn=(HttpURLConnection) mediaUrl.openConnection();
			mediaConn.setDoOutput(true);
			mediaConn.setRequestMethod("GET");
			
			// ������ͷ��ȡ��������
			String contentType=mediaConn.getHeaderField("Content-Type");
			// ������������ж��ļ���չ??
			String fileExt=getFileExt(contentType);
			// �����忪??
			outputStream.write(("--"+boundary+"\r\n").getBytes());
			outputStream.write(String.format("Content-Disposition: form-data; name=\"media\";filename=\"file1%s\"\r\n", fileExt).getBytes());
			outputStream.write(String.format("Content-Type: %s\r\n\r\n", contentType).getBytes());
			
			// ��ȡý���ļ���������(��ȡ�ļ�)
			BufferedInputStream bis=new BufferedInputStream(mediaConn.getInputStream());
			byte[] buf=new byte[8096];
			int size=0;
			while((size=bis.read(buf))!=-1){
				// ��ý���ļ�д�����������΢�ŷ�����д���??
				outputStream.write(buf,0,size);
			}
			
			// �������??
			outputStream.write(("\r\n--"+boundary+"--\r\n").getBytes());
			outputStream.close();
			bis.close();
			mediaConn.disconnect();
			
			// ��ȡý���ļ��ϴ�������������΢�ŷ����������??
			InputStream inputStream = uploadConn.getInputStream();
			InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"utf-8");
			BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
			StringBuffer buffer=new StringBuffer();
			String str=null;
			while ((str=bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// �ͷ���Դ
			inputStream.close();
			inputStream=null;
			uploadConn.disconnect();
			
			// ʹ��JSON-lib�������ؽ��
			JSONObject jsonObject=JSONObject.parseObject(buffer.toString());
			System.err.println(jsonObject);
			weixinMedia=new WeixinMedia();
			weixinMedia.setType(jsonObject.getString("type"));
			// type����thumbʱ�ķ��ؽ����������Ͳ�????
			if ("thumb".equals(type)) {
				weixinMedia.setMediaId(jsonObject.getString("thumb_media_id"));
			}else {
				weixinMedia.setMediaId(jsonObject.getString("media_id"));
				weixinMedia.setCreatedAt(jsonObject.getInteger("created_at"));
			}
		} catch (Exception e) {
			weixinMedia=null;
			System.out.println("�ϴ�ý���ļ�ʧ��:{}"+e);
		}
		return weixinMedia;
	}
	
	/**
     * ��������ж��ļ���չ��
     *
     * @param contentType ��������
     * @return String
     */
    public static String getFileExt(String contentType) {
        String fileExt="";
        if ("image/jpeg".equals(contentType)) {
            fileExt=".jpg";
        }else if ("audio/mpeg".equals(contentType)) {
            fileExt=".mp3";
        }else if ("audio/amr".equals(contentType)) {
            fileExt=".amr";
        }else if ("video/mp4".equals(contentType)) {
            fileExt=".mp4";
        }else if ("video/mpeg4".equals(contentType)) {
            fileExt=".mp4";
        }
        return fileExt;
    }
}
