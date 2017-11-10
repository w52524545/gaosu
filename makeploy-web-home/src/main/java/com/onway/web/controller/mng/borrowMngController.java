package com.onway.web.controller.mng;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.makeploy.common.dal.dataobject.BorrowMoneyDO;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;
@Controller
public class borrowMngController extends AbstractController{

@RequestMapping("borrowMng.html")
public String  searchUserInfo( HttpServletRequest request,ModelMap map){
	String userId = (String)request.getSession().getAttribute("userId");
	boolean checkRoleBoss = checkRoleBoss(userId);
	if(checkRoleBoss == false){
		return "mng/loginMng";
	}
	
	//判断是否为运营者
    int checkOperative = checkOperative(userId);
    map.put("checkOperative", checkOperative);
	
	String state=request.getParameter("state");
	int totalItems=(int)borrowMoneyDao.selectInfoCount(state);
	Map<String, Integer> pageData=getPageData(request);
	List<BorrowMoneyDO> list=borrowMoneyDao.selectInfoByState(state, pageData.get(OFFSET), pageData.get(PAGE_SIZE_STR)/2);
	 map.put("totalPages", calculatePage(totalItems, pageData.get(PAGE_SIZE_STR)/2));
      map.put(CURRENTPAGE, pageData.get(CURRENTPAGE));
      map.put("totalItems", totalItems);
      map.put("borrowInfoList",list);
      map.put("activeType",state);
      request.setAttribute("page", map);
	return "mng/borrowMng";
}





 // 修改申请状态
@RequestMapping("/borrowModify.do")
@ResponseBody   
public Object modifyUserInfo(HttpServletRequest request){
	JsonResult jsonResult = new JsonResult(true);
	String borrowId = request.getParameter("borrowId");
	String state = request.getParameter("state");    //下拉菜单获取到的值
	try {
		if(borrowId == null || borrowId == ""){
			return "mng/borrowMng";
		}
		borrowMoneyDao.modifyState(state, borrowId);
		jsonResult.setBizSucc(true);   	//设置返回值为true
	} catch (Exception e) {
		e.printStackTrace();
	}
	return jsonResult;
}

}
