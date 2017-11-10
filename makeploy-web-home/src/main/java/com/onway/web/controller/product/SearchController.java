package com.onway.web.controller.product;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onway.common.lang.StringUtils;
import com.onway.makeploy.common.dal.dataobject.ProductDO;
import com.onway.makeploy.common.dal.dataobject.ShopDO;
import com.onway.web.controller.AbstractController;

@Controller
public class SearchController extends AbstractController{

	@RequestMapping("/dosearch.html")
	public Object dosearch(final HttpServletRequest request,final ModelMap modelMap){
		
		try {
			String key=request.getParameter("key").toString();
			key= new String(key.getBytes("ISO8859-1"),"UTF-8");
			
			String search=request.getParameter("search");
			search= new String(search.getBytes("ISO8859-1"),"UTF-8");
			
			final Integer pageNum = adjustPageNo(request);
			final Integer pageSize = adjustPageSize(request);
			Integer startRow = (pageNum - 1) * pageSize;
			
			if(StringUtils.equals(search, "…Ã∆∑")){
				List<ProductDO> productList = productDao.search("%"+key+"%", startRow, pageSize);
				modelMap.put("proList", productList);
				return "html/sortlist";
			}
			if(StringUtils.equals(search, "µÍ∆Ã")){
				List<ShopDO> shopList=shopDAO.search("%"+key+"%", startRow, pageSize);
				modelMap.put("shopList", shopList);
				return "html/sortlist";
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "html/search";
	}
}
