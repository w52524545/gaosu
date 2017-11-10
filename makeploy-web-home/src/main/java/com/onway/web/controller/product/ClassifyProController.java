package com.onway.web.controller.product;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.makeploy.common.dal.dataobject.CategoryDO;
import com.onway.makeploy.common.dal.dataobject.ProductDO;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonPageResult;

/**
 * ���ఴť   
 * @author Administrator
 *
 */
@Controller
public class ClassifyProController extends AbstractController{

	/**
	 * ��ת����
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/sort.html")
	public String sort(final HttpServletRequest request,ModelMap modelMap){
		 Integer pageNum = adjustPageNo(request);
		 Integer pageSize = adjustPageSize(request);
		 Integer startRow = (pageNum - 1) * pageSize; 
		 String productType =request.getParameter("father");
		try {
			 List<ProductDO> productList = productDao.selectAllProduct(productType,startRow, pageSize);
			 int totalItem = productDao.selectNewProductCount();
			 int totalPages = (totalItem - 1) / pageSize + 1;
			 modelMap.addAttribute("productList", productList);
			 modelMap.addAttribute("totalItem", totalItem);
			 modelMap.addAttribute("totalPages", totalPages);
			 modelMap.addAttribute("startRow", startRow);
			 modelMap.addAttribute("nextPage", totalPages > pageNum ? true : false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "html/sort";
	}
	
	/**
	 * �ۺ�����
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/selectTypeByFatherNameSort.do")
	@ResponseBody
	public Object sorting(final HttpServletRequest request,final ModelMap modelMap){
		JsonPageResult<ProductDO> result = new JsonPageResult<ProductDO>(true);
		 Integer pageNum = adjustPageNo(request);
		 Integer pageSize = adjustPageSize(request);
		 Integer startRow = (pageNum - 1) * pageSize;
		 String productType =request.getParameter("father");
		 try {
			 List<ProductDO> productList = productDao.selectAllProduct(productType,startRow, pageSize);
			 result.setListObject(productList);
			 int totalItem = productDao.selectNewProductCount();
			 
			 int totalPages = (totalItem - 1) / pageSize + 1;
			 modelMap.addAttribute("totalItem", totalItem);
			 modelMap.addAttribute("totalPages", totalPages);
			 modelMap.addAttribute("startRow", startRow);
			 modelMap.addAttribute("nextPage", totalPages > pageNum ? true : false);
			 result.setBizSucc(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * �۸�ӵ͵�������չʾ��Ʒ
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("selectTypeByFatherNameSortAsc.do")
	@ResponseBody
	public Object sortingAsc(final HttpServletRequest request,final ModelMap modelMap){
		JsonPageResult<ProductDO> result = new JsonPageResult<ProductDO>(true);
		 String productType =request.getParameter("father");
		 Integer pageNum = adjustPageNo(request);
		 Integer pageSize = adjustPageSize(request);
		 Integer startRow = (pageNum - 1) * pageSize;
		 try {
			 List<ProductDO> productListAsc = productDao.selectAllProductOrderByPrice(productType,startRow, pageSize);
			 result.setListObject(productListAsc);
			 result.setBizSucc(true);
			 int totalItem = productDao.selectNewProductCount();
			 
			 int totalPages = (totalItem - 1) / pageSize + 1;
			 modelMap.addAttribute("totalItem", totalItem);
			 modelMap.addAttribute("totalPages", totalPages);
			 modelMap.addAttribute("startRow", startRow);
			 modelMap.addAttribute("nextPage", totalPages > pageNum ? true : false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * �۸�Ӹߵ�������
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("selectTypeByFatherNameSortDesc.do")
	@ResponseBody
	public Object sortingDesc(final HttpServletRequest request,final ModelMap modelMap){
		JsonPageResult<ProductDO> result = new JsonPageResult<ProductDO>(true);
		 String productType =request.getParameter("father");
		 Integer pageNum = adjustPageNo(request);
		 Integer pageSize = adjustPageSize(request);
		 Integer startRow = (pageNum - 1) * pageSize;
		 try {
			 List<ProductDO> productListDesc = productDao.selectAllProductOrderByPriceDesc(productType,startRow, pageSize);
			 result.setListObject(productListDesc);
			 result.setBizSucc(true);
			 int totalItem = productDao.selectNewProductCount();
			 
			 int totalPages = (totalItem - 1) / pageSize + 1;
			 modelMap.addAttribute("totalItem", totalItem);
			 modelMap.addAttribute("totalPages", totalPages);
			 modelMap.addAttribute("startRow", startRow);
			 modelMap.addAttribute("nextPage", totalPages > pageNum ? true : false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	

	
	/**
	 * 
	 * ��ת����
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/sortlist.html")
	public String sortList(final HttpServletRequest request,final ModelMap modelMap,final HttpServletResponse response){
		try {
			String productType = request.getParameter("fatherName").toString();
			productType= new String(productType.getBytes("ISO8859-1"),"UTF-8");
			Integer pageNum = adjustPageNo(request);
			Integer pageSize = adjustPageSize(request);
			Integer startRow = (pageNum - 1) * pageSize;
			
			List<ProductDO> proList = productDao.selectproductByType(productType, startRow, pageSize);
			int totalItem = (int) productDao.selectproductCountByType(productType);
			int totalPages = (totalItem - 1) / pageSize + 1;
			modelMap.addAttribute("productType", productType);
			modelMap.addAttribute("proList", proList);
			modelMap.addAttribute("totalItem", totalItem);
			modelMap.addAttribute("totalPages", totalPages);
			modelMap.addAttribute("startRow", startRow);
			modelMap.addAttribute("nextPage", totalPages > pageNum ? true : false);
			//ҳ�洫ֵ
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "html/sortlist";
	}
	
	/**
	 * ����ĸ��ݼ۸���
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/selectTypeByChildrenNameSortDesc.do")
	@ResponseBody
	public Object childrenSortingDesc(final HttpServletRequest request,ModelMap modelMap){
		 JsonPageResult<ProductDO> result = new JsonPageResult<ProductDO>(true);
		try {
			 String productType = request.getParameter("father").toString();
			 //productType= new String(productType.getBytes("ISO8859-1"),"UTF-8");
			 Integer pageNum = adjustPageNo(request);
			 Integer pageSize = adjustPageSize(request);
			 Integer startRow = (pageNum - 1) * pageSize;
		 
			 List<ProductDO> productListDesc = productDao.selectproductByTypeOrderByPriceDesc(productType, startRow, pageSize);
			 result.setListObject(productListDesc);
			 result.setBizSucc(true);
			 int totalItem = (int) productDao.selectproductCountByType(productType);
			 
			 int totalPages = (totalItem - 1) / pageSize + 1;
			 modelMap.addAttribute("totalItem", totalItem);
			 modelMap.addAttribute("totalPages", totalPages);
			 modelMap.addAttribute("startRow", startRow);
			 modelMap.addAttribute("nextPage", totalPages > pageNum ? true : false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * ����ĸ��ݼ۸��ۺ�����
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/selectTypeByChildrenNameSort.do")
	@ResponseBody
	public Object childrenSorting(final HttpServletRequest request,ModelMap modelMap){
		 JsonPageResult<ProductDO> result = new JsonPageResult<ProductDO>(true);
		try {
			 String productType = request.getParameter("father").toString();
			 //productType= new String(productType.getBytes("ISO8859-1"),"UTF-8");
			 Integer pageNum = adjustPageNo(request);
			 Integer pageSize = adjustPageSize(request);
			 Integer startRow = (pageNum - 1) * pageSize;
		 
			 List<ProductDO> proList = productDao.selectproductByType(productType, startRow, pageSize);
			 result.setListObject(proList);
			 result.setBizSucc(true);
			 int totalItem = (int) productDao.selectproductCountByType(productType);
			 
			 int totalPages = (totalItem - 1) / pageSize + 1;
			 modelMap.addAttribute("totalItem", totalItem);
			 modelMap.addAttribute("totalPages", totalPages);
			 modelMap.addAttribute("startRow", startRow);
			 modelMap.addAttribute("nextPage", totalPages > pageNum ? true : false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * ����ĸ��ݼ۸���������
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/selectTypeByChildrenNameSortAsc.do")
	@ResponseBody
	public Object childrenSortingAsc(final HttpServletRequest request,ModelMap modelMap){
		 JsonPageResult<ProductDO> result = new JsonPageResult<ProductDO>(true);
		try {
			 String productType = request.getParameter("father").toString();
			 //productType= new String(productType.getBytes("ISO8859-1"),"UTF-8");
			 Integer pageNum = adjustPageNo(request);
			 Integer pageSize = adjustPageSize(request);
			 Integer startRow = (pageNum - 1) * pageSize;
		 
			 List<ProductDO> proList = productDao.selectproductByTypeOrderByPrice(productType, startRow, pageSize);
			 result.setListObject(proList);
			 result.setBizSucc(true);
			 int totalItem = (int) productDao.selectproductCountByType(productType);
			 
			 int totalPages = (totalItem - 1) / pageSize + 1;
			 modelMap.addAttribute("totalItem", totalItem);
			 modelMap.addAttribute("totalPages", totalPages);
			 modelMap.addAttribute("startRow", startRow);
			 modelMap.addAttribute("nextPage", totalPages > pageNum ? true : false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * ���������ʾ�����ǩ
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("selectTypeByFatherName.do")
	@ResponseBody
	public Object type(final HttpServletRequest request,final ModelMap modelMap){
		JsonPageResult<CategoryDO> result = new JsonPageResult<CategoryDO>(true);
		String fatherName = request.getParameter("father");
		try {
			List<CategoryDO> categorylist = categoryDAO.selectTypeByFather(fatherName);
			result.setFather(fatherName);
			result.setListObject(categorylist);
			result.setBizSucc(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * ���������ʾ�����µ���Ʒ��Ϣ
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/selectFatherInfo.do")
	@ResponseBody
	public Object FatherInfo(final HttpServletRequest request,final ModelMap modelMap){
		JsonPageResult<ProductDO> result = new JsonPageResult<ProductDO>(true);
		 String productType = request.getParameter("father").toString();
		 Integer pageNum = adjustPageNo(request);
		 Integer pageSize = adjustPageSize(request);
		 Integer startRow = (pageNum - 1) * pageSize;
		try {
			List<ProductDO> proList = productDao.selectproductByType(productType, startRow, pageSize);
			result.setListObject(proList);
			
			int totalItem = (int) productDao.selectproductCountByType(productType);
			int totalPages = (totalItem - 1) / pageSize + 1;
			modelMap.addAttribute("productType", productType);
			modelMap.addAttribute("proList", proList);
			modelMap.addAttribute("totalItem", totalItem);
			modelMap.addAttribute("totalPages", totalPages);
			modelMap.addAttribute("startRow", startRow);
			modelMap.addAttribute("nextPage", totalPages > pageNum ? true : false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
