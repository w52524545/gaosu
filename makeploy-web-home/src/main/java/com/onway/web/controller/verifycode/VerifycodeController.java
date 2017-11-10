package com.onway.web.controller.verifycode;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.makeploy.common.dal.dataobject.VerifyCodeDO;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.VerifycodeResult;
import com.onway.web.controller.template.ControllerCallBack;

/**
 * �ҵĶ�ά��
 * @author Administrator
 *
 */
@Controller
public class VerifycodeController extends AbstractController{

	/**
	 * ��ȡ�ҵĶ�ά����Ϣ
	 * @param  userId   �û�Id
	 */
	@RequestMapping("/queryMyVerifycode.do")
	@ResponseBody
	public Object queryMyVerifycode(final HttpServletRequest request) {
		
		final String userId = "13252";//request.getParameter("userId");
		
		final  VerifycodeResult result = new  VerifycodeResult(true, "", "");
		controllerTemplate.execute(result, new ControllerCallBack() {
			@Override
			public void check() {
				AssertUtil.notBlank(userId, "�û�IdΪ��");
			}

			@Override
			public void executeService() {
				// �����û�Id��ѯ��ά����Ϣ
				VerifyCodeDO verifyCodeDO = verifyCodeDAO.selectVerifyCodeInfoByUserId(userId);
				
				if (null == verifyCodeDO) {
					result.setErrMsg("��ά����Ϣ��ѯʧ�ܣ�û�и�����¼");
					result.setBizSucc(false);
				}
				result.setVerifyCodeDO(verifyCodeDO);
			}
		});
		return result;
	}
}
