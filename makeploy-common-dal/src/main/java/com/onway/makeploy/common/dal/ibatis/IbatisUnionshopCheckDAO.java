/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.makeploy.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.makeploy.common.dal.daointerface.UnionshopCheckDAO;

import com.onway.makeploy.common.dal.dataobject.UnionshopCheckDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.Map;
import java.util.HashMap;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.makeploy.common.dal.daointerface.UnionshopCheckDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/makeploy_unionshop_check.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisUnionshopCheckDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisUnionshopCheckDAO extends SqlMapClientDaoSupport implements UnionshopCheckDAO {
	/**
	 *  Query DB table <tt>makeploy_unionshop_check</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from makeploy_unionshop_check</tt>
	 *
	 *	@param shopName
	 *	@param shopCell
	 *	@param offset
	 *	@param limit
	 *	@return List<UnionshopCheckDO>
	 *	@throws DataAccessException
	 */	 
    public List<UnionshopCheckDO> selectUnCheck(String shopName, String shopCell, String offset, String limit) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("shopName", shopName);
        param.put("shopCell", shopCell);
        param.put("offset", offset);
        param.put("limit", limit);

        return getSqlMapClientTemplate().queryForList("MS-UNIONSHOP-CHECK-SELECT-UN-CHECK", param);

    }

	/**
	 *  Query DB table <tt>makeploy_unionshop_check</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select count(1) from makeploy_unionshop_check</tt>
	 *
	 *	@param shopName
	 *	@param shopCell
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long countUnCheck(String shopName, String shopCell) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("shopName", shopName);
        param.put("shopCell", shopCell);

	    Long retObj = (Long) getSqlMapClientTemplate().queryForObject("MS-UNIONSHOP-CHECK-COUNT-UN-CHECK", param);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.longValue();
		}

    }

}