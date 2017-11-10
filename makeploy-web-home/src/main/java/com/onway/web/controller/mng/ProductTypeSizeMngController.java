package com.onway.web.controller.mng;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.onway.makeploy.common.dal.dataobject.CategoryDO;
import com.onway.platform.common.configration.ConfigrationFactory;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;
import com.onway.web.controller.result.TypeResult;


@Controller
public class ProductTypeSizeMngController extends AbstractController{
	private static final String IMAGE_FILE = ConfigrationFactory
			.getConfigration().getPropertyValue("user_img_upload_realpath");

	private static final String IMAGE_PATH = ConfigrationFactory
			.getConfigration().getPropertyValue("user_img_path");
	
	
	/***********    分类部分
	 * 
	 */
	/**
	 * 进入商品分类管理页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("productTypeMng.html")
	public ModelAndView productTypeMng(HttpServletRequest request, ModelMap modelMap){
		String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return new ModelAndView("mng/loginMng");
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
		
		List<String> fatherTypeList=categoryDAO.selectAllFather();
		List<TypeResult> typeJsonResultList=new ArrayList<TypeResult>();//初始化 设置size 尝试失败  改用 add
		List<CategoryDO> OnlyFather=new ArrayList<CategoryDO>();
		List<String> father=categoryDAO.selectOnlyFather();
		for(int i=0;i<father.size();i++){
			OnlyFather.add(categoryDAO.selectOneTypeByFather(father.get(i)));
		}
		for(int i=0;i<fatherTypeList.size();i++){
			TypeResult TypeResult=new TypeResult();
			List<CategoryDO> typelist=categoryDAO.selectTypeByFather(fatherTypeList.get(i));
			if(typelist.size()>0){
			TypeResult.setFatherType(fatherTypeList.get(i));
			TypeResult.setFatherTypeIcon(typelist.get(0).getFatherImg());
			TypeResult.setListObject(typelist);
			TypeResult.setType(typelist.get(0).getType());
			typeJsonResultList.add(TypeResult);
			}
		}
		modelMap.put("typeJsonResultList", typeJsonResultList);
		modelMap.put("OnlyFather", OnlyFather);
		return new ModelAndView("mng/productTypeMng");
	}
	
	@RequestMapping("tohpage.do")
	@ResponseBody
	public Object tohpage(HttpServletRequest request){
		JsonResult result = new JsonResult(false, null, "操作失败");
		String fatherName= request.getParameter("fathertype");
		String type = request.getParameter("type");
		categoryDAO.updateTypeToHomePage(type, fatherName);
		return result;
	}
	
	/**
	 * 添加子类型/父类型
	 * @param request
	 * @param addChirdFile
	 * @return
	 */
	@RequestMapping("addChildType.do")
	@ResponseBody
	public Object addChildType(HttpServletRequest request, String addChirdFile){
		String childName=request.getParameter("childName");
		String fatherType=request.getParameter("fatherType");
		//判断该分类是否在首页
		String type=request.getParameter("type");
		String path = IMAGE_FILE;
	    Base64 base64 =  new Base64();
//		String imageNames = new String();
		String imageRelativePath= new String();
	       //file 为前台隐藏域里面的字符串
		if (addChirdFile != null && addChirdFile.length() != 0) {
			imageRelativePath = String.valueOf(System.currentTimeMillis())
					+ ".jpg";//

			// base64 解码
			byte[] byteArray = base64.decode(addChirdFile);
			// 调整异常数据
			for (byte b : byteArray) {
				if (b < 0)
					b += 256;
			}
			try {
				OutputStream out = new FileOutputStream(path
						+ imageRelativePath);
				out.write(byteArray);
				out.close();
				imageRelativePath = IMAGE_PATH + imageRelativePath;
				CategoryDO category=new CategoryDO();
				if(null!=fatherType&&!fatherType.equals("a")){//判断添加的为 子类型
					CategoryDO cate=categoryDAO.selectByFather(fatherType).get(0);
					category.setFatherName(cate.getFatherName());
					category.setFatherImg(cate.getFatherImg());
					category.setChildrenImg(imageRelativePath);
					category.setChildrenName(childName);
					category.setMemo("0");
					if(StringUtils.equals(type, "1")){
						category.setType("1");
					}else{
						category.setType("0");
					}
					categoryDAO.addType(category);
				}else if(fatherType.equals("a")){//判断添加的为 父类型
					category.setFatherName(childName);
					category.setFatherImg(imageRelativePath);
					category.setMemo("1");//仅有父类
					categoryDAO.addType(category);
					
					CategoryDO categorys=new CategoryDO();
					categorys.setFatherName(childName);
					categorys.setFatherImg(imageRelativePath);
					categorys.setChildrenName(childName);
					categorys.setChildrenImg(imageRelativePath);
					categorys.setMemo("0");
					categorys.setType("1");
					categoryDAO.addType(categorys);
				}
				
				//删除  仅有父类 无子类 的 记录
				CategoryDO CategoryDo=categoryDAO.selectOneTypeByFather(fatherType);
				if(null!=CategoryDo){
					categoryDAO.deleteOnlyFather(fatherType);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "1";
	}
	@RequestMapping("delectchild.do")
	@ResponseBody
	public Object delectchild(HttpServletRequest request, String childType){
		try{
			categoryDAO.deleteChirdType(childType);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return "1";
		
	}
	/**
	 * 修改父类图标
	 * @param request
	 * @param baseFile
	 * @return
	 */
	@RequestMapping("uploadFatherImg.do")
	@ResponseBody
	public Object uploadFatherImg(HttpServletRequest request,String[] baseFile,String baseFileType,String editpFatherType){
		String path = IMAGE_FILE;
	    Base64 base64 =  new Base64();
		String imageNames = new String();
		String imageRelativePath= new String();
	       //file 为前台隐藏域里面的字符串
	    if(baseFile!= null && baseFile.length!=0){
	    	if (null == baseFileType
					|| baseFileType.split("/") == null) {
				imageRelativePath = String.valueOf(System
						.currentTimeMillis()) + ".jpg";//
			} else {
				if (baseFileType.split("/").length > 1) {
					imageRelativePath = String.valueOf(System
							.currentTimeMillis())
							+ "."
							+ baseFileType.split("/")[1];//
				} else {
					imageRelativePath = String.valueOf(System
							.currentTimeMillis()) + ".jpg";//
				}
			}
	     for (String base64Str : baseFile) {
	             //base64 解码
	               byte[] byteArray = base64.decode(base64Str);
	               // 调整异常数据  
	               for (byte b : byteArray) {
	                   if(b<0)
	                       b+=256;
	               }
	               try {
	                   OutputStream out = new FileOutputStream(path+imageRelativePath);
	                   out.write(byteArray);
	                   out.close();
	                   imageRelativePath=IMAGE_PATH+imageRelativePath;
	                   categoryDAO.updateFatherIcon(imageRelativePath, editpFatherType);
	               } catch (Exception e) {
	                   e.printStackTrace();
	                   System.out.println(imageNames);
	               }
	           }
	       }
	           System.out.println(imageNames);
	        System.out.println("");
	        return "1";
	}
	/**
	 * 修改父类名称
	 * @param request
	 * @param baseFile
	 * @return
	 */
	@RequestMapping("editFatherTypeName.do")
	@ResponseBody
	public Object editFatherTypeName(HttpServletRequest request,String fatherType,String newfatherType){
		try{
			categoryDAO.updateFatherName(newfatherType, fatherType);
		}
		catch (Exception e) {
            e.printStackTrace();
        }
		return "1";
		
	}
	/**
	 * 删除父类型
	 * @param request
	 * @param fatherType
	 * @return
	 */
	@RequestMapping("delectFatherType.do")
	@ResponseBody
	public Object delectFatherType(HttpServletRequest request,String fatherType){
		try{
			categoryDAO.delectFatherType(fatherType);
		}
		catch (Exception e) {
            e.printStackTrace();
        }
		return "1";
	}
	
	
	/*******     样式部分
	 * 
	 */
	/**
	 * 进入 商品样式管理页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("productParameterMng.html")
	public ModelAndView productParameterMng(HttpServletRequest request,
			ModelMap modelMap) {
		String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return new ModelAndView("mng/loginMng");
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
		
		List<TypeResult> typeJsonResultList = new ArrayList<TypeResult>();// 初始化设置size尝试失败 改用 add
		List<CategoryDO> sizeList = categoryDAO.selectSize();
		if (sizeList.size() > 0) {
			for (int i = 0; i < sizeList.size(); i++) {
				TypeResult typeResult = new TypeResult();
				typeResult.setFatherType(sizeList.get(i).getFatherName());
				String parameter = sizeList.get(i).getFatherImg();
				String[] PParameter = parameter.split(",");
				if (PParameter.length > 0) {
					List<String> list = new ArrayList<String>();
					for (int j = 0; j < PParameter.length; j++) {
						if(PParameter[j].length()>0){
							list.add(PParameter[j]);
						}
					}
					typeResult.setList(list);
				}
				typeJsonResultList.add(typeResult);
			}
		}
		modelMap.put("typeJsonResultList", typeJsonResultList);
		return new ModelAndView("mng/productParameterMng");
	}
	/**
	 * 修改样式名称
	 * @param request
	 * @param fatherType
	 * @param newfatherType
	 * @return
	 */
	@RequestMapping("editSizeName.do")
	@ResponseBody
	public Object editSizeName(HttpServletRequest request,String fatherType,String newfatherType){
		try{
			categoryDAO.updateFatherName(newfatherType, fatherType);
		}
		catch (Exception e) {
            e.printStackTrace();
        }
		return "1";
		
	}
	
	/**
	 * 删除样式
	 * @param request
	 * @param fatherType
	 * @return
	 */
	@RequestMapping("delectSize.do")
	@ResponseBody
	public Object delectSize(HttpServletRequest request,String fatherType){
		try{
			categoryDAO.delectFatherType(fatherType);
		}
		catch (Exception e) {
            e.printStackTrace();
        }
		return "1";
	}
	
	/**
	 * 添加参数
	 * @param request
	 * @param Size
	 * @return
	 */
	@RequestMapping("addParameter.do")
	@ResponseBody
	public Object addParameter(HttpServletRequest request,String Size){
		String parameterName=getParameterCheck(request, "parameterName");
		if(null!=parameterName&&null!=Size) {
			String addParameter=categoryDAO.selectParamenterBySize(Size).getFatherImg();
			if(parameterName.contains("-")){
				String [] Parameter=parameterName.split("-");
				for(int i=0;i<Parameter.length ;i++){
					addParameter+=","+Parameter[i];
				}
			}else {
				addParameter+=","+parameterName;
			}
			categoryDAO.updateParamenterBySize(addParameter, Size);
		}
		
		return "1";
		
	}
	
	/**
	 * 删除参数
	 * @param request
	 * @return
	 */
	@RequestMapping("delectParamenter.do")
	@ResponseBody
	public Object delectParamenter(HttpServletRequest request){
		String size=getParameterCheck(request, "size");
		String paramenter=getParameterCheck(request, "paramenter");
		if(null!=size&&null!=paramenter){
			CategoryDO categoryDO=categoryDAO.selectParamenterBySize(size);
			if(null!=categoryDO){
				String para=categoryDO.getFatherImg();
				if(para.contains(paramenter)){
					para=para.replace(paramenter, "");
					para=para.replace(",,", ",");
					categoryDAO.updateParamenterBySize(para, size);
					return "1";
				}else{
					
					return "0";
				}
			}else{
				
				return "0";
			}
		}else{
			
			return "0";
		}
		
		
		
	}
	
	
	@RequestMapping("addSizeParameter.do")
	@ResponseBody
	public Object addSizeParameter(HttpServletRequest request){
		String size=getParameterCheck(request, "size");
		String paramenter=getParameterCheck(request, "parameterName");
		if(null!=size&&null!=paramenter){
			if(paramenter.contains("-")){
				paramenter=paramenter.replace("-", ",");
			}
			CategoryDO category=new CategoryDO();
			category.setFatherImg(paramenter);
			category.setFatherName(size);
			category.setMemo("3");
			try{
			categoryDAO.addType(category);
			}catch (Exception e) {
	            e.printStackTrace();
	        }
			return "1";
		}else{
		
			return "0";
		}
		
	}
	
}
