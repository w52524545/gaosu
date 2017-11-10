package com.onway.web.controller.mng;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.onway.common.lang.Money;
import com.onway.makeploy.common.dal.dataobject.AreaDO;
import com.onway.makeploy.common.dal.dataobject.CarrymodeDO;
import com.onway.makeploy.common.dal.dataobject.FaretemplateDO;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.UUIDHexGenerator;
import com.onway.web.controller.result.JsonResult;
import com.onway.web.controller.template.ControllerCallBack;

@Controller
public class Faretemplate extends AbstractController {

	@RequestMapping("/faretemplate.html")
	public String faretemplate(HttpServletRequest request, ModelMap modelMap) {
		String shopId = (String)request.getSession().getAttribute("shopId");
		List<FaretemplateDO> faretemplateList = faretemplateDAO.selectAllFaretemplate(shopId);
		List<CarrymodeDO> carryModeList = new ArrayList<CarrymodeDO>(); 
		for (FaretemplateDO faretemplateDO : faretemplateList) {
			String templateId = faretemplateDO.getTemplateId();
			CarrymodeDO carryMode = carrymodeDAO.selectAllCarryModeByTemplateId(templateId);
			carryModeList.add(carryMode);
		}
		modelMap.put("carryModeList", carryModeList);
		modelMap.put("faretemplateList", faretemplateList);
		return "mng/faretemplate";
	}
	
	@RequestMapping("/addFaretemplate.html")
	public String addFaretemplate(HttpServletRequest request, ModelMap modelMap) {
		
		List<AreaDO> areaList = areaDAO.selectAllFromArea();
		modelMap.put("areaList", areaList);

		return "mng/addFaretemplate";
	}
	
	/**
	 * 添加运费模板//平邮
			 String post21 = request.getParameter("post21");
			 String post22 = request.getParameter("post22");
			 String post23 = request.getParameter("post23");
			 String post24 = request.getParameter("post24");
			 //EMS
			 String post31 = request.getParameter("post31");
			 String post32 = request.getParameter("post32");
			 String post33 = request.getParameter("post33");
			 String post34 = request.getParameter("post34");
			 
			 //平邮
			 String post51 = request.getParameter("post51");
			 String post52 = request.getParameter("post52");
			 String post53 = request.getParameter("post53");
			 String post54 = request.getParameter("post54");
			 //EMS
			 String post61 = request.getParameter("post61");
			 String post62 = request.getParameter("post62");
			 String post63 = request.getParameter("post63");
			 String post64 = request.getParameter("post64");
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("addFaretemplate.do")
	 public ModelAndView addfaretemplate(HttpServletRequest request, ModelMap modelMap){
		 
		 String shopId = (String)request.getSession().getAttribute("shopId");
		 
		 //方案几种
//		 String countNumber = request.getParameter("countnumber");
//		 String weightnumber = request.getParameter("weightnumber");
		 //模板名称
		 String templateName = request.getParameter("templateName");
		 //发货地址
		 String province = request.getParameter("province");
		 String city = request.getParameter("city");
		 String district = request.getParameter("district");
		 
		 String goodsAddress = province+"-"+city+"-"+district;
		 
		 String templateId = UUIDHexGenerator.getNum();
		 int result = 0;
		 //是否包邮
		 String postFree = request.getParameter("postFree");
		 if(!StringUtils.equals(postFree, "包邮")){
			 //不包邮后记算方式
			 String postWay = request.getParameter("postWay");
			 String areaChina = request.getParameter("chinaArea");
			 
			 FaretemplateDO faretemplateDO = new FaretemplateDO();
			 faretemplateDO.setTemplateId(templateId);
			 faretemplateDO.setShopId(shopId);
			 faretemplateDO.setTemplateName(templateName);
			 faretemplateDO.setGoodsAddress(goodsAddress);
			 faretemplateDO.setFreePost("2");//包邮
			 faretemplateDO.setDelFlag("2");
			 
			 CarrymodeDO carrymodeDO = new CarrymodeDO();
			 
			 if(StringUtils.equals(postWay, "按重量")){
				 faretemplateDO.setValuationWay("2");
				 //记重
				 //快递
				 String post11 = request.getParameter("post11");
				 String post12 = request.getParameter("post12");
				 String post13 = request.getParameter("post13");
				 String post14 = request.getParameter("post14");
				 
				 carrymodeDO.setTemplateId(templateId);
				 carrymodeDO.setSendAddress(areaChina);
				 carrymodeDO.setFirstWeight(Double.valueOf(post11));
				 carrymodeDO.setFirstMoney(new Money(post12));
				 carrymodeDO.setNextWeight(Double.valueOf(post13));
				 carrymodeDO.setNextMoney(new Money(post14));
				 
			 }
			 if(StringUtils.equals(postWay, "按件数")){
				 faretemplateDO.setValuationWay("1");
				 //记件
				 //快递
				 String post41 = request.getParameter("post41");
				 String post42 = request.getParameter("post42");
				 String post43 = request.getParameter("post43");
				 String post44 = request.getParameter("post44");
				 
				 carrymodeDO.setTemplateId(templateId);
				 carrymodeDO.setSendAddress(areaChina);
				 carrymodeDO.setFirstCount(Integer.valueOf(post41));
				 carrymodeDO.setFirstMoney(new Money(post42));
				 carrymodeDO.setNextCount(Integer.valueOf(post43));
				 carrymodeDO.setNextMoney(new Money(post44));
			 }
			 if(StringUtils.equals(postWay, "几件包邮")){
				 faretemplateDO.setValuationWay("4");
				 //快递
				 String post71 = request.getParameter("post71");
				 String post72 = request.getParameter("post72");
				 String post73 = request.getParameter("post73");
				 String post74 = request.getParameter("post74");
				 String post75 = request.getParameter("post75");
				 
				 carrymodeDO.setTemplateId(templateId);
				 carrymodeDO.setSendAddress(areaChina);
				 carrymodeDO.setFirstCount(Integer.valueOf(post71));
				 carrymodeDO.setFirstMoney(new Money(post72));
				 carrymodeDO.setNextCount(Integer.valueOf(post73));
				 carrymodeDO.setNextMoney(new Money(post74));
				 carrymodeDO.setMemo(post75);
			 }
			 carrymodeDO.setPostWay("1");
			 carrymodeDO.setDelFlag("2");
			 
			 result = carrymodeDAO.addNewCarryMode(carrymodeDO);
			 result = faretemplateDAO.addNewFaretemplate(faretemplateDO);
			 
		 }else{
			 //包邮
			 FaretemplateDO faretemplateDO = new FaretemplateDO();
			 faretemplateDO.setTemplateId(templateId);
			 faretemplateDO.setShopId(shopId);
			 faretemplateDO.setTemplateName(templateName);
			 faretemplateDO.setGoodsAddress(goodsAddress);
			 faretemplateDO.setFreePost("1");//包邮
			 faretemplateDO.setDelFlag("2");
			 
			 result = faretemplateDAO.addNewFaretemplate(faretemplateDO);
		 }
		 modelMap.put("result", result);
		 return new ModelAndView("mng/addsuccess");
	 }
	
	@RequestMapping("/deleteTemplate.do")
	@ResponseBody
	public Object deleteTemplate(HttpServletRequest request) {
		final String shopId = (String)request.getSession().getAttribute("shopId");
		final String templateId = request.getParameter("templateId");

		final JsonResult jsonResult = new JsonResult(false);
		controllerTemplate.execute(jsonResult, new ControllerCallBack() {

			@Override
			public void executeService() {
				int result = 0;
				result+= faretemplateDAO.changeDelFlg("1", shopId, templateId);
				result+= carrymodeDAO.changeDelFlg("1", templateId);
				
				if(result > 0){
					jsonResult.setBizSucc(true);
				}
			}

			@Override
			public void check() {
			}
		});
		return jsonResult;
	}
}
