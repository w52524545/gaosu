package com.onway.web.controller.msg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onway.common.lang.StringUtils;
import com.onway.makeploy.common.dal.dataobject.MsgDO;
import com.onway.makeploy.common.service.exception.ErrorException;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.MyMsgResult;

/**
 * 我的消息
 * @author Administrator
 *
 */
@Controller
public class MsgController extends AbstractController{

	
	/**
	 * 获取我的消息列表
	 * 
	 * @param request
	 * @param userId  用户Id
	 * @return
	 */
	@RequestMapping("/message.html")
	public Object message(final HttpServletRequest request,final ModelMap modelMap) {
		// String userId = (String) request.getSession().getAttribute("userId");
		final String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("用户未登录");
		}
		
		final Integer pageNum = adjustPageNo(request);
		final Integer pageSize = adjustPageSize(request);
		
		// 分页未考虑
		List<MsgDO> msgList=msgDao.selectMsgInfoByUserId(userId, null, null);

		List<MyMsgResult> list = new ArrayList<MyMsgResult>();
		MyMsgResult myMsgResult = null;
		for (MsgDO msgDO : msgList) {
			myMsgResult = new MyMsgResult(true, "", "");
			
			//查询发信者name
			String sUserName=userDao.selectUserInfoByUserId(msgDO.getSUserId()).getUserName();
			
			myMsgResult.setSUserName(sUserName);
			myMsgResult.setMsg(msgDO.getMsg());
			myMsgResult.setSendTime(msgDO.getSendTime());
			
			
			list.add(myMsgResult);
		}
		modelMap.put("message", list);
		return "html/message";
	}
}
