package com.onway.web.controller.mng;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.onway.makeploy.common.dal.dataobject.ActivityDO;
import com.onway.platform.common.configration.ConfigrationFactory;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;

/**
 * @author jianyong.jiang
 * activityģ��
 *
 */
@Controller
public class ActivityMngController extends AbstractController {
    private static final String IMAGE_FILE = ConfigrationFactory.getConfigration()
                                               .getPropertyValue("user_img_upload_realpath");

    private static final String IMAGE_PATH = ConfigrationFactory.getConfigration()
                                               .getPropertyValue("user_img_path");

    @RequestMapping("/homepage.go")
    public String homepage(HttpServletRequest request, ModelMap modelMap) {
        System.out.println("��̨����---");

        return "mng/index";

    }

    /**
     * notice ��صĲ���
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("/noticeMng.html")
    public String imgMng(HttpServletRequest request, ModelMap modelMap) {
    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	
    	//�ж��Ƿ�Ϊ��Ӫ��
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
    	
        List<ActivityDO> noticeInfo = activityDao.selectInfoByType("1");
        modelMap.addAttribute("noticeInfoList", noticeInfo);
        return "mng/noticeMng";
    }

    @RequestMapping("/notice.do")
    @ResponseBody
    public Object noticeDelete(HttpServletRequest request, ModelMap modelMap) {
        JsonResult result = new JsonResult(false);
        int noticeId = Integer.parseInt(request.getParameter("noticeId"));
        String flag = request.getParameter("flag");// flag=1 ɾ��
                                                   // flag=2����state�ö�
        if ("1".equals(flag)) {
            int i = activityDao.deleteActivityById(noticeId);
            if (i > 0) {
                result.setBizSucc(true);
                int x = 0;
                for (ActivityDO acDo : activityDao.selectInfoByType("1")) {
                    if (acDo.getState().equals("1")) {
                        x++;
                    }
                }
                if (x > 0) {
                    result.setInformation("ɾ���ɹ�!");
                } else {
                    result.setInformation("ɾ���ɹ�,�����·������ö�һ������!");
                }
            } else {
                result.setInformation("ɾ��ʧ��!");
            }
        } else if ("2".equals(flag)) {
            activityDao.resetStateByType("1");
            int i = activityDao.updateStateById("1", noticeId);
            if (i > 0) {
                result.setBizSucc(true);
                result.setInformation("�ö��ɹ�!");
            } else {
                result.setInformation("ָ������ʧ��!");
            }
        }
        return result;
    }

    @RequestMapping("/addNotice.do")
    @ResponseBody
    public Object addNotice(HttpServletRequest request, ModelMap modelMap) {
        JsonResult result = new JsonResult(false);
        String title = request.getParameter("title");
        String info = request.getParameter("noticeinfo");
        activityDao.resetStateByType("1");
        ActivityDO noticeInfo = new ActivityDO();
        noticeInfo.setTitle(title);
        noticeInfo.setInfo(info);
        noticeInfo.setType("1");
        noticeInfo.setState("1");//�·����Ĺ���Ĭ���ö�
        int i = activityDao.addInfo(noticeInfo);
        if (i > 0) {
            result.setBizSucc(true);
            return result;
        }
        return result;
    }

    /**
     * banner ��صĲ���
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("/bannerMng.html")
    public String bannerMng(HttpServletRequest request, ModelMap modelMap) {
    	
    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	
    	//�ж��Ƿ�Ϊ��Ӫ��
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
    	
        List<ActivityDO> bannerList = activityDao.selectInfoByType("2");
        modelMap.addAttribute("bannerList", bannerList);//banner

        List<ActivityDO> mallPicList = activityDao.selectInfoByType("6");
        modelMap.addAttribute("mallPic", mallPicList);//mallPic

        List<ActivityDO> teamPicList = activityDao.selectInfoByType("7");
        modelMap.addAttribute("teamPic", teamPicList);//teamPic
        
        List<ActivityDO> partnerPicList = activityDao.selectInfoByType("5");
        modelMap.addAttribute("partnerPic", partnerPicList);//partnerPic
        
        List<ActivityDO> commercePicList = activityDao.selectInfoByType("9");
        modelMap.addAttribute("commercePic", commercePicList);//commercePic
        
        List<ActivityDO> topPicList = activityDao.selectInfoByType("11");
        modelMap.addAttribute("topPic", topPicList);//topPic
        /*bannerOne
        if(!bannerList.isEmpty()){
        int banner1Id=bannerList.get(0).getId();
        String banner1url=bannerList.get(0).getPoster();
        modelMap.addAttribute("banner1Id", banner1Id);
        modelMap.addAttribute("banner1url", banner1url);
        bannerTwo
        int banner2Id=bannerList.get(1).getId();
        String banner2url=bannerList.get(0).getPoster();
        modelMap.addAttribute("banner2Id", banner2Id);
        modelMap.addAttribute("banner2url", banner2url);
        bannerThree
        int banner3Id=bannerList.get(2).getId();
        String banner3url=bannerList.get(0).getPoster();
        modelMap.addAttribute("banner3Id", banner3Id);
        modelMap.addAttribute("banner3url", banner3url);
        }*/
        return "mng/bannerMng";
    }
    
    /**
     * banner ��صĲ���
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("/bannerCommerceMng.html")
    public String bannerCommerceMng(HttpServletRequest request, ModelMap modelMap) {
    	
    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	
    	//�ж��Ƿ�Ϊ��Ӫ��
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
    	
        
        List<ActivityDO> commercePicList = activityDao.selectInfoByType("9");
        modelMap.addAttribute("commercePic", commercePicList);//commercePic
        
        return "mng/bannerCommerceMng";
    }

    @RequestMapping("/uploadBanner.do")
    public String uploadBanner(HttpServletRequest request, ModelMap modelMap,
                               @RequestParam MultipartFile newBanner) {
        String bannerId = request.getParameter("bannerId");

        StringBuffer newBannerPath = new StringBuffer();
        newBannerPath.append(String.valueOf(System.currentTimeMillis())).append(".jpg");
        String bannerPath = IMAGE_FILE + newBannerPath;
        try {
            ImageZipUtil.compressPic(newBanner, bannerPath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //���idΪ�գ�˵��û��banner�����ϴ�����Ҫ��ӽ���
        if (null == bannerId || bannerId.equals("")) {
            ActivityDO bannerDo = new ActivityDO();
            bannerDo.setPoster(IMAGE_PATH + newBannerPath + "");
            bannerDo.setType("2");
            bannerDo.setState("1");
            activityDao.addInfo(bannerDo);
        } else {

            activityDao.updateBannerById(IMAGE_PATH + newBannerPath + "",
                Integer.parseInt(bannerId));
        }

        /*������Ⱦҳ��*/
        List<ActivityDO> bannerList = activityDao.selectInfoByType("2");
        modelMap.addAttribute("bannerList", bannerList);//banner

        List<ActivityDO> mallPicList = activityDao.selectInfoByType("6");
        modelMap.addAttribute("mallPic", mallPicList);//mallPic

        List<ActivityDO> teamPicList = activityDao.selectInfoByType("7");
        modelMap.addAttribute("teamPic", teamPicList);//teamPic
        
        List<ActivityDO> partnerPicList = activityDao.selectInfoByType("5");
        modelMap.addAttribute("partnerPic", partnerPicList);//partnerPic
        
        List<ActivityDO> commercePicList = activityDao.selectInfoByType("9");
        modelMap.addAttribute("commercePic", commercePicList);//commercePic
        
        List<ActivityDO> topPicList = activityDao.selectInfoByType("11");
        modelMap.addAttribute("topPic", topPicList);//topPic
        
        return "mng/bannerMng";
    }
    
    @RequestMapping("/uploadCommerceBanner.do")
    public String uploadCommerceBanner(HttpServletRequest request, ModelMap modelMap,
                               @RequestParam MultipartFile newBanner) {
        String bannerId = request.getParameter("bannerId");

        StringBuffer newBannerPath = new StringBuffer();
        newBannerPath.append(String.valueOf(System.currentTimeMillis())).append(".jpg");
        String bannerPath = IMAGE_FILE + newBannerPath;
        try {
            ImageZipUtil.compressPic(newBanner, bannerPath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //���idΪ�գ�˵��û��banner�����ϴ�����Ҫ��ӽ���
        if (null == bannerId || bannerId.equals("")) {
            ActivityDO bannerDo = new ActivityDO();
            bannerDo.setPoster(IMAGE_PATH + newBannerPath + "");
            bannerDo.setType("9");
            bannerDo.setState("1");
            activityDao.addInfo(bannerDo);
        } else {

            activityDao.updateBannerById(IMAGE_PATH + newBannerPath + "",
                Integer.parseInt(bannerId));
        }

        /*������Ⱦҳ��*/
        List<ActivityDO> bannerList = activityDao.selectInfoByType("2");
        modelMap.addAttribute("bannerList", bannerList);//banner

        List<ActivityDO> mallPicList = activityDao.selectInfoByType("6");
        modelMap.addAttribute("mallPic", mallPicList);//mallPic

        List<ActivityDO> teamPicList = activityDao.selectInfoByType("7");
        modelMap.addAttribute("teamPic", teamPicList);//teamPic
        
        List<ActivityDO> partnerPicList = activityDao.selectInfoByType("5");
        modelMap.addAttribute("partnerPic", partnerPicList);//partnerPic
        
        List<ActivityDO> commercePicList = activityDao.selectInfoByType("9");
        modelMap.addAttribute("commercePic", commercePicList);//commercePic
        
        List<ActivityDO> topPicList = activityDao.selectInfoByType("11");
        modelMap.addAttribute("topPic", topPicList);//topPic
        
        return "mng/bannerCommerceMng";
    }

    @RequestMapping("/uploadPic.do")
    public String uploadPic(HttpServletRequest request, ModelMap modelMap,
                            @RequestParam MultipartFile newBanner) {
        String picId = request.getParameter("picId");
        //		String picType = request.getParameter("type");
        StringBuffer newBannerPath = new StringBuffer();
        newBannerPath.append(String.valueOf(System.currentTimeMillis())).append(".jpg");
        String bannerPath = IMAGE_FILE + newBannerPath;
        try {
            ImageZipUtil.compressPic(newBanner, bannerPath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //���idΪ�գ�˵��û��banner�����ϴ�����Ҫ��ӽ���

        activityDao.updateBannerById(IMAGE_PATH + newBannerPath + "", Integer.parseInt(picId));

        /*������Ⱦҳ��*/
        List<ActivityDO> bannerList = activityDao.selectInfoByType("2");
        modelMap.addAttribute("bannerList", bannerList);//banner

        List<ActivityDO> mallPicList = activityDao.selectInfoByType("6");
        modelMap.addAttribute("mallPic", mallPicList);//mallPic

        List<ActivityDO> teamPicList = activityDao.selectInfoByType("7");
        modelMap.addAttribute("teamPic", teamPicList);//teamPic
        List<ActivityDO> partnerPicList = activityDao.selectInfoByType("5");
        modelMap.addAttribute("partnerPic", partnerPicList);//partnerPic
        
        List<ActivityDO> commercePicList = activityDao.selectInfoByType("9");
        modelMap.addAttribute("commercePic", commercePicList);//commercePic
        
        List<ActivityDO> topPicList = activityDao.selectInfoByType("11");
        modelMap.addAttribute("topPic", topPicList);//topPic

        return "mng/bannerMng";
    }

    @RequestMapping("newBanner.do")
    @ResponseBody
    public Object newBanner() {
        JsonResult result = new JsonResult(false);
        ActivityDO activity = new ActivityDO();
        activity.setType("2");
        activity.setPoster("images/blank.png");
        activity.setUrl("#");
        activity.setState("1");
        int i = activityDao.addInfo(activity);
        if (i > 0) {
            result.setBizSucc(true);
            return result;
        }
        return result;
    }
    
    @RequestMapping("newCommerceBanner.do")
    @ResponseBody
    public Object newCommerceBanner() {
        JsonResult result = new JsonResult(false);
        ActivityDO activity = new ActivityDO();
        activity.setType("9");
        activity.setPoster("images/blank.png");
        activity.setUrl("#");
        activity.setState("1");
        int i = activityDao.addInfo(activity);
        if (i > 0) {
            result.setBizSucc(true);
            return result;
        }
        return result;
    }

    @RequestMapping("deleteBanner.do")
    @ResponseBody
    public Object deleteBanner(HttpServletRequest request, ModelMap map) {
        JsonResult result = new JsonResult(false, null, "����ʧ��!");
        int bannerId = Integer.parseInt(request.getParameter("bannerId"));
        int i = activityDao.deleteActivityById(bannerId);
        if (i > 0) {
            result.setBizSucc(true);
            result.setErrMsg("�����ɹ�");
            return result;
        }
        return result;
    }

}
