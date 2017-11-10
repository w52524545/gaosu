package com.onway.web.controller.mng;

import java.util.List;

import com.onway.makeploy.common.dal.dataobject.CommerceCheckDO;
import com.onway.makeploy.common.dal.dataobject.CommerceDO;
import com.onway.makeploy.common.dal.dataobject.UserDO;

public class CommerceResult {

	
	private CommerceDO commerceDO;
	
	private UserDO userDO;
	
	private UserDO leaderUser;
	
	private UserDO chargeUser;
	
	private UserDO secretaryUser;
	
	private CommerceCheckDO leaderCheckUser;
	
	private CommerceCheckDO chargeCheckUser;
	
	private CommerceCheckDO secretaryCheckUser;
	
	private int checkLeaderUser;
	
	private int checkViceLeaderUser;
	
	private int checkChargeUser;
	
	private int checkSecretaryUser;
	
	private int checkExecutiveChairmanUser;
	
	private int checkStandingPresidentUser;
	
	private int checkHonoraryChairmanUser;
	
	private int checkExecutiveDirectorUser;
	
	private int checkManagingDirectorUser;
	
	private int checkExecutiveUser;
	
	private int checkSecretaryPartyUser;
	
	private List<UserDO> viceLeaderUser;
	
	private List<CommerceCheckDO> viceCheckLeader;
	
	private List<CommerceCheckDO> executiveChairmanCheckLeader;
	
	private List<CommerceCheckDO> standingPresidentCheckLeader;
	
	private List<CommerceCheckDO> honoraryChairmanCheckLeader;
	
	private List<CommerceCheckDO> executiveDirectorCheckLeader;
	
	private List<CommerceCheckDO> managingDirectorCheckLeader;
	
	private List<CommerceCheckDO> executiveCheckLeader;
	
	private List<CommerceCheckDO> secretaryPartyCheckLeader;
	
	private int checkNewUser;
	
	public int getCheckSecretaryUser() {
		return checkSecretaryUser;
	}

	public void setCheckSecretaryUser(int checkSecretaryUser) {
		this.checkSecretaryUser = checkSecretaryUser;
	}

	private CommerceCheckDO commerceCheckDO;

	public CommerceDO getCommerceDO() {
		return commerceDO;
	}

	public void setCommerceDO(CommerceDO commerceDO) {
		this.commerceDO = commerceDO;
	}

	public UserDO getLeaderUser() {
		return leaderUser;
	}

	public void setLeaderUser(UserDO leaderUser) {
		this.leaderUser = leaderUser;
	}

	public UserDO getUserDO() {
		return userDO;
	}

	public void setUserDO(UserDO userDO) {
		this.userDO = userDO;
	}

	public int getCheckLeaderUser() {
		return checkLeaderUser;
	}

	public void setCheckLeaderUser(int checkLeaderUser) {
		this.checkLeaderUser = checkLeaderUser;
	}

	public int getCheckViceLeaderUser() {
		return checkViceLeaderUser;
	}

	public void setCheckViceLeaderUser(int checkViceLeaderUser) {
		this.checkViceLeaderUser = checkViceLeaderUser;
	}

	public CommerceCheckDO getCommerceCheckDO() {
		return commerceCheckDO;
	}

	public void setCommerceCheckDO(CommerceCheckDO commerceCheckDO) {
		this.commerceCheckDO = commerceCheckDO;
	}

	public UserDO getChargeUser() {
		return chargeUser;
	}

	public void setChargeUser(UserDO chargeUser) {
		this.chargeUser = chargeUser;
	}

	public int getCheckChargeUser() {
		return checkChargeUser;
	}

	public void setCheckChargeUser(int checkChargeUser) {
		this.checkChargeUser = checkChargeUser;
	}


	public UserDO getSecretaryUser() {
		return secretaryUser;
	}

	public void setSecretaryUser(UserDO secretaryUser) {
		this.secretaryUser = secretaryUser;
	}

	public int getCheckNewUser() {
		return checkNewUser;
	}

	public void setCheckNewUser(int checkNewUser) {
		this.checkNewUser = checkNewUser;
	}
	
	public CommerceCheckDO getLeaderCheckUser() {
		return leaderCheckUser;
	}

	public void setLeaderCheckUser(CommerceCheckDO leaderCheckUser) {
		this.leaderCheckUser = leaderCheckUser;
	}

	public CommerceCheckDO getChargeCheckUser() {
		return chargeCheckUser;
	}

	public void setChargeCheckUser(CommerceCheckDO chargeCheckUser) {
		this.chargeCheckUser = chargeCheckUser;
	}

	public CommerceCheckDO getSecretaryCheckUser() {
		return secretaryCheckUser;
	}

	public void setSecretaryCheckUser(CommerceCheckDO secretaryCheckUser) {
		this.secretaryCheckUser = secretaryCheckUser;
	}

	public List<UserDO> getViceLeaderUser() {
		return viceLeaderUser;
	}

	public void setViceLeaderUser(List<UserDO> viceLeaderUser) {
		this.viceLeaderUser = viceLeaderUser;
	}

	public List<CommerceCheckDO> getViceCheckLeader() {
		return viceCheckLeader;
	}

	public void setViceCheckLeader(List<CommerceCheckDO> viceCheckLeader) {
		this.viceCheckLeader = viceCheckLeader;
	}

	public int getCheckExecutiveChairmanUser() {
		return checkExecutiveChairmanUser;
	}

	public void setCheckExecutiveChairmanUser(int checkExecutiveChairmanUser) {
		this.checkExecutiveChairmanUser = checkExecutiveChairmanUser;
	}

	public int getCheckStandingPresidentUser() {
		return checkStandingPresidentUser;
	}

	public void setCheckStandingPresidentUser(int checkStandingPresidentUser) {
		this.checkStandingPresidentUser = checkStandingPresidentUser;
	}

	public int getCheckHonoraryChairmanUser() {
		return checkHonoraryChairmanUser;
	}

	public void setCheckHonoraryChairmanUser(int checkHonoraryChairmanUser) {
		this.checkHonoraryChairmanUser = checkHonoraryChairmanUser;
	}

	public int getCheckExecutiveDirectorUser() {
		return checkExecutiveDirectorUser;
	}

	public void setCheckExecutiveDirectorUser(int checkExecutiveDirectorUser) {
		this.checkExecutiveDirectorUser = checkExecutiveDirectorUser;
	}

	public int getCheckManagingDirectorUser() {
		return checkManagingDirectorUser;
	}

	public void setCheckManagingDirectorUser(int checkManagingDirectorUser) {
		this.checkManagingDirectorUser = checkManagingDirectorUser;
	}

	public int getCheckExecutiveUser() {
		return checkExecutiveUser;
	}

	public void setCheckExecutiveUser(int checkExecutiveUser) {
		this.checkExecutiveUser = checkExecutiveUser;
	}

	public int getCheckSecretaryPartyUser() {
		return checkSecretaryPartyUser;
	}

	public void setCheckSecretaryPartyUser(int checkSecretaryPartyUser) {
		this.checkSecretaryPartyUser = checkSecretaryPartyUser;
	}

	public List<CommerceCheckDO> getExecutiveChairmanCheckLeader() {
		return executiveChairmanCheckLeader;
	}

	public void setExecutiveChairmanCheckLeader(
			List<CommerceCheckDO> executiveChairmanCheckLeader) {
		this.executiveChairmanCheckLeader = executiveChairmanCheckLeader;
	}

	public List<CommerceCheckDO> getStandingPresidentCheckLeader() {
		return standingPresidentCheckLeader;
	}

	public void setStandingPresidentCheckLeader(
			List<CommerceCheckDO> standingPresidentCheckLeader) {
		this.standingPresidentCheckLeader = standingPresidentCheckLeader;
	}

	public List<CommerceCheckDO> getHonoraryChairmanCheckLeader() {
		return honoraryChairmanCheckLeader;
	}

	public void setHonoraryChairmanCheckLeader(
			List<CommerceCheckDO> honoraryChairmanCheckLeader) {
		this.honoraryChairmanCheckLeader = honoraryChairmanCheckLeader;
	}

	public List<CommerceCheckDO> getExecutiveDirectorCheckLeader() {
		return executiveDirectorCheckLeader;
	}

	public void setExecutiveDirectorCheckLeader(List<CommerceCheckDO> executiveDirectorCheckLeader) {
		this.executiveDirectorCheckLeader = executiveDirectorCheckLeader;
	}

	public List<CommerceCheckDO> getManagingDirectorCheckLeader() {
		return managingDirectorCheckLeader;
	}

	public void setManagingDirectorCheckLeader(
			List<CommerceCheckDO> managingDirectorCheckLeader) {
		this.managingDirectorCheckLeader = managingDirectorCheckLeader;
	}

	public List<CommerceCheckDO> getExecutiveCheckLeader() {
		return executiveCheckLeader;
	}

	public void setExecutiveCheckLeader(List<CommerceCheckDO> executiveCheckLeader) {
		this.executiveCheckLeader = executiveCheckLeader;
	}

	public List<CommerceCheckDO> getSecretaryPartyCheckLeader() {
		return secretaryPartyCheckLeader;
	}

	public void setSecretaryPartyCheckLeader(
			List<CommerceCheckDO> secretaryPartyCheckLeader) {
		this.secretaryPartyCheckLeader = secretaryPartyCheckLeader;
	}
	
}
