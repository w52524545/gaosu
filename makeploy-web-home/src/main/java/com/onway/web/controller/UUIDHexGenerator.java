package com.onway.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UUIDHexGenerator {
	private static long num = 0l;    
    private static String date ;    
    
    /**  
     * 编号  
     * @return  
     */    
    public static synchronized String getNum() {    
        String str = new SimpleDateFormat("yyMMddHHmmss").format(new Date());    
        if(date==null||!date.equals(str)){    
            date = str;    
            num  = 0l;    
        }    
        num ++;    
        long orderNo = Long.parseLong((date)) * 10000;    
        orderNo += num;;    
        return orderNo+"";    
    }    
    
    /**  
     * 编号  
     * @return  
     */    
    public static synchronized String getProNo() {    
        String str = new SimpleDateFormat("yyMMddHHmm").format(new Date());    
        if(date==null||!date.equals(str)){    
            date = str;    
            num  = 0l;    
        }    
        num ++;    
        long orderNo = Long.parseLong((date)) * 10000;    
        orderNo += num;;    
        return "22"+orderNo+"";
    }  
    
    /**  
     * 编号  
     * @return  
     */    
    public static synchronized String getAccountNo() {    
        String str = new SimpleDateFormat("yyMMddHHmm").format(new Date());    
        if(date==null||!date.equals(str)){    
            date = str;    
            num  = 0l;    
        }    
        num ++;    
        long orderNo = Long.parseLong((date)) * 10000;    
        orderNo += num;;    
        return "11"+orderNo+"";
    }  
    
    /**
     * 手动录入用户Id
     * @return
     */
    public static synchronized String getHandUserId() {    
        String str = new SimpleDateFormat("yyMMddHHmm").format(new Date());    
        if(date==null||!date.equals(str)){    
            date = str;    
            num  = 0l;    
        }    
        num ++;    
        long orderNo = Long.parseLong((date)) * 10000;    
        orderNo += num;;    
        return "66"+orderNo+"";
    }  
    
    public static synchronized String getOrderNo() {    
        String str = new SimpleDateFormat("yyMMddHHmm").format(new Date());    
        if(date==null||!date.equals(str)){    
            date = str;    
            num  = 0l;    
        }    
        num ++;    
        long orderNo = Long.parseLong((date)) * 10000;    
        orderNo += num;;    
        return "44"+orderNo+"";    
    }  

}
