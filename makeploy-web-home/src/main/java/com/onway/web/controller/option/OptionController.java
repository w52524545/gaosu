package com.onway.web.controller.option;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.common.lang.StringUtils;
import com.onway.makeploy.common.dal.dataobject.AddressDO;
import com.onway.makeploy.common.dal.dataobject.OptionDO;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;
import com.onway.web.controller.template.ControllerCallBack;

/**
 * 信息反馈
 * @author Administrator
 *
 */
@Controller
public class OptionController extends AbstractController{

	
	@RequestMapping("/feedback.html")
	public Object feedback(final HttpServletRequest request,final ModelMap modelMap){
		return "html/feedback";
	}
	
	/**
	 * 用户添加反馈信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOption.do")
	@ResponseBody
	public Object addOption(final HttpServletRequest request){
		// String userId = (String) request.getSession().getAttribute("userId");
		final String userId="13252";
		final String message=request.getParameter("message");
		
	
		final JsonResult jsonResult=new JsonResult(false);
		controllerTemplate.execute(jsonResult, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				
				OptionDO optionDo=new OptionDO();
				optionDo.setUserId(userId);
				optionDo.setMessage(message);
				
				int result = optionDao.addOption(optionDo);
				if( result >= 1)
				{
					jsonResult.setBizSucc(true);
				}
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(userId, "参数为空：userId");
				AssertUtil.notBlank(message, "参数为空：message");
			}
		});
		return jsonResult;	
	}
}
