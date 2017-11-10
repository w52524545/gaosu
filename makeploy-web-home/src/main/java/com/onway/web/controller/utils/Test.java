package com.onway.web.controller.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.onway.common.lang.Money;
import com.onway.makeploy.common.dal.dataobject.CategoryDO;
import com.onway.makeploy.common.dal.dataobject.ProductDO;
import com.onway.makeploy.common.dal.ibatis.IbatisCategoryDAO;
import com.onway.makeploy.common.dal.ibatis.IbatisProductDAO;
import com.onway.makeploy.common.service.exception.ErrorException;

public class Test {

	public static void main(String[] args) {
//		double oneStar=Integer.valueOf("4");
//		double twoStar=Integer.valueOf("3");
//		double threeStar=Integer.valueOf("1");
//		double shopStar=(oneStar+twoStar+threeStar)/3;
//		Math.ceil(shopStar);
//		Integer.valueOf((int) Math.ceil(shopStar));
//		System.out.println(">>>>>>>>>>>>>>>>>"+shopStar);
//		System.out.println(">>>>>>>>>>>>>>>>>"+Math.ceil(shopStar));
//		System.out.println(">>>>>>>>>>>>>>>>>"+Integer.valueOf((int) Math.ceil(shopStar)));
		
//		IbatisCategoryDAO dao=new IbatisCategoryDAO();
//		List<CategoryDO> father = dao.selectAllFather();
//		for (CategoryDO categoryDO : father) {
//			categoryDO.getFatherName();
//			System.out.println(">>>>>>>>>>>>>>>>>>>"+categoryDO.getFatherName());
//		}
//		String a="152325,132132";
//		String[] b = a.split(",");
//		System.out.println(b.length);
//		for (int i = 0; i < b.length; i++) { 
//    	    String c=b[i];
//            System.out.println("<<<<<<<<<<<<<"+c);
//    	}
//		
		Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
        calendar.add(Calendar.DATE, 0);    //得到当天
        String  dayAgo= new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        System.out.println(dayAgo);
	}
	
	
}
