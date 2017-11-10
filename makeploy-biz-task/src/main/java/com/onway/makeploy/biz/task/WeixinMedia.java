package com.onway.makeploy.biz.task;


/**  
*   
* ��Ŀ��ƣ�wechatapi  
* ����ƣ�WeixinMedia  
* ��������ý���ļ���Ϣ  
* �����ˣ�Myna Wang  
* ����ʱ��??014-3-8 ����6:19:39  
* @version       
*/
public class WeixinMedia {
	// ý������
	private String type;
	// ý���ļ���ʶ������ͼ��ý���ļ���??
	private String mediaId;
	// ý���ļ��ϴ���ʱ??
	private int createdAt;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getMediaId() {
		return mediaId;
	}
	
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	public int getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(int createdAt) {
		this.createdAt = createdAt;
	}
}
