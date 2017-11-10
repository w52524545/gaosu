package com.onway.web.controller.sendMessage;


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
* 项目名称：wechatapi  
* 类名称：MediaUtil  
* 类描述：上传下载多媒体文件工?? 
* 创建人：Myna Wang  
* 创建时间??014-3-8 下午7:55:34  
* @version       
*/
public class MediaUtil{
    
    // 上传多媒体文件url
    protected final static String UPLOAD_MEDIA_URL="http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	/**
	 * 上传多媒体文??
	 * 
	 * @param accessToken 调用接口凭证
	 * @param type 媒体文件类型，分别有图片（image）??语音（voice）??视频（video）和缩略图（thumb??
	 * @param mediaFileUrl 媒体文件url(如：localhost:8080/test/upload/music.mp3)
	 * @return WeixinMedia
	 */
	public static WeixinMedia uploadMedia(String accessToken,String type,String mediaFileUrl) {
		WeixinMedia weixinMedia=null;
		String uploadMediaUrl=UPLOAD_MEDIA_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
		
		//定义数据分隔??
		String boundary="------------7da2e536604c8";
		try {
			URL uploadUrl=new URL(uploadMediaUrl);
			HttpURLConnection uploadConn=(HttpURLConnection)uploadUrl.openConnection();
			uploadConn.setDoOutput(true);
			uploadConn.setDoInput(true);
			uploadConn.setRequestMethod("POST");
			// 设置请求头Content-Type
			uploadConn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
			// 获取媒体文件上传的输出流（往微信服务器写数据??
			OutputStream outputStream=uploadConn.getOutputStream();
			
			URL mediaUrl=new URL(mediaFileUrl);
			HttpURLConnection mediaConn=(HttpURLConnection) mediaUrl.openConnection();
			mediaConn.setDoOutput(true);
			mediaConn.setRequestMethod("GET");
			
			// 从请求头获取内容类型
			String contentType=mediaConn.getHeaderField("Content-Type");
			// 根据内容类型判断文件扩展??
			String fileExt=getFileExt(contentType);
			// 请求体开??
			outputStream.write(("--"+boundary+"\r\n").getBytes());
			outputStream.write(String.format("Content-Disposition: form-data; name=\"media\";filename=\"file1%s\"\r\n", fileExt).getBytes());
			outputStream.write(String.format("Content-Type: %s\r\n\r\n", contentType).getBytes());
			
			// 获取媒体文件的输入流(读取文件)
			BufferedInputStream bis=new BufferedInputStream(mediaConn.getInputStream());
			byte[] buf=new byte[8096];
			int size=0;
			while((size=bis.read(buf))!=-1){
				// 将媒体文件写到输出流（往微信服务器写数据??
				outputStream.write(buf,0,size);
			}
			
			// 请求体结??
			outputStream.write(("\r\n--"+boundary+"--\r\n").getBytes());
			outputStream.close();
			bis.close();
			mediaConn.disconnect();
			
			// 获取媒体文件上传的输入流（从微信服务器读数据??
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
			// 释放资源
			inputStream.close();
			inputStream=null;
			uploadConn.disconnect();
			
			// 使用JSON-lib解析返回结果
			JSONObject jsonObject=JSONObject.parseObject(buffer.toString());
			System.err.println(jsonObject);
			weixinMedia=new WeixinMedia();
			weixinMedia.setType(jsonObject.getString("type"));
			// type等于thumb时的返回结果和其他类型不????
			if ("thumb".equals(type)) {
				weixinMedia.setMediaId(jsonObject.getString("thumb_media_id"));
			}else {
				weixinMedia.setMediaId(jsonObject.getString("media_id"));
				weixinMedia.setCreatedAt(jsonObject.getInteger("created_at"));
			}
		} catch (Exception e) {
			weixinMedia=null;
			System.out.println("上传媒体文件失败:{}"+e);
		}
		return weixinMedia;
	}
	
	/**
     * 根据类型判断文件扩展名
     *
     * @param contentType 内容类型
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
