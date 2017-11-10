package com.onway.web.controller.mng;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.common.lang.StringUtils;
import com.onway.makeploy.common.dal.dataobject.ShopDO;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;

@Controller
public class GetShopLatAndLng extends AbstractController{

	
	@RequestMapping("/getShopLatAndLng.do")
	@ResponseBody
	public Object getShopLatAndLng(final HttpServletRequest request,
			final ModelMap modelMap) {
        
		JsonResult result = new JsonResult(false);
		//获取所有店铺信息
		List<ShopDO> allShopInfo = shopDAO.selectAllShopInfo();
		for (ShopDO shopDO : allShopInfo) {
			String shopId = shopDO.getShopId();
			String addr = shopDO.getShopAddr();
			if(!StringUtils.isBlank(addr)){
				GetLatAndLngByBaidu gl = new GetLatAndLngByBaidu();
				try {
					Object[] coordinate = gl.getCoordinate(addr);
					double loactionX =Double.parseDouble(coordinate[0].toString());
					double loactionY =Double.parseDouble(coordinate[1].toString());
					//更新
					shopDAO.updateShopLngAndLat(loactionX, loactionY, shopId);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		result.setBizSucc(true);
		
		return result;
	}
}
