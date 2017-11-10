package com.onway.web.controller.mng;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.makeploy.common.dal.dataobject.UserDO;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;

@Controller
public class updatePasswordController extends AbstractController {

		@RequestMapping("/updatePassword.html")
		public String updatepassword(){
			
			return "mng/updatePassword";
		}
		
		@RequestMapping("/updatepassword.do")
		@ResponseBody
		public Object update(HttpServletRequest request, ModelMap map){
			JsonResult result= new JsonResult(false);
			String cell=request.getParameter("cell");
			String oldpass=request.getParameter("oldpassword");
			String newpass=request.getParameter("newpassword");
			UserDO user=userDao.selectUserInfoByCell(cell);
			if(null==user){
				result.setErrMsg("手机号码输入有误");
				return result;
			}else if(user.getPassWord().equals(oldpass)){
				userDao.updatePass(newpass,cell);
				result.setBizSucc(true);
				return result;
			}else{
				result.setErrMsg("原密码输入有误!");
				return result;
			}
			
		}
}
