package com.onway.web.controller.mng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

public class GetLatAndLngByBaidu {
	
	/** 
	* @param addr 
	* ��ѯ�ĵ�ַ 
	* @return 
	* @throws IOException 
	*/ 
	public Object[] getCoordinate(String addr) throws IOException { 
		String lng = null;//����
		String lat = null;//γ��
		String address = null;
		try { 
			address = java.net.URLEncoder.encode(addr, "UTF-8"); 
		}catch (UnsupportedEncodingException e1) { 
			e1.printStackTrace(); 
		} 
		String key = "MsN1WtQmTst4LRPqICEcGF1y8Pz3iOoX";
		String url = String .format("http://api.map.baidu.com/geocoder/v2/?address=%s&output=json&ak=%s", address, key);
//		String url = String .format("http://api.map.baidu.com/geocoder?address=%s&output=json&key=%s", address, key); 
		URL myURL = null; 
		URLConnection httpsConn = null; 
		try { 
			myURL = new URL(url); 
		} catch (MalformedURLException e) { 
			e.printStackTrace(); 
		} 
		InputStreamReader insr = null;
		BufferedReader br = null;
		try { 
			httpsConn = (URLConnection) myURL.openConnection();// ��ʹ�ô��� 
			if (httpsConn != null) { 
				insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8"); 
				br = new BufferedReader(insr); 
				String data = null;
				if ((data = br.readLine()) != null) {
                    lat = data.substring(data.indexOf("\"lat\":") 
                    + ("\"lat\":").length(), data.indexOf("},\"precise\""));
                    lng = data.substring(data.indexOf("\"lng\":") 
                    + ("\"lng\":").length(), data.indexOf(",\"lat\""));
                }
			} 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} finally {
			if(insr!=null){
				insr.close();
			}
			if(br!=null){
				br.close();
			}
		}
		return new Object[]{lng,lat}; 
	} 
	
	/**
	 * longitude and latitude;
	 * lng            lat
	 *  ��     ��                  γ   ��
	 * @return
	 */  
    public String getDistance(Double longitude,Double latitude,Double addressX, Double addressY){  
        double lon1 = (Math.PI/180)*longitude;  
        double lon2 = (Math.PI/180)*addressX;//����  
  
        double lat1 = (Math.PI/180)*latitude;  
        double lat2 = (Math.PI/180)*addressY;//ά��  
  
        //����뾶  
        double R = 6371;  
  
        //�������� km�������Ҫ�׵Ļ������*1000�Ϳ�����  
        double d =  Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*R;  
//        d=d*1000;
        DecimalFormat df=new DecimalFormat("0.00");  
        return  df.format(d);  
    }  

	/**
	 *  ��γ�ȼ���
	 *  �ɶ����츮�Ľ�
	 *  ���ݱ���  120.19237 30.187588  
	 * @param args
	 * @throws IOException
	 */
//	public static void main(String[] args) throws IOException {
//		GetLatAndLngByBaidu getLatAndLngByBaidu = new GetLatAndLngByBaidu();
//		Object[] o = getLatAndLngByBaidu.getCoordinate("�㽭ʡ���ж����к�������88��");
//		System.out.println(o[0]);//����
//		System.out.println(o[1]);//γ��
//		
//		double o0 = Double.parseDouble(o[0].toString());
//		double o1 = Double.parseDouble(o[1].toString());
//		
//		Object[] q = getLatAndLngByBaidu.getCoordinate("�³�");
//		System.out.println(q[0]);//����
//		System.out.println(q[1]);//γ��
//		double q0 = Double.parseDouble(q[0].toString());
//		double q1 = Double.parseDouble(q[1].toString());
//		
//		String distance = getDistance(o0,o1,q0,q1);
//		System.out.println(distance);
//	}
	
}
