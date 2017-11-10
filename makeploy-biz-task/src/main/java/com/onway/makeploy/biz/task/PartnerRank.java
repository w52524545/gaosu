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

	/* ��ʱ���� */
	@Override
	protected void process() {
		
		Calendar calendar = Calendar.getInstance();//��ʱ��ӡ����ȡ����ϵͳ��ǰʱ��
        calendar.add(Calendar.DATE, 0);    //�õ�����     2017-5-21
        String  today= new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        
        Calendar calendars = Calendar.getInstance();//��ʱ��ӡ����ȡ����ϵͳ��ǰʱ��
        calendars.add(Calendar.DATE, -1);    //�õ�ǰһ��   2017-5-20
        String  dayAgo= new SimpleDateFormat("yyyy-MM-dd").format(calendars.getTime());
        int partnerCount = 0;
        
		int todayCount = partnerRankDAO.count(dayAgo,today);//����������
		int yesterdayCount  = Integer.valueOf(sysConfigDAO.selectByKey("YESTERDAY_OUT_NUM").getSysValue());//ǰһ���������
		partnerCount = todayCount + yesterdayCount;//��ʵ����
		
		int allCount = partnerRankDAO.count(null,today);//�ŵ�������
		
		int outNum = Integer.valueOf(sysConfigDAO
				.selectByKey("OUT_NUM").getSysValue());//���γ���
		outNum = outNum -1;
		int numOfAll =Integer.valueOf(sysConfigDAO
				.selectByKey("NUM_OF_ALL").getSysValue());//����֮һ������
		
		int outCount = partnerCount / numOfAll;//��ǰ�����ŵ��û���
		if(outCount < 1){
			System.out.println("��ǰ��������");
			return;
		}
		int leftCount = allCount - outCount;//ʣ������
		//�ȰѸò������ �����  �����޸�
		//���ź������  ��leftCount��  partnerNum - outCount
		List<PartnerRankDO> leftRankList = partnerRankDAO.selectAllDesc(null,today, 0 ,leftCount);
		int lastPartner = 0;
		if(leftRankList.size() > 0 ){
			lastPartner = leftRankList.get(0).getPartnerNum() - outCount;
			
		}
		//��ǰ��  outCount ����  
		List<PartnerRankDO> outRankList = partnerRankDAO.selectAll(null,today, 0, outCount);
		
		for (PartnerRankDO partnerRankDO : leftRankList) {
			int partnerNum = partnerRankDO.getPartnerNum();
			partnerNum = partnerNum - outCount;
			partnerRankDAO.changePartnerNum(partnerNum, partnerRankDO.getId());
		}
//		sysConfigDAO.updateRate(outRankList.get(outRankList.size()-1).getOrderId(), "OUT_RANK_NO");
		
		for (PartnerRankDO partnerRankDO : outRankList) {
			//���ڵ���α��ų�ȥ��
			int outNums = partnerRankDO.getOutNum();
			if(outNums == outNum){
				partnerRankDAO.delete(partnerRankDO.getId());
			}else{
				lastPartner = lastPartner + 1; 
				partnerRankDAO.changePartnerNum(lastPartner, partnerRankDO.getId());
				partnerRankDAO.changeOutNum(outNums+1 , partnerRankDO.getId());
			}
		}
		//����ǰһ���������
		sysConfigDAO.updateRate(String.valueOf(outCount), "YESTERDAY_OUT_NUM");
	}
}
