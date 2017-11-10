/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.web.controller;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.onway.cif.common.util.FileUtils;
import com.onway.cif.common.util.ImageUploadUtil;
import com.onway.cif.core.constants.ParamErrorException;
import com.onway.common.lang.ArrayUtils;
import com.onway.common.lang.StringUtils;
import com.onway.makeploy.common.dal.daointerface.AccountDAO;
import com.onway.makeploy.common.dal.daointerface.ActivityDAO;
import com.onway.makeploy.common.dal.daointerface.AddressDAO;
import com.onway.makeploy.common.dal.daointerface.AgentDAO;
import com.onway.makeploy.common.dal.daointerface.AreaDAO;
import com.onway.makeploy.common.dal.daointerface.BorrowMoneyDAO;
import com.onway.makeploy.common.dal.daointerface.CarrymodeDAO;
import com.onway.makeploy.common.dal.daointerface.CategoryDAO;
import com.onway.makeploy.common.dal.daointerface.CollectionDAO;
import com.onway.makeploy.common.dal.daointerface.CommerceCheckDAO;
import com.onway.makeploy.common.dal.daointerface.CommerceDAO;
import com.onway.makeploy.common.dal.daointerface.DiscountDAO;
import com.onway.makeploy.common.dal.daointerface.FaretemplateDAO;
import com.onway.makeploy.common.dal.daointerface.MsgDAO;
import com.onway.makeploy.common.dal.daointerface.OptionDAO;
import com.onway.makeploy.common.dal.daointerface.OrderDAO;
import com.onway.makeploy.common.dal.daointerface.PartnerDAO;
import com.onway.makeploy.common.dal.daointerface.PartnerRankDAO;
import com.onway.makeploy.common.dal.daointerface.ProImageDAO;
import com.onway.makeploy.common.dal.daointerface.ProductCommentDAO;
import com.onway.makeploy.common.dal.daointerface.ProductDAO;
import com.onway.makeploy.common.dal.daointerface.ProductParameterDAO;
import com.onway.makeploy.common.dal.daointerface.ProductPromotionDAO;
import com.onway.makeploy.common.dal.daointerface.RoleDAO;
import com.onway.makeploy.common.dal.daointerface.SequenceDAO;
import com.onway.makeploy.common.dal.daointerface.ShopCheckDAO;
import com.onway.makeploy.common.dal.daointerface.ShopCommentDAO;
import com.onway.makeploy.common.dal.daointerface.ShopDAO;
import com.onway.makeploy.common.dal.daointerface.ShopIncomeDAO;
import com.onway.makeploy.common.dal.daointerface.ShoppingCartDAO;
import com.onway.makeploy.common.dal.daointerface.SysConfigDAO;
import com.onway.makeploy.common.dal.daointerface.TeamGoDAO;
import com.onway.makeploy.common.dal.daointerface.TeamIncomeDAO;
import com.onway.makeploy.common.dal.daointerface.UserDAO;
import com.onway.makeploy.common.dal.daointerface.VerifyCodeDAO;
import com.onway.makeploy.common.dal.dataobject.RoleDO;
import com.onway.makeploy.common.service.exception.ErrorException;
import com.onway.makeploy.common.service.integration.account.AccountInfoQueryServiceClient;
import com.onway.makeploy.core.localcache.manager.SysConfigCacheManager;
import com.onway.makeploy.core.service.code.CodeGenerateComponent;
import com.onway.web.controller.template.ControllerTemplate;

/**
 * 控制器基类
 * 
 * @author guangdong.li
 * @version $Id: AbstractController.java, v 0.1 17 Feb 2016 11:18:55 guangdong.li Exp $
 */
public class AbstractController {
    /**  */
    private static final Pattern   SPLIT_PATTERN = Pattern.compile("_");

    /** logger */
    public static final Logger    logger        = Logger.getLogger(AbstractController.class);
    
    protected static String       PAGE_NUM_STR      = "pageNum";

    protected static String       PAGE_SIZE_STR     = "pageSize";

    protected static int          DEFAULT_PAGE_SIZE = 10;

    protected static int          DEFAULT_PAGE_NUM  = 1;

    protected static final String CURRENTPAGE       = "currentPage";

    protected static final String OFFSET            = "offset";

    protected static final String LIMIT             = "limit";

    protected static final String PAGE_FLAG         = "page";
    

    protected static final String   TOKEN_ERROR                  = "token不正确";

    protected static final String   USER_ID                      = "userId";

    protected static final String   TOKEN                        = "token";

    protected static final String   APP_TYPE                     = "appType";

    protected static final String   VERSION                      = "version";

    protected static final String   SIGN_T                       = "sign_t";

    protected static final String   SIGN                         = "sign";

    protected static final String   PAGE_NUM                     = "pageNum";

    protected static final String   TIME                         = "stime";

    protected static final String   CHECK_CODE                   = "checkCode";

    protected static final String   SIGN_SEED                    = "onway888888";

    protected static final String   PROD_CODE                    = "prodCode";

    protected static final int      PAGE_NUM_DIGIT               = 1;

    protected static final int      PAGE_SIZE_DIGIT              = 10;

    protected static final String   TRANSCODING_ERROR            = "编码方式转型异常";

    protected static final String   UPLOAD_SUCCESS               = "上传成功";

    protected static final String   UPLOAD_ERROR                 = "上传异常";

    protected DecimalFormat         dfZero                       = new DecimalFormat("0");

    protected DecimalFormat         dfDigit                      = new DecimalFormat("0.00");

    private static final String PAGE_NO                          = "pageNo";

    private static final String PAGE_SIZE                        = "pageSize";
    
    protected static final String   CLOSE                        = "CLOSE";
    
    protected static final String   OPEN                         = "OPEN";


    /****************************所有的Client********************************/
    
    @Resource
    protected AccountInfoQueryServiceClient   accountInfoQueryServiceClient;
    
    
    /****************************所有的Component（组成）********************************/
    @Resource
    protected ControllerTemplate      controllerTemplate;

    @Resource
    protected CodeGenerateComponent   codeGenerateComponent;

    @Resource
    protected SysConfigCacheManager   sysConfigCacheManager;
    
    /****************************所有的DAO********************************/
    
    @Resource
    protected AccountDAO       accountDao;
    @Resource
    protected AgentDAO         agentDao;
    
    @Resource
    protected ActivityDAO      activityDao;
    
    @Resource
    protected AddressDAO       addressDao;
    
    @Resource
    protected BorrowMoneyDAO       borrowMoneyDao;
    
    @Resource
    protected CollectionDAO    collectionDao;
    
    @Resource
    protected DiscountDAO      discountDao;
    
    @Resource
    protected MsgDAO           msgDao;
    
    @Resource
    protected OptionDAO        optionDao;
    
    @Resource
    protected OrderDAO         orderDao;
    
    @Resource
    protected ProductDAO       productDao;
    
    @Resource
    protected ProductCommentDAO productCommentDAO;
    
    @Resource
    protected ProductParameterDAO productParameterDAO;
    
    @Resource
    protected RoleDAO          roleDao;
    
    @Resource
    protected SequenceDAO      sequenceDAO;
    
    @Resource
    protected ShopCommentDAO   shopCommentDAO;
    
    @Resource
    protected ShopDAO          shopDAO;
    
    @Resource
    protected ShopCheckDAO 			shopCheckDAO;
    
    @Resource
    protected ShopIncomeDAO    shopIncomeDAO;
    
    @Resource
    protected SysConfigDAO     sysConfigDAO;
    
    @Resource
    protected TeamIncomeDAO    teamIncomeDAO;
    
    @Resource
    protected TeamGoDAO        teamGoDAO;
    
    @Resource
    protected UserDAO          userDao;
    
    @Resource
    protected VerifyCodeDAO    verifyCodeDAO;
    
    @Resource
    protected ShoppingCartDAO  shoppingCartDAO;
    
    @Resource
    protected CategoryDAO categoryDAO;
    
    @Resource
    protected ProImageDAO proImageDAO;
    
    @Resource
    protected PartnerDAO partnerDAO;
    
    @Resource
    protected AreaDAO areaDAO;
    
    @Resource
    protected FaretemplateDAO faretemplateDAO;
    
    @Resource
    protected CarrymodeDAO carrymodeDAO;
    
    @Resource
    protected ProductPromotionDAO productPromotionDAO;
    
    @Resource
    protected CommerceDAO commerceDAO;
    
    @Resource
    protected CommerceCheckDAO commerceCheckDAO;
    
    @Resource
    protected PartnerRankDAO partnerRankDAO;
    
    
//    @Resource
//    protected SmsSender SmsSender;
    /**
     * 获取页码
     * @param request
     * @return
     */
    public int adjustPageNo(HttpServletRequest request){
        String pageNo = request.getParameter(PAGE_NO);
        if(pageNo == null || !NumberUtils.isDigits(pageNo))
            return PAGE_NUM_DIGIT;
        return Integer.parseInt(pageNo) < 1 ? PAGE_NUM_DIGIT : Integer.parseInt(pageNo);
    }

    /**
     * 获取每页显示条数
     * @param request
     * @return
     */
    public int adjustPageSize(HttpServletRequest request){
        String pageSize = request.getParameter(PAGE_SIZE);
        if(pageSize == null || !NumberUtils.isDigits(pageSize))
            return PAGE_SIZE_DIGIT;
        return Integer.parseInt(pageSize) < 1 ? PAGE_SIZE_DIGIT : Integer.parseInt(pageSize);
    }

    /**
     * 转换Double
     * @param d
     * @return
     */
    public Double adjustDouble(String d){
        if(d == null)
            return null;
        if(NumberUtils.isNumber(d)){
            return Double.valueOf(d);
        }
        return null;
    }

    /**
     * 获取分页数据
     * 
     * @param request
     */
    protected Map<String, Integer> getPageData(final HttpServletRequest request) {

        String page = request.getParameter(CURRENTPAGE);
        String pageSize = request.getParameter(PAGE_SIZE_STR);

        // 分页页数
        int pagesize = StringUtils.hasLength(pageSize) && StringUtils.isNumeric(pageSize) ? Integer
            .parseInt(pageSize) : DEFAULT_PAGE_SIZE;

        int currentPage = 1;
        int offset = 0;

        if (StringUtils.hasLength(page) && StringUtils.isNumeric(page)) {
            currentPage = Integer.parseInt(page);
            offset = (currentPage - 1) * pagesize;
        }

        Map<String, Integer> pageMap = new HashMap<String, Integer>();
        pageMap.put(PAGE_SIZE_STR, pagesize);
        pageMap.put(CURRENTPAGE, currentPage);
        pageMap.put(OFFSET, offset);
        pageMap.put(LIMIT, (currentPage) * pagesize);
        return pageMap;
    }
    /**
     * 对得到值做 Null 空字符串 处理
     * @param request
     * @param key
     * @return
     */
    protected String getParameterCheck(final HttpServletRequest request,String key){
    	String Value = request.getParameter(key);
    	if(null==Value||Value.equals("")){
    		Value=null;
		 }
    	return Value;
    }

    /**
     * 计算页数
     * 
     * @param totalItems
     * @return
     */
    protected int calculatePage(int totalItems, int pagesize) {
        int totalPages = 0;
        if (0 != totalItems && totalItems > pagesize) {
            totalPages = 0 == totalItems % pagesize ? totalItems / pagesize : totalItems / pagesize
                                                                              + 1;
        } else if (0 != totalItems && totalItems <= pagesize) {
            totalPages = 1;
        }
        return totalPages;
    }



    /**
     * 获取客户端访问ip地址
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = (String) request.getParameter("loginIP"); // 兼容PC端请求IP记录
        if (StringUtils.isBlank(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StringUtils.isBlank(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (StringUtils.isBlank(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 
     * @param request
     * @return
     */
    protected String getUserId(HttpServletRequest request) {
        String key = "AdD53fE9BCB5E6Db";
        String sign = request.getHeader("Sign");

        if (StringUtils.isEmpty(sign)) {
            sign = request.getParameter("Sign");
        }

        if (StringUtils.isEmpty(sign)) {
            return "";
        }

        //hellomyson_8201409090003422_1370****031_acabd0a7c1f943b5a5f1ec2b50b8adc9
        String text = decode(sign, key);
        String[] arrays = SPLIT_PATTERN.split(text, 4);

        if (arrays == null | arrays.length != 4) {
            return "";
        }

        String userId = arrays[1];

        if (StringUtils.isBlank(userId)) {
            return "";
        }
        return userId;
    }

    /**
     * AES解码
     * 
     * @param sign 加密串
     * @param key 秘钥
     * @return
     */
    public String decode(String sign, String key) {
        if (StringUtils.isEmpty(sign)) {
            return "";
        }
        try {
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, keyspec);
            byte[] b = cipher.doFinal(org.apache.commons.codec.binary.Base64.decodeBase64(sign));
            return new String(b);
        } catch (Exception e) {
            logger.error("", e);
        }
        return "";
    }

    public String encode(String sign, String key) {
        if (StringUtils.isEmpty(sign)) {
            return "";
        }
        try {
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");

            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = sign.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            cipher.init(Cipher.ENCRYPT_MODE, keyspec);
            byte[] b = cipher.doFinal(plaintext);
            return org.apache.commons.codec.binary.Base64.encodeBase64String(b);
        } catch (Exception e) {
            logger.error("", e);
        }
        return "";
    }

    /**
     * 
     * 是否小于指定版本 （向后兼容）
     * @param currentVersion
     * @param oldVersion
     * @param platform
     * @return
     */
    public boolean isSupport(String currentVersion, String oldVersion) {
        try {
            return compareVersion(currentVersion, oldVersion) < 0;
        } catch (Exception e) {
            logger.error("APP版本向下兼容判断", e);
        }
        return false;
    }

    /**
     * 比较版本高低，version1 &lt; version2 返回-1 ，等于返回0，大于返回1
     * 
     * @param version1
     * @param version2
     * @return
     * @throws Exception
     */
    public static int compareVersion(String version1, String version2) {
        if (version1 == null || version2 == null) {
            throw new ParamErrorException("版本号格式错误");
        }
        String[] versionArray1 = version1.split("\\.");
        String[] versionArray2 = version2.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值  
        int diff = 0;
        while (idx < minLength
               && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度  
               && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符  
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；  
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }
    
    /**
     *  文件上传(图片上传) 
     * @param txtFile
     * @param uploadRealpath
     * @param filePath
     * @return
     */
    public  String uploadImage(MultipartFile txtFile, String uploadRealpath, String baseUrl) {
        // 检查照片是否存在
        if (txtFile.getOriginalFilename() == null || "".equals(txtFile.getOriginalFilename())) {
            throw new ErrorException("图片后缀名为空");
        }
        // 照片上传
        File file = ImageUploadUtil.getFile(txtFile, uploadRealpath, "");

        // 获取文件访问地址
        String visitPath = uploadRealpath+file.getName();

        return visitPath;
    } 
    
    /*
     * 上传图片 
     */
     public void uploadFile(MultipartFile image, String absolutePath) throws Exception {

         File userAuthImage = new File(absolutePath);
         if (image == null || ArrayUtils.isEmpty(image.getBytes())) {
         }
         FileUtils.writeByteArrayToFile(userAuthImage, image.getBytes());
     }
    
     /**
      * `ROLE` varchar(32) DEFAULT NULL COMMENT '权限(0:商户 1:管理员 2:区域代理)',
      * @param userId
      * @return
      */
     public boolean checkRoleShop(String userId){
    	 boolean result = false;
    	 
    	 List<RoleDO> roleList = roleDao.selectUserRole(userId);
    	 for (RoleDO roleDO : roleList) {
			if(StringUtils.equals(roleDO.getRole(), "0")){
				result = true;
				break;
			}
		 }
    	 
    	 return result;
     }
     
     public boolean checkRoleArea(String userId){
    	 boolean result = false;
    	 
    	 List<RoleDO> roleList = roleDao.selectUserRole(userId);
    	 for (RoleDO roleDO : roleList) {
			if(StringUtils.equals(roleDO.getRole(), "2")){
				result = true;
				break;
			}
		 }
    	 return result;
     }
     
     public boolean checkRoleBoss(String userId){
    	 boolean result = false;
    	 
    	 List<RoleDO> roleList = roleDao.selectUserRole(userId);
    	 for (RoleDO roleDO : roleList) {
			if(StringUtils.equals(roleDO.getRole(), "1")){
				result = true;
				break;
			}
		 }
    	 return result;
     }
     
     public int checkOperative(String userId){
    	 //判断是否为运营者
         RoleDO roleDO = roleDao.checkOperativeByUserId(userId);
         int checkOperative = 0;
         if(null != roleDO){
         	if(StringUtils.equals(roleDO.getMemo(), "1")){
         		checkOperative = 1;
         	}
         }
         
         return checkOperative;
     }
}
