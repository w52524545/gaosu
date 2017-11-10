package com.onway.web.controller.result;

import com.onway.makeploy.common.dal.dataobject.UserDO;

public class UserResult extends JsonResult {
	private static final long serialVersionUID = 741231858441822688L;
	private UserDO userDO;

	/**
	 * @param bizSucc
	 * @param errCode
	 * @param message
	 */
	public UserResult(boolean bizSucc, String errCode, String message) {
		super(bizSucc, message, message);
	}

	public UserDO getUserDO() {
		return userDO;
	}

	public void setUserDO(UserDO userDO) {
		this.userDO = userDO;
	}

}
