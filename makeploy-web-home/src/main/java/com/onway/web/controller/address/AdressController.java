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
 * �ջ���ַ
 * @author Administrator
 *
 */
@Controller
public class AdressController extends AbstractController{

	
	/**
	 * ��ȡ�ջ���ַ�б�
	 * 
	 * @param request
	 * @param userId     �û�Id
	 * @param delFlg     ɾ����ʶ  0��δɾ�� 1��ɾ��
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
				// ��ѯ��¼��,��ҳ��
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

				AssertUtil.notBlank(userId, "userIdΪ��");
			}
		});
		return result;
	}*/
	
	/**
	 * �ҵ��ջ���ַ
	 * 
	 * @param request
	 * @param userId
	 *            �û�Id
	 * @param delFlg
	 *            ɾ����ʶ 0��δɾ�� 1��ɾ��
	 * @return
	 */
	@RequestMapping("/myAddr.html")
	public Object myAddr(final HttpServletRequest request,final ModelMap modelMap) {
		// String userId = (String) request.getSession().getAttribute("userId");
		final String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("�û�δ��¼");
		}
		
		final Integer pageNum = adjustPageNo(request);
		final Integer pageSize = adjustPageSize(request);
		
		//��ѯuserId��δ��ɾ�������е�ַ������
		int  addCount= addressDao.selectAddressCountByUserIdAndDelFlg(userId, "0");
		if( addCount <= 0)
		{
			return "html/noadd";
		}

		// ��ҳδ����
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
	 * ��ת������ַҳ��
	 *
	 * @return
	 */
	@RequestMapping("/newadd.html")
	public Object newadd(final HttpServletRequest request,final ModelMap modelMap) {
		
		return "html/newadd";
	}
	
	/**
	 * �����ջ���ַ
	 * @param request
	 * @param userId         �û�ID
	 * @param deliveryName   �ջ�������
	 * @param addressNo      �ջ���ַ���
	 * @param cell           �ջ��˵绰
	 * @param addr           �ջ��˵�ַ
	 * @param flg            ��ʶ 0��Ĭ��ѡ�� 1��Ĭ�ϲ�ѡ
	 * @param delFlg         ɾ����ʶ  0��δɾ�� 1��ɾ��
	 * @return
	 */
	@RequestMapping("/addAddress.do")
	@ResponseBody
	public Object addAddress(HttpServletRequest request)
	{
		final String userId="13252";//request.getParameter("userId");
		final String deliveryName=request.getParameter("deliveryName");
		final String cell = StringUtils.isBlank(request.getParameter("cell")) ? null: StringUtils.trim(request.getParameter("cell"));
		
		//ʡ���У���
		final String addr=request.getParameter("addr");//��������
	    
		//��ϸ��ַ
		final String addrdetils=request.getParameter("addrdetils");
		
	
		final JsonResult jsonResult=new JsonResult(false);
		controllerTemplate.execute(jsonResult, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				
				//��ȡʡ����
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
				AssertUtil.notBlank(userId, "����Ϊ�գ�userId");
				AssertUtil.notBlank(deliveryName, "����Ϊ�գ�deliveryName");
				AssertUtil.notBlank(cell, "����Ϊ�գ�cell");
				AssertUtil.notBlank(addr, "����Ϊ�գ�addr");
			}
		});
		return jsonResult;	
	}
	
	
	/**
	 * ɾ���ջ���ַ��Ϣ
	 * @param request
	 * @param userId        �û�ID
	 * @param addressNo     �ջ���ַ���
	 * @param delFlg        ɾ����ʶ  0��δɾ�� 1��ɾ��      
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
				AssertUtil.notBlank(userId, "����Ϊ�գ�userId");
				AssertUtil.notBlank(addressNo, "����Ϊ�գ�addressNo");
			}
		});
		return jsonResult;	
	}
	
	/**
	 * �޸��ջ���ַ��Ϣ
	 * @param request
	 * @param userId        �û�ID
	 * @param addressNo     �ջ���ַ���
	 * @param flg           ��ʶ 0��Ĭ��ѡ�� 1��Ĭ�ϲ�ѡ��      
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
				//�ֽ������ҵĵ�ַ��Ϊ��Ĭ��
				AddressDO addDo=new AddressDO();
				addDo.setUserId(userId);
				addDo.setFlg("1");
				addressDao.changeALLFlg(addDo);
				
				//��addressNo�ĵ�ַ��ΪĬ
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
				AssertUtil.notBlank(userId, "����Ϊ�գ�userId");
				AssertUtil.notBlank(addressNo, "����Ϊ�գ�addressNo");
			}
		});
		return jsonResult;	
	}
}
