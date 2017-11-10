package com.onway.web.controller.result;

import java.util.ArrayList;
import java.util.List;

import com.onway.makeploy.common.dal.dataobject.ProImageDO;

public class ProImageResult {
    private String           flag;
    private List<ProImageDO> result = new ArrayList<ProImageDO>();

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<ProImageDO> getResult() {
        return result;
    }

    public void setResult(List<ProImageDO> result) {
        this.result = result;
    }
}
