package com.onway.makeploy.biz.task;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
*   
* ��Ŀ��ƣ�wechatapi
* ����ƣ�WeixinUtil  
* ��������΢�Ż�?? 
* �����ˣ�Myna Wang  
* ����ʱ��??014-1-18 ����4:05:11  
* @version       
*/
public class WeixinUtil {
	// ��־�ļ�
	protected final static Logger log = LoggerFactory.getLogger(WeixinUtil.class);  
	
    // ������Ϣ���ͣ���??
    public final static String RECRIVE_TEXT = "text";  
    // ������Ϣ���ͣ�ͼ?? 
    public final static String RECRIVE_IMAGE = "image";  
    // ������Ϣ���ͣ���??
    public final static String RECRIVE_VOICE = "voice";  
    // ������Ϣ���ͣ���??
    public final static String RECRIVE_VIDEO = "video"; 
    // ������Ϣ���ͣ�����λ??  
    public final static String RECRIVE_LOCATION = "location";  
    // ������Ϣ���ͣ���??
    public final static String RECRIVE_LINK = "link";  
    // ������Ϣ���ͣ���??  
    public final static String RECRIVE_EVENT = "event";  
    
    // �ظ���Ϣ���ͣ���??
    public final static String REQUEST_TEXT = "text"; 
    // �ظ���Ϣ���ͣ�ͼ?? 
    public final static String REQUEST_IMAGE = "image"; 
    // �ظ���Ϣ���ͣ���?? 
    public final static String REQUEST_VOICE = "voice"; 
    // �ظ���Ϣ���ͣ���??
    public final static String REQUEST_VIDEO = "video"; 
    // �ظ���Ϣ���ͣ���??
    public final static String REQUEST_MUSIC = "music";  
    // �ظ���Ϣ���ͣ�ͼ??
    public final static String REQUEST_NEWS = "news";  
    
    // �¼����ͣ�subscribe(����) 
    public final static String EVENT_SUBSCRIBE = "subscribe";  
    // �¼����ͣ�unsubscribe(ȡ����) 
    public final static String EVENT_UNSUBSCRIBE = "unsubscribe";  
    // �¼����ͣ�LOCATION(�ϱ�����λ���¼�) 
    public final static String EVENT_LOCATION = "LOCATION";  
    // �¼����ͣ�CLICK(�Զ���˵������?? 
    public final static String EVENT_CLICK = "CLICK";  
   
    /**
     * ɨ�������ά���¼�
     * �û�ɨ���??��ά��ʱ�������������������¼���
		1.����û���δ��ע���ںţ����û����Թ�ע���ںţ���ע��΢�ŻὫ��??��ע�¼���??��????
   		2.����û��Ѿ���ע���ںţ���΢�ŻὫ��ֵɨ���¼����͸�????��??
    */
    // �¼����ͣ�subscribe(�û�δ��עʱ�����й�ע����¼���?? 
    public final static String EVENT_QRCODE_SUBSCRIBE = "subscribe";
    // �¼����ͣ�scan(�û��ѹ�עʱ���¼���?? 
    public final static String EVENT_QRCODE_SCAN = "scan";
	
   
	// ��ȡaccess_token�Ľӿڵ�????GET????00����/�죩  
	public final static String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";   
	// ????�߿�ͨ��OpenID����ȡ�û�����??url 
	protected final static String GET_PERSONALINF_URL="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";	
	// ͨ��OpenID��ȡ��ѯ�û�????����url
	protected final static String GET_PERSONGROUPID_URL="https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=ACCESS_TOKEN";
	/**
	 *  OAuth2.0���ע�ߴ�??�û�ͬ����Ȩ����ȡcodeҳ��url
	 *  1.scope������Ϊ��snsapi_base����������Ȩҳ�棬ֱ����ת��ֻ�ܻ�ȡ�û�openid����snsapi_userinfo ��������Ȩҳ??
	 *  2.redirect_uri����Ȩ���ض���Ļص����ӵ�ַ����ʹ��urlencode�����ӽ��д�?? ������commonutil��urlEncodeUTF8()
	 */
	public final static String FANS_GET_CODE="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	// OAuth2.0ͨ��code��ȡ��ҳ��Ȩaccess_token
	protected final static String OAUTH2_ACCESSTOKEN_URL="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	// OAuth2.0ˢ��access_token
	protected final static String REFRESH_ACCESSTOKEN_URL="https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
	// OAuth2.0��ȡ�û���Ϣ(??cope??snsapi_userinfo)
	protected final static String OAUTH2_USERINFO_URL="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	// ������??�ͷ���Ϣurl
	protected final static String SEND_CUSTOM_URL="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	// �����ʱ��ά��url
	protected final static String TEMPORARY_QRCODE_URL="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
	// ������ö�ά��url
	protected final static String PERMANENT_QRCODE_URL="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
	// ��ȡ��ά��url
	protected final static String GET_QRCODE_URL="https://mp.weixin.qq.com/cgi-bin/shoMyna Wangrcode?ticket=TICKET";
	// ��ȡ��ע���б�url
	protected final static String GET_USERLIST_URL="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	
	// ��ȡ????������Ϣurl
	protected final static String GET_GROUPS_URL="https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
	// ��������url
	protected final static String CREATE_GROUPS_URL="https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
	// �޸ķ���url
	protected final static String UPDATE_GROUPS_URL="https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";
	// �ƶ��û�����url
	protected final static String REMOVE_MEMBER_URL="https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";

	// �ϴ���ý���ļ�url
	protected final static String UPLOAD_MEDIA_URL="http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	// ���ض�ý���ļ�url
	protected final static String DOWNLOAD_MEDIA_URL="http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	// �˵�������POST????00����/�죩  
	protected final static String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";  
	// �˵���ѯ��GET??
	protected final static String GET_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	// �˵�ɾ��GET??
	protected final static String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";


}
