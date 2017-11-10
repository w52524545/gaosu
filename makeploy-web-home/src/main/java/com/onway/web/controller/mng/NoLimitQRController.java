package com.onway.web.controller.mng;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.onway.common.lang.HttpUtils;
import com.onway.common.lang.StringUtils;
import com.onway.makeploy.common.dal.dataobject.SysConfigDO;
import com.onway.makeploy.common.dal.dataobject.UserDO;
import com.onway.makeploy.core.localcache.enums.SysConfigCacheKeyEnum;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;
import com.onway.web.controller.template.ControllerCallBack;

@Controller
public class NoLimitQRController extends AbstractController{
	
	@RequestMapping("noLimitQr.html")
    public String gonoLimitQr(HttpServletRequest request, ModelMap modelMap) {
		
		String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//�ж��Ƿ�Ϊ��Ӫ��
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
    	
    	return "mng/noLimitQr";
    }
	
	@RequestMapping("/noLimitQr.do")
	@ResponseBody
	public Object noLimitQr(final HttpServletRequest request,
			final ModelMap modelMap) {

		final String cell = request.getParameter("cell");

		final JsonResult result = new JsonResult(false);

		controllerTemplate.execute(result, new ControllerCallBack() {

			@Override
			public void executeService() {
				try {
					UserDO userDO = userDao.selectUserInfoByCell(cell);
					
					if(null != userDO){
						SysConfigDO sysConfigDO = sysConfigDAO.selectByKey(SysConfigCacheKeyEnum.ACCESS_TOKEN.toString());
						String accessToken = sysConfigDO.getSysValue();
//						String accessToken = sysConfigCacheManager
//								.getConfigValue(SysConfigCacheKeyEnum.ACCESS_TOKEN);
						// �����ȡtoken
						// String accessToken = makeployTairManager.getToken("");
						if (StringUtils.isBlank(accessToken)) {
							// return new ModelAndView("index");
						}
						logger.info(MessageFormat.format(
								"��ȡ��ά����Ȩ��Ϣ! accessToken:{0}",
								new Object[]{accessToken}));
	
						String promoteId = userDO.getPromoteId() + "";
						String createUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="
								+ accessToken;
//						{"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "123"}}}
//						{"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_id": 123}}}
//						String params = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\":"
//								+ promoteId + "}}}";
						String params = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\":"
								+ promoteId + "}}}";
						String imgResultStr = HttpUtils.executePostJsonMethod(
								createUrl, "utf-8", params);
						logger.info(MessageFormat.format(
								"��ȡ��ά����Ϣ! imgResultStr:{0}, promoteId:{1}",
								new Object[]{imgResultStr, promoteId}));
						ImgInfo imgInfo = JSON.parseObject(imgResultStr,
								ImgInfo.class);
	
						String getImgUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="
								+ imgInfo.getTicket();
						
						if(StringUtils.isEmpty(imgInfo.getTicket())){
							result.setBizSucc(false);
							result.setErrMsg("��ȡticketʧ��");
						}else{
							result.setBizSucc(true);
							result.setInformation(getImgUrl);
							
							int qrResult = userDao.updateUserQR(getImgUrl, userDO.getUserId());
							if(qrResult <=0 ){
								result.setBizSucc(false);
								result.setErrMsg("�޸�ʧ��");
							}
						}
					}else{
						result.setBizSucc(false);
						result.setErrMsg("���ɶ�ά��ʧ�ܣ��û���Ϣ������");
					}
					
				} catch (Exception e) {
					logger.error("", e);
				}

			}

			@Override
			public void check() {

			}
		});

		return result;
	}

	
	 /**
     * ��ѯ�û��Ƿ����  ����ũ�徭����
     * @param request
     * @return
     */
    @RequestMapping("/selectUserQR.do")
    @ResponseBody
    public Object selectUserQR(HttpServletRequest request) {
        String cell = getParameterCheck(request, "cell");
        UserDO userdo = userDao.selectUserInfoByCell(cell);
        if (null != userdo) {
                return userdo;
        } else {
            return "0";
        }
    }
    
    /**
     * ������߼��û�
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("hightest.html")
    public String hightest(HttpServletRequest request, ModelMap modelMap) {
		
		String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//�ж��Ƿ�Ϊ��Ӫ��
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
    	return "mng/hightest";
    }
	
	@RequestMapping("/hightest.do")
	@ResponseBody
	public Object sethightest(final HttpServletRequest request,
			final ModelMap modelMap) {

		final String cell = request.getParameter("cell");

		final JsonResult result = new JsonResult(false);

		controllerTemplate.execute(result, new ControllerCallBack() {

			@Override
			public void executeService() {
				try {
					UserDO userDO = userDao.selectUserInfoByCell(cell);
					
					if(null != userDO){
						String userId = userDO.getUserId();
						int i = userDao.sethightestUser(null, userId);
						if(i>=1){
							result.setBizSucc(true);
							result.setErrMsg("�����ɹ�");
						}
						
					}else{
						result.setBizSucc(false);
						result.setErrMsg("�û���Ϣ������");
					}
					
				} catch (Exception e) {
					logger.error("", e);
				}

			}

			@Override
			public void check() {

			}
		});

		return result;
	}
}
