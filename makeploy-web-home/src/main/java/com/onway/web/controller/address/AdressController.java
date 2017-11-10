package com.onway.web.controller.address;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.common.lang.StringUtils;
import com.onway.makeploy.common.dal.dataobject.AddressDO;
import com.onway.makeploy.common.service.exception.ErrorException;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;
import com.onway.web.controller.result.MyAddressResult;
import com.onway.web.controller.template.ControllerCallBack;

/**
 * 收货地址
 * @author Administrator
 *
 */
@Controller
public class AdressController extends AbstractController{

	
	/**
	 * 获取收货地址列表
	 * 
	 * @param request
	 * @param userId     用户Id
	 * @param delFlg     删除标识  0：未删除 1：删除
	 * @return
	 *//*
	@RequestMapping("/addressList.do")
	@ResponseBody
	public Object addressList(final HttpServletRequest request) {
		final String userId ="13252";// request.getParameter("userId");
		final Integer pageNum = adjustPageNo(request);
		final Integer pageSize = adjustPageSize(request);

		final JsonPageResult<AddressDO> result = new JsonPageResult<AddressDO>(true);
		controllerTemplate.execute(result, new ControllerCallBack() {

			@Override
			public void executeService() {
				// 查询记录数,总页数
				Integer startRow = (pageNum - 1) * pageSize;

				int totalItem = addressDao.selectAddressCountByUserIdAndDelFlg(userId, "0");
				int totalPages = (totalItem - 1) / pageSize + 1;

				List<AddressDO> addressDoList = addressDao.selectAddressInfoByUserIdAndDelFlg(userId, "0", startRow, pageSize);
				result.setListObject(addressDoList);
				result.setTotalPages(totalPages);
				result.setNext(totalPages > pageNum ? true : false);
			}

			@Override
			public void check() {

				AssertUtil.notBlank(userId, "userId为空");
			}
		});
		return result;
	}*/
	
	/**
	 * 我的收货地址
	 * 
	 * @param request
	 * @param userId
	 *            用户Id
	 * @param delFlg
	 *            删除标识 0：未删除 1：删除
	 * @return
	 */
	@RequestMapping("/myAddr.html")
	public Object myAddr(final HttpServletRequest request,final ModelMap modelMap) {
		// String userId = (String) request.getSession().getAttribute("userId");
		final String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("用户未登录");
		}
		
		final Integer pageNum = adjustPageNo(request);
		final Integer pageSize = adjustPageSize(request);
		
		//查询userId下未被删除的所有地址的数量
		int  addCount= addressDao.selectAddressCountByUserIdAndDelFlg(userId, "0");
		if( addCount <= 0)
		{
			return "html/noadd";
		}

		// 分页未考虑
		List<AddressDO> addList = addressDao.selectAddressInfoByUserIdAndDelFlg(userId, "0", null, null);

		List<MyAddressResult> list = new ArrayList<MyAddressResult>();
		MyAddressResult myAddressResult = null;
		for (AddressDO addressDo : addList) {
			myAddressResult = new MyAddressResult(true, "", "");
			myAddressResult.setDeliveryName(addressDo.getDeliveryName());
			myAddressResult.setCell(addressDo.getCell());
			myAddressResult.setFlg(addressDo.getFlg());
			myAddressResult.setAddressNo(addressDo.getAddressNo());
			StringBuffer detialAddr= new StringBuffer().append(addressDo.getProvience()).append(addressDo.getCity()).append(addressDo.getDistrict()).append(addressDo.getAddr());
			myAddressResult.setDetialAddr(detialAddr.toString());
			list.add(myAddressResult);
		}
		modelMap.put("address", list);
		return "html/add";
	}
	
	/**
	 * 跳转新增地址页面
	 *
	 * @return
	 */
	@RequestMapping("/newadd.html")
	public Object newadd(final HttpServletRequest request,final ModelMap modelMap) {
		
		return "html/newadd";
	}
	
	/**
	 * 新增收货地址
	 * @param request
	 * @param userId         用户ID
	 * @param deliveryName   收货人姓名
	 * @param addressNo      收货地址编号
	 * @param cell           收货人电话
	 * @param addr           收货人地址
	 * @param flg            标识 0：默认选中 1：默认不选
	 * @param delFlg         删除标识  0：未删除 1：删除
	 * @return
	 */
	@RequestMapping("/addAddress.do")
	@ResponseBody
	public Object addAddress(HttpServletRequest request)
	{
		final String userId="13252";//request.getParameter("userId");
		final String deliveryName=request.getParameter("deliveryName");
		final String cell = StringUtils.isBlank(request.getParameter("cell")) ? null: StringUtils.trim(request.getParameter("cell"));
		
		//省，市，区
		final String addr=request.getParameter("addr");//三级联动
	    
		//详细地址
		final String addrdetils=request.getParameter("addrdetils");
		
	
		final JsonResult jsonResult=new JsonResult(false);
		controllerTemplate.execute(jsonResult, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				
				//截取省市区
				String[] splitAddress=addr.split(",");
			    
			    String provience=splitAddress[0];
			    String city=splitAddress[1];
			    String district="";
			    if(splitAddress.length==3)
			    {
			       district=splitAddress[2];
			    }
			    
			    AddressDO addressDo=new AddressDO();
			    
				addressDo.setUserId(userId);
				addressDo.setDeliveryName(deliveryName);
				addressDo.setAddressNo("123458");//codeGenerateComponent
				addressDo.setCell(cell);
				addressDo.setProvience(provience);
				addressDo.setCity(city);
				addressDo.setDistrict(district);
				addressDo.setAddr(addrdetils);
				addressDo.setFlg("1");
				addressDo.setDelFlg("0");
				
				int result = addressDao.addAddress(addressDo);
				
				if(result >= 1 )
				{
					jsonResult.setBizSucc(true);
				}	
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(userId, "参数为空：userId");
				AssertUtil.notBlank(deliveryName, "参数为空：deliveryName");
				AssertUtil.notBlank(cell, "参数为空：cell");
				AssertUtil.notBlank(addr, "参数为空：addr");
			}
		});
		return jsonResult;	
	}
	
	
	/**
	 * 删除收货地址信息
	 * @param request
	 * @param userId        用户ID
	 * @param addressNo     收货地址编号
	 * @param delFlg        删除标识  0：未删除 1：删除      
	 * @return
	 */
	@RequestMapping("/deleteAddress.do")
	@ResponseBody
	public Object deleteAddress(HttpServletRequest request)
	{
		final String userId="13252";//request.getParameter("userId");
		final String addressNo=request.getParameter("addressNo");
		
		final JsonResult jsonResult=new JsonResult(false);
		controllerTemplate.execute(jsonResult, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				AddressDO addressDo=new AddressDO();
				addressDo.setUserId(userId);
				addressDo.setAddressNo(addressNo);
				addressDo.setDelFlg("1");
				int result = addressDao.changeAddressDelFlg(addressDo);
				
				if(result >= 1 )
				{
					jsonResult.setBizSucc(true);
				}	
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(userId, "参数为空：userId");
				AssertUtil.notBlank(addressNo, "参数为空：addressNo");
			}
		});
		return jsonResult;	
	}
	
	/**
	 * 修改收货地址信息
	 * @param request
	 * @param userId        用户ID
	 * @param addressNo     收货地址编号
	 * @param flg           标识 0：默认选中 1：默认不选中      
	 * @return
	 */
	@RequestMapping("/editAddr.do")
	@ResponseBody
	public Object editAddr(HttpServletRequest request)
	{
		final String userId="13252";//request.getParameter("userId");
		final String addressNo=request.getParameter("addressNo");
		
		final JsonResult jsonResult=new JsonResult(false);
		controllerTemplate.execute(jsonResult, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				//现将所有我的地址改为非默认
				AddressDO addDo=new AddressDO();
				addDo.setUserId(userId);
				addDo.setFlg("1");
				addressDao.changeALLFlg(addDo);
				
				//将addressNo的地址改为默
				AddressDO addressDo=new AddressDO();
				addressDo.setUserId(userId);
				addressDo.setAddressNo(addressNo);
				addressDo.setFlg("0");
				int result = addressDao.changeAddressFlg(addressDo);
				
				if(result >= 1 )
				{
					jsonResult.setBizSucc(true);
				}	
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(userId, "参数为空：userId");
				AssertUtil.notBlank(addressNo, "参数为空：addressNo");
			}
		});
		return jsonResult;	
	}
}
