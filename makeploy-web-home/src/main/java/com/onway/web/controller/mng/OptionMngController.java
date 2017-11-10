package com.onway.web.controller.mng;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onway.makeploy.common.dal.dataobject.OptionDO;
import com.onway.web.controller.AbstractController;

/**
 * @author southRain
 *
 */

@Controller
public class OptionMngController extends AbstractController{

//进入意见管理页面
	@RequestMapping("/optionMng.html")
	public String shopMng(HttpServletRequest request, ModelMap modelMap) {
		String userIds = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userIds);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userIds);
        modelMap.put("checkOperative", checkOperative);
		
		String userId = request.getParameter("USER_ID");
		Map<String,Integer> pageDate = getPageData(request);
		try {
			List<OptionDO> list=optionDao.selectOptionByUserId(userId, pageDate.get(OFFSET),pageDate.get(PAGE_SIZE_STR));
			int count=(int)optionDao.selectOptionCountByUserId(userId);
		modelMap.put("totalPages",calculatePage(count, pageDate.get(PAGE_SIZE_STR))); // 传输总页数
		modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));//当前页数
		modelMap.put("totalItems", count);// 传输总条数
		modelMap.put("optionList", list);
		modelMap.put("userId", userId);
		request.setAttribute("page", modelMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "mng/optionMng";
	}
	
	
}
