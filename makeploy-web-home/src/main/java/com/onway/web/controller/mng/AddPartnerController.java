package com.onway.web.controller.mng;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.makeploy.common.dal.dataobject.AccountDO;
import com.onway.makeploy.common.dal.dataobject.ActivityDO;
import com.onway.makeploy.common.dal.dataobject.OrderDO;
import com.onway.makeploy.common.dal.dataobject.PartnerDO;
import com.onway.makeploy.common.dal.dataobject.PartnerRankDO;
import com.onway.makeploy.common.dal.dataobject.ProImageDO;
import com.onway.makeploy.common.dal.dataobject.ProductDO;
import com.onway.makeploy.common.dal.dataobject.ProductParameterDO;
import com.onway.makeploy.common.dal.dataobject.RoleDO;
import com.onway.makeploy.common.dal.dataobject.ShopCheckDO;
import com.onway.makeploy.common.dal.dataobject.ShopDO;
import com.onway.makeploy.common.dal.dataobject.SysConfigDO;
import com.onway.makeploy.common.dal.dataobject.UserDO;
import com.onway.makeploy.core.localcache.enums.SysConfigCacheKeyEnum;
import com.onway.platform.common.enums.PlatformCodeEnum;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.UUIDHexGenerator;
import com.onway.web.controller.result.JsonResult;
import com.onway.web.controller.sendMessage.SendCustomMessage;
import com.onway.web.controller.template.ControllerCallBack;

@Controller
public class AddPartnerController extends AbstractController {
	/**
	 * 添加合伙人
	 * @param request
	 * @param modelMap
	 * @return
	 */
    @RequestMapping("/addPartner.html")
    public String homepage(HttpServletRequest request, ModelMap modelMap) {
        String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
        
        return "mng/addPartner";
    }
    /**
     * 新增排单
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("/addRankPartner.html")
    public String addRankPartner(HttpServletRequest request, ModelMap modelMap) {
        String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
        
        return "mng/addRankPartner";
    }
    /**
     * 插入排单
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("/insertRankPartner.html")
    public String insertRankPartner(HttpServletRequest request, ModelMap modelMap) {
        String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
        
        return "mng/insertRankPartner";
    }

    @RequestMapping("/recharge.html")
    public String recharge(HttpServletRequest request, ModelMap modelMap) {
    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}

    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
        
        return "mng/recharge";

    }

    /**
     * 查询用户是否存在  
     * @param request
     * @return
     */
    @RequestMapping("/selectUser.do")
    @ResponseBody
    public Object selectUser(HttpServletRequest request) {
        String userId = getParameterCheck(request, "userId");
        UserDO userdo = userDao.selectUserInfoByUserId(userId);
        if (null != userdo) {
            userdo.setCertNo(userdo.getBalance().toString());//CertNo字段存 余额
            float points = (float) userdo.getPointAccout() / 100;
            userdo.setCertType(points + "");//certType字段存 积分
            return userdo;
        } else {
            return "0";
        }
    }

    /**
     * 充值
     * @param request
     * @return
     */
    @RequestMapping("/recharg.do")
    @ResponseBody
    public Object recharg(HttpServletRequest request) {
        String userId = getParameterCheck(request, "userId");
        String number = getParameterCheck(request, "number");
        String flag = getParameterCheck(request, "flag");
        String accountNo = codeGenerateComponent.nextCodeByType(PlatformCodeEnum.MALL_PLATFORM);
        AccountDO account = new AccountDO();
        if (null != flag && null != number && null != userId) {
            UserDO userdo = userDao.selectUserInfoByUserId(userId);
            if (flag.equals("2")) {//充值余额
                Money money = new Money(number);
                money.addTo(userdo.getBalance());
                userdo.setBalance(money);
                //插入流水表
                account.setOrderNo(accountNo);
                account.setUserId(userId);
                account.setType("1");
                account.setAmount(new Money(number));
                accountDao.insert(account);
            } else if (flag.equals("1")) {//充值积分
                float points = Float.parseFloat(number) * 100;
                int point = (int) points;
                point += userdo.getPointAccout();
                userdo.setPointAccout(point);
                //插入流水表
                account.setOrderNo(accountNo);
                account.setUserId(userId);
                account.setType("1");
                account.setPoint((int) points);
                accountDao.insert(account);
            }
            userDao.updateUserReturn(userdo.getPointAccout(), userdo.getBalance(),
                userdo.getWithdrawAccout(), userId);
            return "1";
        } else {
            return "0";
        }

    }

    /**
     * 查询用户是否存在  已是农村经纪人
     * @param request
     * @return
     */
    @RequestMapping("/selectUserPartner.do")
    @ResponseBody
    public Object selectUserPartner(HttpServletRequest request) {
        String userId = getParameterCheck(request, "userId");
        PartnerDO partnerDO = partnerDAO.checkPartnerByPUserId(userId);
        UserDO userdo = userDao.selectUserInfoByUserId(userId);
        if (null != userdo) {
            if (null != partnerDO) {
                return "1";
            } else {
                return userdo;
            }
        } else {
            return "0";
        }
    }
    
    /**
     * 查询用户是否存在  已是农村经纪人
     * @param request
     * @return
     */
    @RequestMapping("/selectRankPartner.do")
    @ResponseBody
    public Object selectRankPartner(HttpServletRequest request) {
        String userId = getParameterCheck(request, "userId");
        int count = partnerRankDAO.selectPartnerUserIdCount(userId,null);
        UserDO userdo = userDao.selectUserInfoByUserId(userId);
        
        String sysValue = sysConfigDAO.selectByKey("LIMIT_RANK").getSysValue();
        int limitRank = Integer.valueOf(sysValue);
        if (null != userdo) {
            if (count >= limitRank) {
                return "1";
            } else {
            	return userdo;
            }
        } else {
            return "0";
        }
    }

    /**
     * 设为农村经纪人
     * @param request
     * @return
     */
    @RequestMapping("/setPartner.do")
    @ResponseBody
    public Object setPartner(HttpServletRequest request) {
        String userId = getParameterCheck(request, "userId");
        JsonResult result = new JsonResult(false);
        UserDO userDO = userDao.selectUserInfoByUserId(userId);
        if(null == userDO){
        	result.setBizSucc(false);
            result.setErrMsg("用户不存在！");
        }
        ShopCheckDO sc = shopCheckDAO.selectByUserIdApplyType(userId, null , "0");//APPLY_TYPE=0;CHECK_STATUS=0
        if (null != sc) {
        	if(StringUtils.equals(sc.getApplyType(), "0")){
        		result.setBizSucc(false);
                result.setErrMsg("该用户已经提交了农村经纪人申请,请去受理!");
        	}
        	if(StringUtils.equals(sc.getApplyType(), "1")){
        		result.setBizSucc(false);
                result.setErrMsg("该用户已经提交了联盟商家申请,请去受理!");
        	}
        } else {
        	//判断有没有联盟申请
            ShopCheckDO schUnion = shopCheckDAO.selectByUserIdApplyType(userId, "1", null);//
            if(null == schUnion){
            	//判断是否有被拒绝的农村经纪人申请
	            ShopCheckDO sch = shopCheckDAO.selectByUserIdApplyType(userId, "0", "2");//APPLY_TYPE=0;CHECK_STATUS=2
	            if (null == sch) {//不存在被拒绝的申请
	                // 创建店铺Id 
	                String shopId1 = codeGenerateComponent
	                    .nextCodeByType(PlatformCodeEnum.MALL_PLATFORM);// 系统生成店铺id
	                addPartnerProduct(shopId1, userId);//添加默认农村经纪人商品
	                //      放到 shop表
	                ShopDO shopDo = new ShopDO();
	                shopDo.setUserId(userId);
	                shopDo.setShopId(shopId1);
	                shopDo.setUnionFlg("0");
	                shopDo.setShopUrl("http://jzsc8888.com/image/1486950168906.jpeg");
	                shopDo.setUnionFlg("1");
	                shopDAO.creatNewShop(shopDo);
	                
	                // 农村经纪人表 插入数据
	                PartnerDO partnerDO = new PartnerDO();
	                Money PAccount = new Money(0);
	                partnerDO.setPartnerAccout(PAccount);
	                partnerDO.setPartnerUserId(userId);
	                partnerDAO.joinPartner(partnerDO);
	                
	                // 权限表插入数据
	                try {
	                    RoleDO roleDO = roleDao.selectShopRoleByUserId(userId);
	                    if (null == roleDO) {
	                        RoleDO Role = new RoleDO();
	                        Role.setRole("0");
	                        Role.setUserId(userId);
	                        roleDao.addRole(Role);
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	                // 更新 user表
	                userDao.addShopByUserId(shopId1, userId);
	                //check表插入一条apply_type=0  check_status=1的农村经纪人申请成功的数据
	                ShopCheckDO sck = new ShopCheckDO();
	                sck.setApplyType("0");
	                sck.setCheckStatus("1");
	                sck.setShopName("---");
	                sck.setShopDec("后台手动添加的农村经纪人");
	                sck.setShopAddr("---");
	                sck.setUserJob("----");
	                sck.setShopId(shopId1);
	                sck.setUserId(userId);
	                shopCheckDAO.addCheckedPartner(sck);
	                ActivityDO shopCheckNotice = new ActivityDO();
	                String userName = userDao.selectUserInfoByUserId(userId).getNickName();
	                //首页滚动消息
	                shopCheckNotice.setTitle("客户农村经纪人申请(后台添加)");
	                //shopCheckNotice.setInfo(userName+"在"+checktime+"申请了属于自己的店铺！");
	                //              shopCheckNotice.setInfo("“"+shopDAO.selectShopByShopId(shopId).getShopName()+"”"+"店刚刚通过审核入驻平台！");
	                shopCheckNotice.setInfo(userName + "刚刚通过审核成为农村经纪人！");
	                shopCheckNotice.setType("1");
	                activityDao.resetStateByType("1");
	                shopCheckNotice.setState("1");
	                activityDao.addInfo(shopCheckNotice);
	                try {
	                    // 公众号发送提示消息
	                	SysConfigDO sysConfigDO = sysConfigDAO.selectByKey(SysConfigCacheKeyEnum.ACCESS_TOKEN.toString());
						String accessToken = sysConfigDO.getSysValue();
//	                    String accessToken = sysConfigCacheManager
//	                        .getConfigValue(SysConfigCacheKeyEnum.ACCESS_TOKEN);
	                    UserDO userdo = userDao.selectUserInfoByUserId(userId);
	                    String jsonTextMsg = "";
	                    String notice = "亲爱的用户，您已被添加为农村经纪人";
	                    //                      notice= new String(notice.getBytes("UTF-8"),"GB18030");
	                    jsonTextMsg = SendCustomMessage.makeTextCustomMessage(userdo.getWechatId(),
	                        notice);
	                    SendCustomMessage.sendCustomMessage(accessToken, jsonTextMsg);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	                result.setBizSucc(true);
	                result.setErrMsg("添加农村经纪人成功!");
	                return result;
	            } else {//存在被拒绝的申请
	                String shopId = sch.getShopId();
	                addPartnerProduct(shopId, userId);//添加默认农村经纪人商品
	                //      放到 shop表
	                ShopDO shopDo = new ShopDO();
	                shopDo.setUserId(userId);
	                shopDo.setShopId(shopId);
	                shopDo.setUnionFlg("0");
	                shopDo.setShopUrl("http://jzsc8888.com/image/1486950168906.jpeg");
	                shopDo.setUnionFlg("1");
	                shopDAO.creatNewShop(shopDo);
	
	                // 农村经纪人表 插入数据
	                PartnerDO partnerDO = new PartnerDO();
	                Money PAccount = new Money(0);
	                partnerDO.setPartnerAccout(PAccount);
	                partnerDO.setPartnerUserId(userId);
	                partnerDAO.joinPartner(partnerDO);
	                
	                // 权限表插入数据
	                try {
	                    RoleDO roleDO = roleDao.selectShopRoleByUserId(userId);
	                    if (null == roleDO) {
	                        RoleDO Role = new RoleDO();
	                        Role.setRole("0");
	                        Role.setUserId(userId);
	                        roleDao.addRole(Role);
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	                // 更新 user表
	                userDao.addShopByUserId(shopId, userId);
	                //在check修改审核状态check_status=1
	                shopCheckDAO.updateCheckStatusByshopId("1",sch.getShopId());
	                
	                ActivityDO shopCheckNotice = new ActivityDO();
	                String userName = userDao.selectUserInfoByUserId(userId).getNickName();
	                //首页滚动消息
	                shopCheckNotice.setTitle("客户农村经纪人申请(后台添加)");
	                //shopCheckNotice.setInfo(userName+"在"+checktime+"申请了属于自己的店铺！");
	                //              shopCheckNotice.setInfo("“"+shopDAO.selectShopByShopId(shopId).getShopName()+"”"+"店刚刚通过审核入驻平台！");
	                shopCheckNotice.setInfo(userName + "刚刚通过审核成为农村经纪人！");
	                shopCheckNotice.setType("1");
	                activityDao.resetStateByType("1");
	                shopCheckNotice.setState("1");
	                activityDao.addInfo(shopCheckNotice);
	                result.setBizSucc(true);
	                result.setErrMsg("添加农村经纪人成功!");
	                return result;
	            }
            }else{
            	// 创建店铺Id 
                String shopId = schUnion.getShopId();
            	if(StringUtils.isEmpty(schUnion.getShopId())){
            		shopId = codeGenerateComponent.nextCodeByType(PlatformCodeEnum.MALL_PLATFORM);// 系统生成店铺id
//                  放到 shop表
                    ShopDO shopDo = new ShopDO();
                    shopDo.setUserId(userId);
                    shopDo.setShopId(shopId);
                    shopDo.setUnionFlg("0");
                    shopDo.setShopUrl("http://jzsc8888.com/image/1486950168906.jpeg");
                    shopDo.setUnionFlg("1");
                    shopDAO.creatNewShop(shopDo);
                    // 更新 user表
                    userDao.addShopByUserId(shopId, userId);
                    addPartnerProduct(shopId, userId);//添加默认农村经纪人商品
            	}
                // 农村经纪人表 插入数据
                PartnerDO partnerDO = new PartnerDO();
                Money PAccount = new Money(0);
                partnerDO.setPartnerAccout(PAccount);
                partnerDO.setPartnerUserId(userId);
                partnerDAO.joinPartner(partnerDO);
                
                // 权限表插入数据
                try {
                    RoleDO roleDO = roleDao.selectShopRoleByUserId(userId);
                    if (null == roleDO) {
                        RoleDO Role = new RoleDO();
                        Role.setRole("0");
                        Role.setUserId(userId);
                        roleDao.addRole(Role);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                //在check修改审核状态check_status=1
                shopCheckDAO.changeApplytype("1", "0",userId);
                
                ActivityDO shopCheckNotice = new ActivityDO();
                String userName = userDao.selectUserInfoByUserId(userId).getNickName();
                //首页滚动消息
                shopCheckNotice.setTitle("客户农村经纪人申请(后台添加)");
                //shopCheckNotice.setInfo(userName+"在"+checktime+"申请了属于自己的店铺！");
                //              shopCheckNotice.setInfo("“"+shopDAO.selectShopByShopId(shopId).getShopName()+"”"+"店刚刚通过审核入驻平台！");
                shopCheckNotice.setInfo(userName + "刚刚通过审核成为农村经纪人！");
                shopCheckNotice.setType("1");
                activityDao.resetStateByType("1");
                shopCheckNotice.setState("1");
                activityDao.addInfo(shopCheckNotice);
                result.setBizSucc(true);
                result.setErrMsg("添加农村经纪人成功!");
                return result;
            }

        }
        return result;
    }
    
    @RequestMapping("/setRankPartner.do")
    @ResponseBody
    public Object setRankPartner(HttpServletRequest request) {
        String userId = getParameterCheck(request, "userId");
        JsonResult result = new JsonResult(true);
        UserDO userDO = userDao.selectUserInfoByUserId(userId);
        if(null == userDO){
        	result.setBizSucc(false);
            result.setErrMsg("用户不存在！");
        }
        //生成一个订单
        OrderDO orderDo = new OrderDO();
        String orderNo = UUIDHexGenerator.getOrderNo();
		orderDo.setUserId(userId);
		orderDo.setUserName(userDO.getUserName());
		orderDo.setCell(userDO.getCell());
		orderDo.setOrderNo(orderNo);
		orderDo.setOrderId(orderNo);
		orderDo.setLuggage(new Money());
		int orderNum = 1;
		//判断第几个订单
        OrderDO lastPartnerOrder = orderDao.selectLastPartnerOrder();
        if(null == lastPartnerOrder){
        	orderNum = 1;
	    }else{
	    	int myRank;
		    int lastRank= lastPartnerOrder.getOrderNum();
		    myRank = lastRank + 1;
		    orderNum = myRank;
	    }
        orderDo.setOrderNum(orderNum);
		orderDao.creatPartnerOrder(orderDo);
		
		//查询最后一个
		PartnerRankDO lastPartner = partnerRankDAO.selectLastPartner(null,0,1);
		int parNum = 1;
		if(null != lastPartner){
			parNum = lastPartner.getPartnerNum()+1;
		}
        //排序表插数据
        PartnerRankDO partnerRankDO = new PartnerRankDO();
        partnerRankDO.setUserId(userId);
        partnerRankDO.setPartnerNum(parNum);
        partnerRankDO.setOrderNum(orderNum);
        partnerRankDO.setOrderId(orderNo);
        partnerRankDAO.insert(partnerRankDO);
        
        return result;
    }
    
    @RequestMapping("/insertRankPartner.do")
    @ResponseBody
    public Object insertRankPartner(HttpServletRequest request) {
        String userId = getParameterCheck(request, "userId");
        String partnerNum = getParameterCheck(request, "partnerNum");//排在这个位置之前
        int num = Integer.valueOf(partnerNum);//当前人应排的排单位置
        JsonResult result = new JsonResult(true);
        UserDO userDO = userDao.selectUserInfoByUserId(userId);
        if(null == userDO){
        	result.setBizSucc(false);
            result.setErrMsg("用户不存在！");
        }
        PartnerRankDO bigEqualThan = partnerRankDAO.selectByPartnerNum(num);
        if(null == bigEqualThan){
        	result.setBizSucc(false);
            result.setErrMsg("该顺序排单不存在！");
        }
        int orderNum = bigEqualThan.getOrderNum();//当前人应排的订单位置
        
        //生成一个订单
        OrderDO orderDo = new OrderDO();
        String orderNo = UUIDHexGenerator.getOrderNo();
		orderDo.setUserId(userId);
		orderDo.setUserName(userDO.getUserName());
		orderDo.setCell(userDO.getCell());
		orderDo.setOrderNo(orderNo);
		orderDo.setOrderId(orderNo);
		orderDo.setLuggage(new Money());
        orderDo.setOrderNum(orderNum);
        
        //排序表插数据
        PartnerRankDO partnerRankDO = new PartnerRankDO();
        partnerRankDO.setUserId(userId);
        partnerRankDO.setPartnerNum(num);
        partnerRankDO.setOrderNum(orderNum);
        partnerRankDO.setOrderId(orderNo);
        
        List<PartnerRankDO> equalThan = partnerRankDAO.selectAllBigEqualThan(num);
        for (PartnerRankDO partnerRankDO2 : equalThan) {
			partnerRankDAO.changePartnerNum(partnerRankDO2.getPartnerNum()+1, partnerRankDO2.getId());
			partnerRankDAO.changeOrderNum(partnerRankDO2.getOrderNum()+1, partnerRankDO2.getId());
		}
        
        List<OrderDO> selectBigThan = orderDao.selectBigThan(orderNum);
        for (OrderDO orderDO2 : selectBigThan) {
			orderDao.changeOrderNum(orderDO2.getOrderNum()+1, orderDO2.getOrderId());
		}
        
        orderDao.creatPartnerOrder(orderDo);
        partnerRankDAO.insert(partnerRankDO);
        
        return result;
    }

    //农村经纪人的默认商品添加  方法
    public int addPartnerProduct(String myShopId, String myUserId) {
        int result = 0;
        String shopId = "3320170116000180";
        //查询所有农村经纪人商品
        List<ProductDO> partnerProduct = productDao.selectPartnerProduct(shopId);

        for (ProductDO productDO : partnerProduct) {

            String oldShopId = productDO.getShopId();
            String productNo = productDO.getProductNo();

            //查询购买的商品信息(makeploy_product表)
            ProductDO myProduct = new ProductDO();
            myProduct.setShopId(myShopId);
            myProduct.setUserId(myUserId);
            myProduct.setProductNo(productNo);
            myProduct.setProductName(productDO.getProductName());
            myProduct.setProductType(productDO.getProductType());
            myProduct.setChildren(productDO.getChildren());
            myProduct.setTemplateId(productDO.getTemplateId());
            myProduct.setProductOff(productDO.getProductOff());
            myProduct.setPrice(productDO.getPrice());
            myProduct.setLuggage(productDO.getLuggage());
            myProduct.setUsePoint(productDO.getUsePoint());
            myProduct.setOldPrice(productDO.getOldPrice());
            myProduct.setSoleCount(0);
            myProduct.setCheckStatus("1");
            myProduct.setStatus("0");
            myProduct.setStock(productDO.getStock());
            myProduct.setProductUrl(productDO.getProductUrl());
            myProduct.setDelFlg("0");
            myProduct.setRecommendFlg("6");
            myProduct.setProportionReturn(productDO.getProportionReturn());
            myProduct.setProportionIntegral(productDO.getProportionIntegral());
            myProduct.setMemo(productDO.getMemo());

            result = productDao.addPartnerPro(myProduct);

            //查询购买的商品信息(makeploy_product_parameters表)
            List<ProductParameterDO> productParameters = productParameterDAO
                .selectInfoByShopIdAndProductNo(productNo, oldShopId);
            for (ProductParameterDO productParameterDO : productParameters) {
                ProductParameterDO myproParameter = new ProductParameterDO();
                myproParameter.setShopId(myShopId);
                myproParameter.setProductNo(productNo);
                myproParameter.setFatherName(productParameterDO.getFatherName());
                myproParameter.setChildrenName(productParameterDO.getChildrenName());
                myproParameter.setStock(productParameterDO.getStock());
                myproParameter.setParflag(productParameterDO.getParflag());

                result += productParameterDAO.addPartnerPara(myproParameter);
            }

            //查询购买的商品信息(makeploy_pro_image表)
            List<ProImageDO> selectAllProImage = proImageDAO
                .selectAllProImage(productNo, oldShopId);
            for (ProImageDO proImageDO : selectAllProImage) {
                ProImageDO myProImage = new ProImageDO();
                myProImage.setShopId(myShopId);
                myProImage.setProductNo(proImageDO.getProductNo());
                myProImage.setFlag(proImageDO.getFlag());
                myProImage.setImg(proImageDO.getImg());

                result += proImageDAO.addPartnerImage(myProImage);
            }
        }

        return result;
    }
    
    @RequestMapping("/changeProSellCount.html")
    public String changeProSellCount(HttpServletRequest request, ModelMap modelMap) {

    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
        
        return "mng/changeProSellCount";
    }
    
    /**
     * 查询店铺信息
     * @param request
     * @return
     */
    @RequestMapping("/selectshopBycell.do")
    @ResponseBody
    public Object selectshopBycell(HttpServletRequest request) {
    	
        final String shopCell = getParameterCheck(request, "shopCell");
        final String productNo = getParameterCheck(request, "productNo");
        
        final JsonResult result = new JsonResult(false);
        controllerTemplate.execute(result, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				
				ShopDO shopDO = shopDAO.selectShopInfoByShopCell(shopCell);
				if(null == shopDO){
					result.setInformation("1");
				}else{
					String shopId = shopDO.getShopId();
					ProductDO productDO = productDao.selectProductByProductNo(productNo, shopId);
					if(null == productDO){
						result.setInformation("1");
					}else{
					    result.setInformation("2");
					}
					result.setProductDO(productDO);
				}
				result.setShopDO(shopDO);
				result.setBizSucc(true);
			}
			
			@Override
			public void check() {
				// TODO Auto-generated method stub
				
			}
		});
        return result;
    }
    
    @RequestMapping("/changeShopCount.do")
    @ResponseBody
    public Object changeShopCount(HttpServletRequest request) {
    	
        final String shopId = getParameterCheck(request, "shopId");
        final String soleCount = getParameterCheck(request, "soleCount");
        final String productNo = getParameterCheck(request, "productNo");
        
        final JsonResult result = new JsonResult(false);
        controllerTemplate.execute(result, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				//查询修改前该商品的销售量
				ProductDO productDO = productDao.selectProductByProductNo(productNo, shopId);
				ShopDO shopDO = shopDAO.selectShopByShopId(shopId);
				if(null != productDO && null != shopDO){
					int oldsellCount  =  productDO.getSoleCount();
					int nowsellCount  = Integer.valueOf(soleCount);
					//增加的数量
					int changeCount = nowsellCount - oldsellCount;
					int oldShopSellCount = shopDO.getSellCount();
					int nowShopSellCount = oldShopSellCount + changeCount;
					//修改
					int result1 = productDao.changeProductSellCount(nowsellCount, shopId, productNo);
					int result2 = shopDAO.changeShopCellCount(nowShopSellCount, shopId);
					if(result1 > 0 && result2 > 0){
						result.setBizSucc(true);
				    	result.setErrMsg("修改成功");
				    }else{
				    	result.setErrMsg("修改失败");
					}
				}
			}
			
			@Override
			public void check() {
				// TODO Auto-generated method stub
				
			}
		});
        return result;
    }
    
    @RequestMapping("/addPartnerNum.do")
    @ResponseBody
    public Object addPartnerNum(HttpServletRequest request) {
        
        final JsonResult result = new JsonResult(true);
        controllerTemplate.execute(result, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				List<PartnerDO> allPartner = partnerDAO.selectAll();
				int partnerNum = 0;
				for (PartnerDO partnerDO : allPartner) {
					String partnerUserId = partnerDO.getPartnerUserId();
					partnerDAO.changePartnerNum(partnerNum, partnerUserId);
				}
				
			}
			
			@Override
			public void check() {
				// TODO Auto-generated method stub
				
			}
		});
        return result;
    }
    
    /**
     * 修改排单出局次数
     * @param request
     * @return
     */
    @RequestMapping("/changeOutNum.do")
    @ResponseBody
    public Object changeOutNum(HttpServletRequest request) {
        final String userId = request.getParameter("userId");
        final String orderId = request.getParameter("orderId");
        final String outNum = request.getParameter("outNum");
        
        final JsonResult result = new JsonResult(false);
        controllerTemplate.execute(result, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				
				if(!StringUtils.isNotBlank(outNum)){
					result.setBizSucc(false);
				}else{
					int nowNum = Integer.valueOf(outNum);
					if(nowNum >= 5){
						result.setBizSucc(false);
					}else{
						PartnerRankDO partnerRankDO = partnerRankDAO.selectByOrderIdAndUserId(orderId, userId);
						if(null == partnerRankDO){
							result.setBizSucc(false);
						}else{
							int numResult = partnerRankDAO.changeOutNum(nowNum, partnerRankDO.getId());
							if(numResult <= 0){
								result.setBizSucc(false);
							}else{
								result.setBizSucc(true);
							}
						}
					}
				}
			}
			@Override
			public void check() {
				// TODO Auto-generated method stub
				
			}
		});
        return result;
    }
    
    /**
     * 删除排单
     * @param request
     * @return
     */
    @RequestMapping("/deleteRank.do")
    @ResponseBody
    public Object deleteRank(HttpServletRequest request) {
        final String userId = request.getParameter("userId");
        final String orderId = request.getParameter("orderId");
        
        final JsonResult result = new JsonResult(false);
        controllerTemplate.execute(result, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				//查询当前排单
				PartnerRankDO partnerRankDO = partnerRankDAO.selectByOrderIdAndUserId(orderId, userId);
				if(null != partnerRankDO){
					int partnerNum = partnerRankDO.getPartnerNum();
					//把所有大于该partnerNum的排单查出   -1
					List<PartnerRankDO> bigThanList = partnerRankDAO.selectAllBigThan(partnerNum);
					for (PartnerRankDO partnerRankDO2 : bigThanList) {
						int newPartnerNum = partnerRankDO2.getPartnerNum() - 1; 
						partnerRankDAO.changePartnerNum(newPartnerNum, partnerRankDO2.getId());
					}
					int i = partnerRankDAO.delete(partnerRankDO.getId());
					if(i > 0){
						result.setBizSucc(true);
					}
				}
			}
			@Override
			public void check() {
				// TODO Auto-generated method stub
				
			}
		});
        return result;
    }
    
    /**
     * 修改排单备注
     * @param request
     * @return
     */
    @RequestMapping("/rankMemo.do")
    @ResponseBody
    public Object rankMemo(HttpServletRequest request) {
        final String userId = request.getParameter("userId");
        final String orderId = request.getParameter("orderId");
        final String memo = request.getParameter("memo");
        
        final JsonResult result = new JsonResult(false);
        controllerTemplate.execute(result, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				//查询当前排单
				PartnerRankDO partnerRankDO = partnerRankDAO.selectByOrderIdAndUserId(orderId, userId);
				if(null != partnerRankDO){
					
					int i = partnerRankDAO.changePartnerMemo(memo, partnerRankDO.getId());
					int j = orderDao.insertCreate(memo, orderId);
					if(i > 0 && j > 0){
						result.setBizSucc(true);
					}
				}
			}
			@Override
			public void check() {
				// TODO Auto-generated method stub
				
			}
		});
        return result;
    }
    
    /**
     * 修改排单人id
     * @param request
     * @return
     */
    @RequestMapping("/changeUserId.do")
    @ResponseBody
    public Object changeUserId(HttpServletRequest request) {
        final String userId = request.getParameter("userId");
        final String orderId = request.getParameter("orderId");
        final String changeUserId = request.getParameter("changeUserId");
        
        final JsonResult result = new JsonResult(false);
        controllerTemplate.execute(result, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				UserDO userDO = userDao.selectUserInfoByUserId(changeUserId);
				if(null == userDO){
					result.setBizSucc(false);
					result.setErrMsg("用户不存在！");
				}else{
					//查询当前排单
					PartnerRankDO partnerRankDO = partnerRankDAO.selectByOrderIdAndUserId(orderId, userId);
					if(null != partnerRankDO){
						int i = partnerRankDAO.changePartnerUserId(changeUserId, partnerRankDO.getId());
						int j = orderDao.updateOrderUserId(changeUserId, orderId);
						if(i > 0 && j > 0){
							result.setBizSucc(true);
						}
					}
				}
			}
			@Override
			public void check() {
				// TODO Auto-generated method stub
				
			}
		});
        return result;
    }
    
    /**
     * 最新出局卡号
     * @param request
     * @return
     */
    @RequestMapping("/changeOutRankNo.do")
    @ResponseBody
    public Object changeOutRankNo(HttpServletRequest request) {
        final String outRankNo = request.getParameter("outRankNo");//排行
        
        final JsonResult result = new JsonResult(false);
        controllerTemplate.execute(result, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				if(StringUtils.isBlank(outRankNo)){
					result.setBizSucc(false);
					result.setErrMsg("错误信息！");
				}else{
					PartnerRankDO partnerRankDO = partnerRankDAO.selectByPartnerNum(Integer.valueOf(outRankNo));
					if(null == partnerRankDO){
						result.setBizSucc(false);
						result.setErrMsg("无此排单顺序！");
					}else{
						int updateRate = sysConfigDAO.updateRate(partnerRankDO.getOrderId(), "OUT_RANK_NO");
						if(updateRate >= 1){
							result.setBizSucc(true);
						}else{
							result.setBizSucc(false);
							result.setErrMsg("操作失败！");
						}
					}
				}
			}
			@Override
			public void check() {
				// TODO Auto-generated method stub
				
			}
		});
        return result;
    }
}
