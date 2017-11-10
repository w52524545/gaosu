package com.onway.web.controller.mng;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.common.lang.StringUtils;
import com.onway.makeploy.common.dal.dataobject.PartnerRankDO;
import com.onway.makeploy.common.dal.dataobject.TeamIncomeDO;
import com.onway.makeploy.common.dal.dataobject.UserDO;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;
import com.onway.web.controller.template.ControllerCallBack;

@Controller
public class UserInfoMngController extends AbstractController {
    @RequestMapping("userInfoMng.html")
    public String searchUserInfo(HttpServletRequest request, ModelMap map) {
    	String userIds = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userIds);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userIds);
        map.put("checkOperative", checkOperative);
        String userId = request.getParameter("userId");
        String cell = request.getParameter("cell");
        String userNickName = request.getParameter("userNickName");

        int totalItems = (int) userDao.count(userId, cell,userNickName);
        Map<String, Integer> pageData = getPageData(request);
        List<UserDO> userInfo = userDao.userInfo(StringUtils.trim(userId), StringUtils.trim(cell),StringUtils.trim(userNickName),
            pageData.get(OFFSET), pageData.get(PAGE_SIZE_STR));
        List<UserInfo>  list = new ArrayList<UserInfo>();
        for (UserDO userDO : userInfo) {
        	UserInfo userInfo2 = new UserInfo();
        	userInfo2.setUserDO(userDO);
        	int pointAccout = userDO.getPointAccout();
        	double poAcount = pointAccout;
        	double point = poAcount/100;
        	userInfo2.setPointCount(point);
        	list.add(userInfo2);
		}
        map.put("totalPages", calculatePage(totalItems, pageData.get(PAGE_SIZE_STR)));
        map.put(CURRENTPAGE, pageData.get(CURRENTPAGE));
        map.put("totalItems", totalItems);
        map.put("userInfo", list);
        map.put("userId", userId);
        map.put("cell", cell);
        map.put("userNickName", userNickName);
        request.setAttribute("page", map);
        return "mng/userInfoMng";
    }
    
    @RequestMapping("partnerInfoMng.html")
    public String partnerInfoMng(HttpServletRequest request, ModelMap map) {
    	String userIds = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userIds);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userIds);
        map.put("checkOperative", checkOperative);
        
        String partnerUserId = request.getParameter("userId");
        String orderId = request.getParameter("orderId");
        int totalItems = (int) partnerRankDAO.selectPartnerUserIdCount(partnerUserId,orderId);
        Map<String, Integer> pageData = getPageData(request);
        List<PartnerRankDO> partnerUserIds = partnerRankDAO.selectPartnerUserId(StringUtils.trim(partnerUserId), StringUtils.trim(orderId),pageData.get(OFFSET), pageData.get(PAGE_SIZE_STR));
        List<UserInfo>  list = new ArrayList<UserInfo>();
        for (PartnerRankDO partnerRankDO : partnerUserIds) {
        	UserInfo userInfo = new UserInfo();
        	String userId = partnerRankDO.getUserId();
        	UserDO userDO = userDao.selectUserInfoByUserId(userId);
        	if(null != userDO){
        		userInfo.setUserDO(userDO);
            	int pointAccout = userDO.getPointAccout();
            	double poAcount = pointAccout;
            	double point = poAcount/100;
            	userInfo.setPointCount(point);
        	}
        	userInfo.setPartnerRankDO(partnerRankDO);
        	list.add(userInfo);
		}
        String outRankNo = sysConfigDAO.selectByKey("OUT_RANK_NO").getSysValue();
        map.put("outRankNo", outRankNo);
        
        map.put("totalPages", calculatePage(totalItems, pageData.get(PAGE_SIZE_STR)));
        map.put(CURRENTPAGE, pageData.get(CURRENTPAGE));
        map.put("totalItems", totalItems);
        map.put("userInfo", list);
        map.put("userId", partnerUserId);
        map.put("orderId", orderId);
        request.setAttribute("page", map);
        return "mng/partnerInfoMng";
    }

    @RequestMapping("addHighUser.do")
    @ResponseBody
    public Object addHighUser(HttpServletRequest request, ModelMap map) {
        JsonResult result = new JsonResult(false);
        String userId = request.getParameter("userId");
        String highUserCell = request.getParameter("highUserCell");
        UserDO highUserDO = userDao.selectUserInfoByCell(highUserCell);
        String highUserId = highUserDO.getUserId();
        //判断所填写的用户是否存在
        if (null != highUserDO) {
            //判断用户是否已经有是上线       -无上线，做添加操作
            if (null == userDao.selectUserInfoByUserId(userId).getHighUserId()
                || "".equals(userDao.selectUserInfoByUserId(userId).getHighUserId())) {
                if (userId.equals(highUserDO.getUserId())) {
                    result.setBizSucc(false);
                    result.setErrMsg("不能将自己设为上限!");
                } else {
                    userDao.updateHighByUserId(highUserId, userId);//插入user表相应的user
                    //判断是否在team_income加数据
                    TeamIncomeDO highUser = new TeamIncomeDO();
                    TeamIncomeDO oldHighUser = teamIncomeDAO.selectTeamIncomeByUserId(highUserId);
                    if (null == oldHighUser) {
                        highUser.setUserId(highUserId);
                        highUser.setTeamSum(1);
                        teamIncomeDAO.addNewTeam(highUser);
                        result.setBizSucc(true);
                    } else {
                        int teamSum = oldHighUser.getTeamSum() + 1;
                        teamIncomeDAO.updateTeamsum(teamSum, highUserId);
                        result.setBizSucc(true);
                    }
                }
            } else {//有上线，做修改上线的操作
                if (userId.equals(highUserDO.getUserId())) {
                    result.setBizSucc(false);
                    result.setErrMsg("不能将自己设为上限!");
                } else {
                    String havedHighUser = userDao.selectUserInfoByUserId(userId).getHighUserId();
                    if (highUserDO.getUserId().equals(havedHighUser)) {
                        result.setBizSucc(false);
                        result.setErrMsg("修改的上级不能与之前相同!");
                    } else {
                        //减去用户原来上线的teamSum
                        TeamIncomeDO oldHighUser = teamIncomeDAO.selectTeamIncomeByUserId(userDao
                            .selectUserInfoByUserId(userId).getHighUserId());
                        int teamSum = oldHighUser.getTeamSum() - 1;
                        teamIncomeDAO.updateTeamsum(teamSum, oldHighUser.getUserId());
                        //新的上级做相同的判断
                        userDao.updateHighByUserId(highUserId, userId);//插入user表相应的user
                        //判断是否在team_income加数据
                        TeamIncomeDO HighUser = teamIncomeDAO.selectTeamIncomeByUserId(highUserId);
                        if (null == HighUser) {
                            TeamIncomeDO highUser = new TeamIncomeDO();
                            highUser.setUserId(highUserId);
                            highUser.setTeamSum(1);
                            teamIncomeDAO.addNewTeam(highUser);
                            result.setBizSucc(true);
                        } else {
                            int teamSum_2 = HighUser.getTeamSum() + 1;
                            teamIncomeDAO.updateTeamsum(teamSum_2, highUserId);
                            result.setBizSucc(true);
                        }
                    }
                }
            }
        }

        return result;
    }
    
    @RequestMapping("check.html")
    public String check(HttpServletRequest request, ModelMap modelMap) {
		
		String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
    	
    	return "mng/check";
    }
	
	@RequestMapping("/checkPassword.do")
	@ResponseBody
	public Object checkPassword(final HttpServletRequest request,
			final ModelMap modelMap) {

		final String password = request.getParameter("password");

		final JsonResult result = new JsonResult(false);

		controllerTemplate.execute(result, new ControllerCallBack() {

			@Override
			public void executeService() {
				try {
					UserDO userDO = userDao.selectUserInfoByUserId("123");
					String passWord = userDO.getPassWord();
					
					if(StringUtils.equals(password, passWord)){
						result.setBizSucc(true);
					}else{
						result.setBizSucc(false);
						result.setErrMsg("密码错误");
					}
					
					
				} catch (Exception e) {
					logger.error("", e);
				}

			}

			@Override
			public void check() {

			}
		});

		return result;
	}
    
    @RequestMapping("userPassword.html")
    public String userPassword(HttpServletRequest request, ModelMap modelMap) {
		
		String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
    	return "mng/userPassword";
    }
	
	@RequestMapping("/userPassword.do")
	@ResponseBody
	public Object userPasswordFind(final HttpServletRequest request,
			final ModelMap modelMap) {

		final String cell = request.getParameter("cell");

		final JsonResult result = new JsonResult(false);

		controllerTemplate.execute(result, new ControllerCallBack() {

			@Override
			public void executeService() {
				try {
					UserDO userDO = userDao.selectUserInfoByCell(cell);
					
					if(null != userDO){
						String passWord = userDO.getPassWord();
						result.setBizSucc(true);
						result.setInformation(passWord);
					}else{
						result.setBizSucc(false);
						result.setErrMsg("查询失败，用户信息不存在");
					}
					
				} catch (Exception e) {
					logger.error("", e);
				}

			}

			@Override
			public void check() {

			}
		});

		return result;
	}
}
