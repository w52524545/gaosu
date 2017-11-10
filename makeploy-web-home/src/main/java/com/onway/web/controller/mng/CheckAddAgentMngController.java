package com.onway.web.controller.mng;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.makeploy.common.dal.dataobject.AgentDO;
import com.onway.makeploy.common.dal.dataobject.RoleDO;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;

/**
 * @author jianyong.jiang
 * �����������
 *
 */
@Controller
public class CheckAddAgentMngController extends AbstractController {
	
	@RequestMapping("/addAgent.html")
	public String addAgent(HttpServletRequest request,ModelMap modelMap){
		String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	
    	//�ж��Ƿ�Ϊ��Ӫ��
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
		return "mng/addAgent";
	}

	@RequestMapping("addAgent.do")
	@ResponseBody
	public Object addAgentDo(HttpServletRequest request,ModelMap modelMap){
		JsonResult result= new JsonResult(false);
		String cell=request.getParameter("cell");
		String province= request.getParameter("province");
		String city    = request.getParameter("city");
		String district= request.getParameter("district");
		String userId;
		String userName;
		if(null==userDao.selectUserInfoByCell(cell)){//�ж��û��ֻ��Ƿ��Ѱ�
			result.setBizSucc(false);
			result.setInformation("���û��ֻ���δ����Ϣ");
		}else{
		if(null==agentDao.selectAll(null, cell, null, null, null)){//�ж��û��Ƿ��Ѿ��Ǵ���
			

		AgentDO agentDo=agentDao.selectAll(null, null, province, city, district);//�жϵ����Ƿ񱻴���
		if(null==agentDo){
			userId=userDao.selectUserInfoByCell(cell).getUserId();
			userName=userDao.selectUserInfoByCell(cell).getUserName();
			AgentDO agent=new AgentDO();
			agent.setProvince(province);
			agent.setCity(city);
			agent.setDistrict(district);
			agent.setUserName(userName);
			agent.setCell(cell);
			agent.setUserId(userId);
			agentDao.addAgent(agent);
			RoleDO role= new RoleDO();
			role.setUserId(userId);
			role.setRole("2");
			roleDao.addRole(role);
			result.setBizSucc(true);
			result.setInformation("���õ�������ɹ���");
		}else{
			result.setInformation("�õ����ѱ�����");
		}
		}else{
			result.setBizSucc(false);
			result.setInformation("���û��Ѿ��ǵ�������");
		}
		}
		return result;
	}
//	@RequestMapping("checkCell.do")
//	@ResponseBody
//	public Object checkCell(HttpServletRequest request,ModelMap modelMap){
//		JsonResult result= new JsonResult(true);
//		String cell=request.getParameter("cell");
//		if(null==userDao.selectUserInfoByCell(cell)){
//			result.setBizSucc(false);
//		}
//		return result;
//	}
}
