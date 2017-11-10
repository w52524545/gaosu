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
 * �ҵ���Ϣ
 * @author Administrator
 *
 */
@Controller
public class MsgController extends AbstractController{

	
	/**
	 * ��ȡ�ҵ���Ϣ�б�
	 * 
	 * @param request
	 * @param userId  �û�Id
	 * @return
	 */
	@RequestMapping("/message.html")
	public Object message(final HttpServletRequest request,final ModelMap modelMap) {
		// String userId = (String) request.getSession().getAttribute("userId");
		final String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("�û�δ��¼");
		}
		
		final Integer pageNum = adjustPageNo(request);
		final Integer pageSize = adjustPageSize(request);
		
		// ��ҳδ����
		List<MsgDO> msgList=msgDao.selectMsgInfoByUserId(userId, null, null);

		List<MyMsgResult> list = new ArrayList<MyMsgResult>();
		MyMsgResult myMsgResult = null;
		for (MsgDO msgDO : msgList) {
			myMsgResult = new MyMsgResult(true, "", "");
			
			//��ѯ������name
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
