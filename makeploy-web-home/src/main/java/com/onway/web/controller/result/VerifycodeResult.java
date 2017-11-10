package com.onway.web.controller.result;

import com.onway.makeploy.common.dal.dataobject.VerifyCodeDO;

public class VerifycodeResult extends JsonResult {
	private static final long serialVersionUID = 741231858441822688L;
	private VerifyCodeDO verifyCodeDO;

	/**
	 * @param bizSucc
	 * @param errCode
	 * @param message
	 */
	public VerifycodeResult(boolean bizSucc, String errCode, String message) {
		super(bizSucc, message, message);
	}

	public VerifyCodeDO getVerifyCodeDO() {
		return verifyCodeDO;
	}

	public void setVerifyCodeDO(VerifyCodeDO verifyCodeDO) {
		this.verifyCodeDO = verifyCodeDO;
	}

}
