package com.onway.web.controller.result;

/**
 * �ļ��ϴ������
 * 
 * @author jiaming.zhu
 * @version $Id: FileUploadResult.java, v 0.1 2016��9��9�� ����4:29:03 ROG Exp $
 */
public class FileUploadResult extends ControllerJsonResult {

    private static final long serialVersionUID = 1475348231900998033L;

    private String            ReturnValue;

    public String getReturnValue() {
        return ReturnValue;
    }

    public void setReturnValue(String returnValue) {
        ReturnValue = returnValue;
    }

    public FileUploadResult(boolean bizSucc) {
        super(bizSucc);
    }

}
