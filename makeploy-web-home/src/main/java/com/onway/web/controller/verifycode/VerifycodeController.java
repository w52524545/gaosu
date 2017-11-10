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
 * 我的二维码
 * @author Administrator
 *
 */
@Controller
public class VerifycodeController extends AbstractController{

	/**
	 * 获取我的二维码信息
	 * @param  userId   用户Id
	 */
	@RequestMapping("/queryMyVerifycode.do")
	@ResponseBody
	public Object queryMyVerifycode(final HttpServletRequest request) {
		
		final String userId = "13252";//request.getParameter("userId");
		
		final  VerifycodeResult result = new  VerifycodeResult(true, "", "");
		controllerTemplate.execute(result, new ControllerCallBack() {
			@Override
			public void check() {
				AssertUtil.notBlank(userId, "用户Id为空");
			}

			@Override
			public void executeService() {
				// 根据用户Id查询二维码信息
				VerifyCodeDO verifyCodeDO = verifyCodeDAO.selectVerifyCodeInfoByUserId(userId);
				
				if (null == verifyCodeDO) {
					result.setErrMsg("二维码信息查询失败，没有该条记录");
					result.setBizSucc(false);
				}
				result.setVerifyCodeDO(verifyCodeDO);
			}
		});
		return result;
	}
}
