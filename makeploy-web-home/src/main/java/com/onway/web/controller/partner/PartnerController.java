package com.onway.web.controller.partner;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onway.web.controller.AbstractController;

/**
 * ��ת��ͨũ�徭����ҳ��
 * @author Administrator
 *
 */
@Controller
public class PartnerController extends AbstractController{

	@RequestMapping("partner.html")
	public Object partner(final HttpServletRequest request,final ModelMap modelMap){
		
		return "html/partner";
	}
}
