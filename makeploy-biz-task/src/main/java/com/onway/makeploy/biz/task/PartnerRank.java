package com.onway.makeploy.biz.task;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.onway.makeploy.common.dal.daointerface.PartnerRankDAO;
import com.onway.makeploy.common.dal.daointerface.SysConfigDAO;
import com.onway.makeploy.common.dal.dataobject.PartnerRankDO;

public class PartnerRank extends AbstractTask {
	
	private PartnerRankDAO partnerRankDAO ;
	
	private SysConfigDAO     sysConfigDAO;

	public SysConfigDAO getSysConfigDAO() {
		return sysConfigDAO;
	}

	public void setSysConfigDAO(SysConfigDAO sysConfigDAO) {
		this.sysConfigDAO = sysConfigDAO;
	}

	public PartnerRankDAO getPartnerRankDAO() {
		return partnerRankDAO;
	}

	public void setPartnerRankDAO(PartnerRankDAO partnerRankDAO) {
		this.partnerRankDAO = partnerRankDAO;
	}

	@Override
	protected boolean canProcess() {
		// TODO Auto-generated method stub
		return true;
	}

	/* 定时排序 */
	@Override
	protected void process() {
		
		Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
        calendar.add(Calendar.DATE, 0);    //得到当天     2017-5-21
        String  today= new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        
        Calendar calendars = Calendar.getInstance();//此时打印它获取的是系统当前时间
        calendars.add(Calendar.DATE, -1);    //得到前一天   2017-5-20
        String  dayAgo= new SimpleDateFormat("yyyy-MM-dd").format(calendars.getTime());
        int partnerCount = 0;
        
		int todayCount = partnerRankDAO.count(dayAgo,today);//当天总人数
		int yesterdayCount  = Integer.valueOf(sysConfigDAO.selectByKey("YESTERDAY_OUT_NUM").getSysValue());//前一天出局人数
		partnerCount = todayCount + yesterdayCount;//真实人数
		
		int allCount = partnerRankDAO.count(null,today);//排单总人数
		
		int outNum = Integer.valueOf(sysConfigDAO
				.selectByKey("OUT_NUM").getSysValue());//几次出局
		outNum = outNum -1;
		int numOfAll =Integer.valueOf(sysConfigDAO
				.selectByKey("NUM_OF_ALL").getSysValue());//几分之一往后排
		
		int outCount = partnerCount / numOfAll;//当前往后排的用户数
		if(outCount < 1){
			System.out.println("当前人数不足");
			return;
		}
		int leftCount = allCount - outCount;//剩余人数
		//先把该查出来的 查出来  再做修改
		//先排后面的人  后leftCount人  partnerNum - outCount
		List<PartnerRankDO> leftRankList = partnerRankDAO.selectAllDesc(null,today, 0 ,leftCount);
		int lastPartner = 0;
		if(leftRankList.size() > 0 ){
			lastPartner = leftRankList.get(0).getPartnerNum() - outCount;
			
		}
		//排前面  outCount 人数  
		List<PartnerRankDO> outRankList = partnerRankDAO.selectAll(null,today, 0, outCount);
		
		for (PartnerRankDO partnerRankDO : leftRankList) {
			int partnerNum = partnerRankDO.getPartnerNum();
			partnerNum = partnerNum - outCount;
			partnerRankDAO.changePartnerNum(partnerNum, partnerRankDO.getId());
		}
//		sysConfigDAO.updateRate(outRankList.get(outRankList.size()-1).getOrderId(), "OUT_RANK_NO");
		
		for (PartnerRankDO partnerRankDO : outRankList) {
			//存在第五次被排出去的
			int outNums = partnerRankDO.getOutNum();
			if(outNums == outNum){
				partnerRankDAO.delete(partnerRankDO.getId());
			}else{
				lastPartner = lastPartner + 1; 
				partnerRankDAO.changePartnerNum(lastPartner, partnerRankDO.getId());
				partnerRankDAO.changeOutNum(outNums+1 , partnerRankDO.getId());
			}
		}
		//更新前一天出局人数
		sysConfigDAO.updateRate(String.valueOf(outCount), "YESTERDAY_OUT_NUM");
	}
}
