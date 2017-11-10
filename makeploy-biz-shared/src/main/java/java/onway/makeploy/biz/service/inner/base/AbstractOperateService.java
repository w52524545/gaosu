package java.onway.makeploy.biz.service.inner.base;

import org.apache.log4j.Logger;

import com.onway.makeploy.core.localcache.manager.SysConfigCacheManager;
import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.base.PageQueryResult;
import com.onway.platform.common.base.QueryResult;
import com.onway.platform.common.service.template.AbstractServiceImpl;

/**
 * ������
 * 
 * @author guangdong.li
 * @version $Id: AbstractOperateService.java, v 0.1 6 Jan 2016 15:03:31 guangdong.li Exp $
 */
public class AbstractOperateService extends AbstractServiceImpl {

    /**��ͨ��־logger*/
    protected static Logger         logger = Logger.getLogger(AbstractOperateService.class);

    /** ϵͳ���û���*/
    protected SysConfigCacheManager sysConfigCacheManager;

    /**
     * PageQueryResultת��
     * 
     * @param result
     * @param pageQueryResult
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void buildPageQueryResult(PageQueryResult result, PageQueryResult pageQueryResult) {

        result.setCode(pageQueryResult.getCode());
        result.setMessage(pageQueryResult.getMessage());
        result.setSuccess(pageQueryResult.isSuccess());

        result.setResultList(pageQueryResult.getResultList());
        result.setTotalItems(pageQueryResult.getTotalItems());
        result.setTotalPages(pageQueryResult.getTotalPages());
        result.setCurrentPage(pageQueryResult.getCurrentPage());
        result.setItemsPerPage(pageQueryResult.getItemsPerPage());
    }

    /**
     * QueryResultת��
     * 
     * @param result
     * @param queryResult
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void buildQueryResult(QueryResult result, QueryResult queryResult) {

        result.setCode(queryResult.getCode());
        result.setMessage(queryResult.getMessage());
        result.setSuccess(queryResult.isSuccess());
        result.setResultObject(queryResult.getResultObject());

    }


    /**
     * QueryResultת��
     * 
     * @param result
     * @param queryResult
     */
    protected void buildBaseResult(BaseResult result, BaseResult queryResult) {
        result.setCode(queryResult.getCode());
        result.setMessage(queryResult.getMessage());
        result.setSuccess(queryResult.isSuccess());
    }

    /**
     * Setter method for property <tt>sysConfigCacheManager</tt>.
     * 
     * @param sysConfigCacheManager value to be assigned to property sysConfigCacheManager
     */
    public void setSysConfigCacheManager(SysConfigCacheManager sysConfigCacheManager) {
        this.sysConfigCacheManager = sysConfigCacheManager;
    }

}
